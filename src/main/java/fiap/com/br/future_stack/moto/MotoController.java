package fiap.com.br.future_stack.moto;

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

import fiap.com.br.future_stack.zona.Zona;
import fiap.com.br.future_stack.zona.ZonaRepository;
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
    private MotoService motoService;

    @Autowired
    private ZonaRepository zonaRepository;

    @GetMapping
    @Cacheable("motos")
    @Operation(summary = "Listar motos com paginação, ordenação e filtros opcionais", tags = "Moto")
    public Page<MotoDTO> listar(@RequestParam(required = false) String modelo,
                                @RequestParam(required = false) String placa,
                                Pageable pageable) {
        log.info("Listando motos com filtros: modelo={}, placa={}", modelo, placa);

        String filtroModelo = (modelo != null && !modelo.isBlank()) ? modelo : "";
        String filtroPlaca  = (placa  != null && !placa.isBlank())  ? placa  : "";

        Page<Moto> page = motoRepository
                .findByModeloContainingIgnoreCaseAndPlacaContainingIgnoreCase(filtroModelo, filtroPlaca, pageable);

        return page.map(motoService::toDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar moto por ID", tags = "Moto")
    public MotoDTO buscar(@PathVariable Long id) {
        log.info("Buscando moto id {}", id);
        Moto moto = getMoto(id);

        return motoService.toDTO(getMoto(id));
    }

    @PostMapping
    @CacheEvict(value = "motos", allEntries = true)
    @ResponseStatus(HttpStatus.CREATED)
    public MotoDTO criar(@RequestBody @Valid MotoDTO dto) {
        log.info("Criando moto modelo: {}, placa: {}", dto.modelo, dto.placa);
        return motoService.createMoto(dto);
    }

    @PutMapping("/{id}")
    @CacheEvict(value = "motos", allEntries = true)
    public MotoDTO atualizar(@PathVariable Long id, @RequestBody @Valid MotoDTO dto) {
        log.info("Atualizando moto id {}", id);
        return motoService.updateMoto(id, dto);
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

}
