package fiap.com.br.future_stack.config;

import fiap.com.br.future_stack.model.*;
import fiap.com.br.future_stack.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class DatabaseSeeder {

    @Bean
    CommandLineRunner initDatabase(
            MotoRepository motoRepo,
            ZonaRepository zonaRepo,
            GatewayRepository gatewayRepo,
            EventoWifiRepository eventoWifiRepo,
            EventoAlprRepository eventoAlprRepo,
            PatioRepository patioRepo
    ) {
        return args -> {

            Patio patioCentral = new Patio("Pátio Central", "Unidade São Paulo");
            Patio patioSecundario = new Patio("Pátio Manutenção", "Unidade Campinas");
            patioRepo.saveAll(List.of(patioCentral, patioSecundario));

            // Zonas
            Zona zonaA = new Zona("Zona A");
            Zona zonaB = new Zona("Zona B");
            zonaRepo.saveAll(List.of(zonaA, zonaB));

            // Motos
            Moto moto1 = new Moto("ABC1234", "Honda CG", "Preto", "ATIVO");
            moto1.setZona(zonaA);

            Moto moto2 = new Moto("XYZ5678", "Yamaha Fazer", "Azul", "INATIVO");
            moto2.setZona(zonaB);

            motoRepo.saveAll(List.of(moto1, moto2));

            // Gateways
            Gateway gateway1 = new Gateway("00:11:22:33:44:55", "Pátio Central", zonaA);
            Gateway gateway2 = new Gateway("AA:BB:CC:DD:EE:FF", "Manutenção Lateral", zonaB);
            gatewayRepo.saveAll(List.of(gateway1, gateway2));

            // Evento Wi-Fi
            EventoWifi evento1 = new EventoWifi(moto1, gateway1, -45);
            EventoWifi evento2 = new EventoWifi(moto2, gateway2, -60);
            eventoWifiRepo.saveAll(List.of(evento1, evento2));

            // Evento ALPR
            EventoAlpr alpr1 = new EventoAlpr(moto1, "ABC1234", "http://camera1/imagem.jpg", LocalDateTime.now());
            EventoAlpr alpr2 = new EventoAlpr(moto2, "XYZ5678", "http://camera2/imagem.jpg", LocalDateTime.now());
            eventoAlprRepo.saveAll(List.of(alpr1, alpr2));

            System.out.println("✅ Dados seed inseridos com sucesso.");
        };
    }
}
