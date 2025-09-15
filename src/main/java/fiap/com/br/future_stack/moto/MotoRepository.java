package fiap.com.br.future_stack.moto;


import fiap.com.br.future_stack.patio.Patio;
import fiap.com.br.future_stack.zona.Zona;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MotoRepository extends JpaRepository<Moto, Long> {
    Page<Moto> findByModeloContainingIgnoreCaseAndPlacaContainingIgnoreCase(String modelo, String placa, Pageable pageable);
    Page<Moto> findAll(Pageable pageable);
    int countByPatioAndZona(Patio patio, Zona zona);
    Page<Moto> findByZona_Patio_IdAndModeloContainingIgnoreCaseAndPlacaContainingIgnoreCase(
            Long patioId, String modelo, String placa, Pageable pageable);

}