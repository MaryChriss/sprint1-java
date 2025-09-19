package fiap.com.br.future_stack.moto;

import fiap.com.br.future_stack.patio.Patio;
import fiap.com.br.future_stack.patio.PatioRepository;
import fiap.com.br.future_stack.zona.TipoZona;
import fiap.com.br.future_stack.zona.Zona;
import fiap.com.br.future_stack.zona.ZonaRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class MotoService {

    private final MotoRepository motoRepository;
    private final ZonaRepository zonaRepository;
    private final PatioRepository patioRepository;

    public MotoDTO createMoto(Long patioId, MotoDTO dto) {
        Patio patio = patioRepository.findById(patioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pátio não encontrado"));

        Moto moto = new Moto();
        moto.setModelo(dto.getModelo());
        moto.setPlaca(dto.getPlaca() != null ? dto.getPlaca().trim().toUpperCase() : null);
        moto.setStatus(dto.getStatus());
        moto.setPatio(patio);

        if (dto.getTipoZona() != null) {
            Zona z = zonaRepository.findByPatioIdAndTipoZona(patio.getId(), dto.getTipoZona())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.BAD_REQUEST, "Zona " + dto.getTipoZona() + " não existe neste pátio"));
            moto.setZona(z);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Informe zonaId ou tipoZona para associar a moto a uma zona existente");
        }

        Moto salvo = motoRepository.save(moto);
        return toDTO(salvo);
    }

    public MotoDTO updateMoto(Long id, MotoDTO dto) {
        Moto moto = motoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Moto não encontrada"));

        if (dto.modelo != null) moto.setModelo(dto.modelo);
        if (dto.placa  != null) moto.setPlaca(dto.placa);
        if (dto.status != null) moto.setStatus(dto.status);

        if (dto.patioId != null && (moto.getPatio() == null || !moto.getPatio().getId().equals(dto.patioId))) {
            Patio novo = patioRepository.findById(dto.patioId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pátio não encontrado"));
            moto.setPatio(novo);
            if (moto.getZona() != null && !zonaRepository.existsByIdAndPatioId(moto.getZona().getId(), novo.getId())) {
                moto.setZona(null);
            }
        }

        if (dto.zonaId != null) {
            Long pid = moto.getPatio() != null ? moto.getPatio().getId() : dto.patioId;
            if (pid == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe o pátio para validar a zona");
            Zona zona = zonaRepository.findByIdAndPatioId(dto.zonaId, pid)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Zona não encontrada neste pátio"));
            moto.setZona(zona);
        }

        return toDTO(motoRepository.save(moto));
    }

    private Moto mapearMoto(Moto moto, MotoDTO dto) {
        moto.setModelo(dto.modelo);
        moto.setPlaca(dto.placa);
        moto.setStatus(dto.status);

        if (dto.zonaId != null) {
            Zona zona = zonaRepository.findById(dto.zonaId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Zona não encontrada"));
            moto.setZona(zona);
        } else {
            moto.setZona(null);
        }
        return moto;
    }

    public MotoDTO toDTO(Moto moto) {
        return new MotoDTO(
                moto.getId(),
                moto.getModelo(),
                moto.getPlaca(),
                moto.getZona() != null ? moto.getZona().getId() : null,
                moto.getZona() != null ? moto.getZona().getTipoZona() : null,
                moto.getStatus(),
                moto.getPatio() != null ? moto.getPatio().getId() : null
        );
    }
}
