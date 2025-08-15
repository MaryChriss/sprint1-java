package fiap.com.br.future_stack.moto;

import fiap.com.br.future_stack.zona.Zona;
import fiap.com.br.future_stack.zona.ZonaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class MotoService {

    private final MotoRepository motoRepository;
    private final ZonaRepository zonaRepository;

    public MotoDTO createMoto(MotoDTO dto) {
        Moto moto = mapearMoto(new Moto(), dto);
        return toDTO(motoRepository.save(moto));
    }

    public MotoDTO updateMoto(Long id, MotoDTO dto) {
        Moto moto = motoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Moto não encontrada"));
        mapearMoto(moto, dto);
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
                moto.getStatus()
        );
    }
}
