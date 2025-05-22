package fiap.com.br.future_stack.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import fiap.com.br.future_stack.model.Moto;

public interface MotoRepository extends JpaRepository<Moto, Long> {
    List<Moto> findByModeloContainingIgnoreCase(String modelo);
    List<Moto> findByPlaca(String placa);
    Page<Moto> findAll(Pageable pageable);
}