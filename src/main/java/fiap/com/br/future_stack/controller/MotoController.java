package fiap.com.br.future_stack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import fiap.com.br.future_stack.dto.MotoDTO;
import fiap.com.br.future_stack.model.Moto;
import fiap.com.br.future_stack.model.Zona;
import fiap.com.br.future_stack.repository.MotoRepository;
import fiap.com.br.future_stack.repository.ZonaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/motos")
@Slf4j
public class MotoController {

    @Autowired
    private MotoRepository motoRepository;

    @Autowired
    private ZonaRepository zonaRepository;

    @GetMapping
    @Cacheable("motos")
    @Operation(summary = "Listar motos com paginação, ordenação e filtros opcionais", tags = "Moto")
    public Page<MotoDTO> listar(
            @RequestParam(required = false) String modelo,
            @RequestParam(required = false) String placa,
            Pageable pageable) {

        log.info("Listando motos com filtros: modelo={}, placa={}", modelo, placa);

        Page<Moto> page;

        if (modelo != null && !modelo.isEmpty()) {
            page = motoRepository.findByModeloContainingIgnoreCase(modelo, pageable);
        } else if (placa != null && !placa.isEmpty()) {
            page = motoRepository.findByPlacaContainingIgnoreCase(placa, pageable);
        } else {
            page = motoRepository.findAll(pageable);
        }

        return page.map(this::toDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar moto por ID", tags = "Moto")
    public MotoDTO buscar(@PathVariable Long id) {
        log.info("Buscando moto id {}", id);
        Moto moto = getMoto(id);
        return toDTO(moto);
    }

    @PostMapping
    @CacheEvict(value = "motos", allEntries = true)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(responses = @ApiResponse(responseCode = "400"))
    public MotoDTO criar(@RequestBody @Valid MotoDTO dto) {
        log.info("Criando moto modelo: {}, placa: {}", dto.modelo(), dto.placa());

        Zona zona = null;
        if (dto.zonaId() != null) {
            zona = zonaRepository.findById(dto.zonaId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Zona não encontrada"));
        }

        Moto moto = new Moto();
        moto.setModelo(dto.modelo());
        moto.setPlaca(dto.placa());
        moto.setZona(zona);
        moto.setStatus(dto.status());

        return toDTO(motoRepository.save(moto));
    }

    @PutMapping("/{id}")
    @CacheEvict(value = "motos", allEntries = true)
    @Operation(responses = @ApiResponse(responseCode = "400"))
    public MotoDTO atualizar(@PathVariable Long id, @RequestBody @Valid MotoDTO dto) {
        log.info("Atualizando moto id {}", id);
        Moto moto = getMoto(id);

        Zona zona = null;
        if (dto.zonaId() != null) {
            zona = zonaRepository.findById(dto.zonaId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Zona não encontrada"));
        }

        moto.setModelo(dto.modelo());
        moto.setPlaca(dto.placa());
        moto.setZona(zona);
        moto.setStatus(dto.status());

        return toDTO(motoRepository.save(moto));
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = "motos", allEntries = true)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Excluir moto", tags = "Moto")
    public void excluir(@PathVariable Long id) {
        log.info("Excluindo moto id {}", id);
        Moto moto = getMoto(id);
        motoRepository.delete(moto);
    }

    private Moto getMoto(Long id) {
        return motoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Moto não encontrada"));
    }

    private MotoDTO toDTO(Moto moto) {
        return new MotoDTO(
                moto.getId(),
                moto.getModelo(),
                moto.getPlaca(),
                moto.getZona() != null ? moto.getZona().getId() : null,
                moto.getStatus());
    }
}
