package fiap.com.br.future_stack.zona;

import fiap.com.br.future_stack.patio.Patio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ZonaRepository extends JpaRepository<Zona, Long> {
    Optional<Zona> findByTipoZonaAndPatio(TipoZona tipoZona, Patio patio);
    Optional<Zona> findByIdAndPatioId(Long zonaId, Long patioId);
    boolean existsByIdAndPatioId(Long zonaId, Long patioId);
    List<Zona> findByPatioId(Long patioId);
    boolean existsByPatioIdAndTipoZona(Long patioId, TipoZona tipoZona);
}
