package fiap.com.br.future_stack.patio;

import fiap.com.br.future_stack.moto.Moto;
import fiap.com.br.future_stack.moto.MotoDTO;
import fiap.com.br.future_stack.moto.MotoRepository;
import fiap.com.br.future_stack.zona.Zona;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class PatioService {

    @Autowired
    private PatioRepository patioRepository;

    @Autowired
    private MotoRepository motoRepository;

    public PatioDTO createPatio(PatioDTO patioDTO) {
        Patio patio = mapearPatio(new Patio(), patioDTO);
        //Moto moto = mapearMoto(new Moto(), dto);
        return toDTO(patioRepository.save(patio));

    }

    public PatioDTO updatePatio(Long id, PatioDTO patioDTO) {
        Patio patio = patioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "patio não encontrado"));
        mapearPatio(patio, patioDTO);
        return toDTO(patioRepository.save(patio));
    }

    public PatioDTO toDTO(Patio patio) {
        return new PatioDTO(
                patio.getId(),
                patio.getNome(),
                patio.getQuantidadeVagas(),
                patio.getMetragemZonaA(),
                patio.getMetragemZonaB()
        );
    }

    private Patio mapearPatio(Patio patio, PatioDTO dto) {
        patio.setNome(dto.nome);
        patio.setQuantidadeVagas(dto.quantidadeVagas);
        patio.setMetragemZonaA(dto.metragemZonaA);
        patio.setMetragemZonaB(dto.metragemZonaB);
        return patio;
    }

}
