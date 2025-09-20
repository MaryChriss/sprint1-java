package fiap.com.br.future_stack.patio;

import fiap.com.br.future_stack.moto.MotoDTO;
import fiap.com.br.future_stack.patio.PatioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patios")
@Slf4j
@RequiredArgsConstructor
public class PatioController {

    @Autowired
    private PatioService patioService;

    @GetMapping
    @Operation(summary = "Listar todos os pátios", tags = "Patio")
    public List<PatioDTO> listar() {
        return patioService.listAllDTO();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pátio por id", tags = "Patio")
    public PatioDTO buscar(@PathVariable Long id) {
        return patioService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar novo pátio", tags = "Patio")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    public PatioDTO criar(@RequestBody @Valid PatioDTO dto) {
        return patioService.createPatio(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar pátio", tags = "Patio")
    public PatioDTO atualizar(@PathVariable Long id, @RequestBody @Valid PatioDTO dto) {
        return patioService.updatePatio(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Excluir pátio", tags = "Patio")
    public void excluir(@PathVariable Long id) {
        patioService.delete(id);
    }

    @GetMapping("/{patioId}/motos")
    @Operation(summary = "Listar motos de um pátio (com filtros)", tags = "Patio")
    public Page<MotoDTO> listarMotosDoPatio(@PathVariable Long patioId,
                                            @RequestParam(required = false) String modelo,
                                            @RequestParam(required = false) String placa,
                                            Pageable pageable) {
        return patioService.listarMotosDoPatio(patioId, modelo, placa, pageable);
    }

    @GetMapping("/{id}/ocupacao")
    @Operation(summary = "Obter ocupação do pátio por zona", tags = "Patio")
    public ResponseEntity<OcupacaoDTO> getOcupacao(@PathVariable Long id) {
        return ResponseEntity.ok(patioService.getOcupacao(id));
    }
}
