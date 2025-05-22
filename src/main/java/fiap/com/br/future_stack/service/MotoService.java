package fiap.com.br.future_stack.service;

import fiap.com.br.future_stack.dto.MotoDTO;
import fiap.com.br.future_stack.model.Moto;
import fiap.com.br.future_stack.model.Zona;
import fiap.com.br.future_stack.repository.MotoRepository;
import fiap.com.br.future_stack.repository.ZonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MotoService {

    @Autowired
    private MotoRepository repository;

    @Autowired
    private ZonaRepository zonaRepository;

    public Page<MotoDTO> listar(Pageable pageable) {
        return repository.findAll(pageable).map(this::toDTO);
    }

    public Optional<MotoDTO> buscarPorId(Long id) {
        return repository.findById(id).map(this::toDTO);
    }

    public MotoDTO salvar(MotoDTO dto) {
        Zona zona = null;
        
        if (dto.zonaId() != null) {
            zona = zonaRepository.findById(dto.zonaId())
                    .orElseThrow(() -> new IllegalArgumentException("Zona não encontrada"));
        }
        
        Moto moto = new Moto();
        moto.setModelo(dto.modelo());
        moto.setPlaca(dto.placa());
        moto.setZona(zona);
        
        return toDTO(repository.save(moto));
    }    

    public void excluir(Long id) {
        repository.deleteById(id);
    }

    public MotoDTO atualizar(Long id, MotoDTO dto) {
        Moto moto = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Moto não encontrada"));
    
        // Atualiza os campos
        moto.setModelo(dto.modelo());
        moto.setPlaca(dto.placa());
    
        if (dto.zonaId() != null) {
            Zona zona = zonaRepository.findById(dto.zonaId())
                    .orElseThrow(() -> new IllegalArgumentException("Zona não encontrada"));
            moto.setZona(zona);
        } else {
            moto.setZona(null);
        }
    
        Moto motoAtualizada = repository.save(moto);
        return toDTO(motoAtualizada);
    }
    
    private MotoDTO toDTO(Moto moto) {
        return new MotoDTO(
                moto.getId(),
                moto.getModelo(),
                moto.getPlaca(),
                moto.getZona() != null ? moto.getZona().getId() : null
        );
    }
}
