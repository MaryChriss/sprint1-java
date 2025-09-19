package fiap.com.br.future_stack.moto;

import fiap.com.br.future_stack.zona.TipoZona;
import fiap.com.br.future_stack.zona.ZonaRepository;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    @GetMapping("/delete/{id}")
    public String excluirHtml(@PathVariable Long id) {
        motoRepository.deleteById(id);
        return "redirect:/motos";
    }

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

    @PostMapping("/{patioId}")
    @CacheEvict(value = "motos", allEntries = true)
    @ResponseStatus(HttpStatus.CREATED)
    public MotoDTO criar(@PathVariable Long patioId, @RequestBody @Valid MotoDTO dto) {
        log.info("Criando moto modelo: {}, placa: {}", dto.modelo, dto.placa);
        return motoService.createMoto(patioId, dto);
    }

    @GetMapping("/{id}/localizacao")
    @Operation(summary = "Obter pátio e zona onde a moto está", tags = "Moto")
    public MotoLocalizacaoDTO localizacao(@PathVariable Long id) {
        Moto moto = motoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Moto não encontrada"));

        var zona = moto.getZona();
        if (zona == null) {
            return new MotoLocalizacaoDTO(
                    moto.getId(),
                    moto.getModelo(),
                    moto.getPlaca(),
                    null,
                    null,
                    null,
                    null,
                    null
            );
        }

        var patio = zona.getPatio();
        return new MotoLocalizacaoDTO(
                moto.getId(),
                moto.getModelo(),
                moto.getPlaca(),
                zona.getId(),
                zona.getNome(),
                zona.getTipoZona() != null ? zona.getTipoZona().name() : null,
                patio != null ? patio.getId() : null,
                patio != null ? patio.getNome() : null
        );
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

    @GetMapping("/search/{patioId}")
    @Operation(
            summary = "Buscar motos por placa e/ou zona (A/B) dentro de um pátio",
            tags = "Moto"
    )
    public Page<MotoDTO> searchPorPatio(
            @PathVariable Long patioId,
            @RequestParam(required = false) String placa,
            @RequestParam(required = false) TipoZona tipoZona,
            Pageable pageable) {

        final String filtroPlaca = placa == null ? "" : placa.trim();

        // 1) placa + tipoZona
        if (!filtroPlaca.isBlank() && tipoZona != null) {
            return motoRepository
                    .findByPlacaContainingIgnoreCaseAndZona_TipoZonaAndZona_Patio_Id(
                            filtroPlaca, tipoZona, patioId, pageable)
                    .map(motoService::toDTO);
        }

        // 2) somente placa (dentro do pátio)
        if (!filtroPlaca.isBlank()) {
            return motoRepository
                    .findByPlacaContainingIgnoreCaseAndPatio_Id(
                            filtroPlaca, patioId, pageable)
                    .map(motoService::toDTO);
        }

        // 3) somente tipoZona (dentro do pátio)
        if (tipoZona != null) {
            return motoRepository
                    .findByZona_TipoZonaAndZona_Patio_Id(
                            tipoZona, patioId, pageable)
                    .map(motoService::toDTO);
        }

        // 4) sem filtros -> retorna tudo do pátio (ou Page.empty se preferir)
        return motoRepository.findByPatio_Id(patioId, pageable)
                .map(motoService::toDTO);
    }


}
