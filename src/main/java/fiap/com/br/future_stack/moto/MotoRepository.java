package fiap.com.br.future_stack.moto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MotoRepository extends JpaRepository<Moto, Long> {
    Page<Moto> findByModeloContainingIgnoreCase(String modelo, Pageable pageable);
    Page<Moto> findByPlacaContainingIgnoreCase(String placa, Pageable pageable);
    Page<Moto> findByModeloContainingIgnoreCaseAndPlacaContainingIgnoreCase(String modelo, String placa, Pageable pageable);
    Page<Moto> findAll(Pageable pageable);
}