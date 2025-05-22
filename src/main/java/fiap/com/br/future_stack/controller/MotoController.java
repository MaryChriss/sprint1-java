package fiap.com.br.future_stack.controller;

import fiap.com.br.future_stack.dto.MotoDTO;
import fiap.com.br.future_stack.service.MotoService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/motos")
public class MotoController {

    @Autowired
    private MotoService service;

    @GetMapping
    @Cacheable("motos")
    public Page<MotoDTO> listar(Pageable pageable) {
        return service.listar(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MotoDTO> buscar(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @CacheEvict(value = "motos", allEntries = true)
    @PostMapping
    public MotoDTO criar(@RequestBody MotoDTO dto) {
        return service.salvar(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @CacheEvict(value = "motos", allEntries = true)
    @PutMapping("/{id}")
    public ResponseEntity<MotoDTO> editar(@PathVariable Long id, @RequestBody @Valid MotoDTO dto) {
        try {
            MotoDTO motoAtualizada = service.atualizar(id, dto);
            return ResponseEntity.ok(motoAtualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
