package fiap.com.br.future_stack.controller;

import fiap.com.br.future_stack.dto.PatioDTO;
import fiap.com.br.future_stack.model.Patio;
import fiap.com.br.future_stack.repository.PatioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/patios")
@Slf4j
public class PatioController {

    @Autowired
    private PatioRepository patioRepository;

    @GetMapping
    @Operation(summary = "Listar todos os pátios", tags = "Patio")
    public List<PatioDTO> listar() {
        log.info("Listando todos os pátios");
        return patioRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pátio por id", tags = "Patio")
    public PatioDTO buscar(@PathVariable Long id) {
        log.info("Buscando pátio id {}", id);
        return getPatio(id).map(this::toDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pátio não encontrado"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar novo pátio", tags = "Patio")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    public PatioDTO criar(@RequestBody @Valid PatioDTO dto) {
        log.info("Criando pátio {}", dto.nome());
        Patio patio = new Patio();
        patio.setNome(dto.nome());
        patio.setQuantidadeVagas(dto.quantidadeVagas());
        patio.setMetragemZonaA(dto.metragemZonaA());
        patio.setMetragemZonaB(dto.metragemZonaB());
        return toDTO(patioRepository.save(patio));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar pátio", tags = "Patio")
    public PatioDTO atualizar(@PathVariable Long id, @RequestBody @Valid PatioDTO dto) {
        log.info("Atualizando pátio id {}", id);
        Patio patio = getPatio(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pátio não encontrado"));

        patio.setNome(dto.nome());
        patio.setQuantidadeVagas(dto.quantidadeVagas());
        patio.setMetragemZonaA(dto.metragemZonaA());
        patio.setMetragemZonaB(dto.metragemZonaB());

        return toDTO(patioRepository.save(patio));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Excluir pátio", tags = "Patio")
    public void excluir(@PathVariable Long id) {
        log.info("Excluindo pátio id {}", id);
        Patio patio = getPatio(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pátio não encontrado"));
        patioRepository.delete(patio);
    }


    private java.util.Optional<Patio> getPatio(Long id) {
        return patioRepository.findById(id);
    }

    private PatioDTO toDTO(Patio patio) {
        return new PatioDTO(
                patio.getId(),
                patio.getNome(),
                patio.getQuantidadeVagas(),
                patio.getMetragemZonaA(),
                patio.getMetragemZonaB()
        );
    }
}
