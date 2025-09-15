package fiap.com.br.future_stack.patio;

import fiap.com.br.future_stack.moto.Moto;
import fiap.com.br.future_stack.moto.MotoDTO;
import fiap.com.br.future_stack.moto.MotoRepository;
import fiap.com.br.future_stack.moto.MotoService;
import fiap.com.br.future_stack.patio.PatioRepository;
import fiap.com.br.future_stack.zona.TipoZona;
import fiap.com.br.future_stack.zona.Zona;
import fiap.com.br.future_stack.zona.ZonaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatioService {

    private final PatioRepository patioRepository;
    private final MotoRepository motoRepository;
    private final ZonaRepository zonaRepository;
    private final MotoService motoService;

    @Transactional
    public PatioDTO createPatio(PatioDTO patioDTO) {
        String nome = patioDTO.getNome().trim();

        if (patioRepository.existsByNomeIgnoreCase(nome)) {
            throw new DuplicateKeyException("patio.nome.unique");
        }

        Patio patio = mapearPatio(new Patio(), patioDTO);
        patio = patioRepository.save(patio);

        criarZonaSeNaoExiste(patio, TipoZona.A, "Zona A", patioDTO.metragemZonaA);
        criarZonaSeNaoExiste(patio, TipoZona.B, "Zona B", patioDTO.metragemZonaB);

        return toDTO(patio);
    }

    @Transactional
    public PatioDTO updatePatio(Long id, PatioDTO patioDTO) {

        String nome = patioDTO.getNome().trim();
        if (patioRepository.existsByNomeIgnoreCaseAndIdNot(nome, id)) {
            throw new DuplicateKeyException("patio.nome.unique");
        }

        Patio patio = patioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pátio não encontrado"));

        mapearPatio(patio, patioDTO);
        patioRepository.save(patio);

        if (patioDTO.metragemZonaA != null) {
            criarOuAtualizarZona(patio, TipoZona.A, "Zona A", patioDTO.metragemZonaA);
        }
        if (patioDTO.metragemZonaB != null) {
            criarOuAtualizarZona(patio, TipoZona.B, "Zona B", patioDTO.metragemZonaB);
        }
        return toDTO(patio);
    }

    @Transactional
    public void delete(Long id) {
        Patio patio = patioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pátio não encontrado"));
        patioRepository.delete(patio);
    }

    @Transactional
    public PatioDTO getById(Long id) {
        Patio patio = patioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pátio não encontrado"));
        return toDTO(patio);
    }

    @Transactional
    public List<PatioDTO> listAllDTO() {
        return patioRepository.findAll().stream().map(this::toDTO).toList();
    }

    @Transactional
    public Page<MotoDTO> listarMotosDoPatio(Long patioId, String modelo, String placa, Pageable pageable) {
        String filtroModelo = (modelo != null && !modelo.isBlank()) ? modelo : "";
        String filtroPlaca  = (placa  != null && !placa.isBlank())  ? placa  : "";

        Page<Moto> page = motoRepository
                .findByZona_Patio_IdAndModeloContainingIgnoreCaseAndPlacaContainingIgnoreCase(
                        patioId, filtroModelo, filtroPlaca, pageable);

        return page.map(motoService::toDTO);
    }

    @Transactional
    public OcupacaoDTO getOcupacao(Long id) {
        Patio patio = patioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pátio não encontrado"));

        Zona zonaA = zonaRepository.findByTipoZonaAndPatio(TipoZona.A, patio)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Zona A não encontrada"));

        Zona zonaB = zonaRepository.findByTipoZonaAndPatio(TipoZona.B, patio)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Zona B não encontrada"));

        int motosZonaA = motoRepository.countByPatioAndZona(patio, zonaA);
        int motosZonaB = motoRepository.countByPatioAndZona(patio, zonaB);
        int totalMotos = motosZonaA + motosZonaB;

        return new OcupacaoDTO(totalMotos, motosZonaA, motosZonaB, patio.getQuantidadeVagas());
    }

    public PatioDTO toDTO(Patio patio) {
        return new PatioDTO(
                patio.getId(),
                patio.getNome(),
                patio.getQuantidadeVagas(),
                patio.getMetragemZonaA(),
                patio.getMetragemZonaB()
        );
    }

    private Patio mapearPatio(Patio patio, PatioDTO dto) {
        patio.setNome(dto.nome);
        patio.setQuantidadeVagas(dto.quantidadeVagas);
        patio.setMetragemZonaA(dto.metragemZonaA);
        patio.setMetragemZonaB(dto.metragemZonaB);
        return patio;
    }

    private void criarZonaSeNaoExiste(Patio patio, TipoZona tipo, String nome, Double metragem) {
        if (!zonaRepository.existsByPatioIdAndTipoZona(patio.getId(), tipo)) {
            Zona z = new Zona();
            z.setNome(nome);
            z.setTipoZona(tipo);
            z.setMetragem(metragem);
            z.setPatio(patio);
            zonaRepository.save(z);
        }
    }

    private void criarOuAtualizarZona(Patio patio, TipoZona tipo, String nome, Double metragem) {
        Zona z = zonaRepository.findByTipoZonaAndPatio(tipo, patio)
                .orElseGet(() -> {
                    Zona nova = new Zona();
                    nova.setPatio(patio);
                    nova.setTipoZona(tipo);
                    nova.setNome(nome);
                    return nova;
                });
        z.setMetragem(metragem);
        zonaRepository.save(z);
    }
}
