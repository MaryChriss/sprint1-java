package fiap.com.br.future_stack.config;

import fiap.com.br.future_stack.model.*;
import fiap.com.br.future_stack.repository.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class DatabaseSeeder {

    @Autowired
    private MotoRepository motoRepo;

    @Autowired
    private ZonaRepository zonaRepo;

    @Autowired
    private GatewayRepository gatewayRepo;

    @Autowired
    private EventoWifiRepository eventoWifiRepo;

    @Autowired
    private EventoAlprRepository eventoAlprRepo;

    @Autowired
    private PatioRepository patioRepo;

    @PostConstruct
    public void init() {

        Patio patio1 = new Patio();
        patio1.setNome("Filial São Paulo");
        patio1.setQuantidadeVagas(60);
        patio1.setMetragemZonaA(200.0);
        patio1.setMetragemZonaB(120.0);

        Patio patio2 = new Patio();
        patio2.setNome("Filial Rio");
        patio2.setQuantidadeVagas(40);
        patio2.setMetragemZonaA(150.0);
        patio2.setMetragemZonaB(100.0);

        patioRepo.saveAll(List.of(patio1, patio2));

        Zona zonaA = Zona.builder()
        .nome("Zona A")
        .tipo(TipoZona.A)
        .patio(patio1)
        .metragem(patio1.getMetragemZonaA())
        .build();

        Zona zonaB = Zona.builder()
                .nome("Zona B")
                .tipo(TipoZona.B)
                .patio(patio1)
                .metragem(patio1.getMetragemZonaB())
                .build();

        zonaRepo.saveAll(List.of(zonaA, zonaB));

        Moto moto1 = Moto.builder()
        .modelo("Honda CG 160")
        .placa("ABC1234")
        .status(StatusMoto.DISPONIVEL)
        .zona(zonaA)
        .build();

    Moto moto2 = Moto.builder()
        .modelo("Yamaha Fazer 250")
        .placa("XYZ5678")
        .status(StatusMoto.MANUTENCAO)
        .zona(zonaB)
        .build();

    Moto moto3 = Moto.builder()
        .modelo("Honda Biz 125")
        .placa("JKL9123")
        .status(StatusMoto.MANUTENCAO)
        .zona(zonaA)
        .build();

    Moto moto4 = Moto.builder()
        .modelo("Yamaha XTZ 150")
        .placa("QWE3456")
        .status(StatusMoto.DISPONIVEL)
        .zona(zonaB)
        .build();

    Moto moto5 = Moto.builder()
        .modelo("Suzuki Burgman")
        .placa("RTY7890")
        .status(StatusMoto.DISPONIVEL)
        .zona(zonaA)
        .build();

    Moto moto6 = Moto.builder()
        .modelo("Haojue DK 150")
        .placa("UIO4567")
        .status(StatusMoto.DISPONIVEL)
        .zona(zonaB)
        .build();

    Moto moto7 = Moto.builder()
        .modelo("Shineray XY 50")
        .placa("MNB3210")
        .status(StatusMoto.ALUGADA)
        .zona(zonaA)
        .build();

    Moto moto8 = Moto.builder()
        .modelo("Honda Pop 110i")
        .placa("ZXC9876")
        .status(StatusMoto.MANUTENCAO)
        .zona(zonaB)
        .build();

    Moto moto9 = Moto.builder()
        .modelo("Yamaha NMax 160")
        .placa("ASD6543")
        .status(StatusMoto.MANUTENCAO)
        .zona(zonaA)
        .build();

    Moto moto10 = Moto.builder()
        .modelo("Honda PCX 150")
        .placa("FGH8520")
        .status(StatusMoto.ALUGADA)
        .zona(zonaB)
        .build();

    motoRepo.saveAll(List.of(moto1, moto2, moto3, moto4, moto5, moto6, moto7, moto8, moto9, moto10));


        Gateway gateway1 = Gateway.builder()
            .mac_address("00:11:22:33:44:55")
            .descricao("Pátio Central")
            .localid_zona(zonaA)
            .build();

        Gateway gateway2 = Gateway.builder()
            .mac_address("AA:BB:CC:DD:EE:FF")
            .descricao("Manutenção Lateral")
            .localid_zona(zonaB)
            .build();

        gatewayRepo.saveAll(List.of(gateway1, gateway2));

        EventoWifi evento1 = EventoWifi.builder()
            .moto(moto1)
            .gateway(gateway1)
            .rssits_evento(-45)
            .build();

        EventoWifi evento2 = EventoWifi.builder()
            .moto(moto2)
            .gateway(gateway2)
            .rssits_evento(-60)
            .build();

        eventoWifiRepo.saveAll(List.of(evento1, evento2));

        EventoAlpr alpr1 = EventoAlpr.builder()
            .moto(moto1)
            .placa_lida("ABC1234")
            .url_imagem("http://camera1/imagem.jpg")
            .ts_alpr(LocalDateTime.now())
            .build();

        EventoAlpr alpr2 = EventoAlpr.builder()
            .moto(moto2)
            .placa_lida("XYZ5678")
            .url_imagem("http://camera2/imagem.jpg")
            .ts_alpr(LocalDateTime.now())
            .build();

        eventoAlprRepo.saveAll(List.of(alpr1, alpr2));

        System.out.println("*** Dados inseridos com sucesso! ***");
    }
}
