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

            Zona zonaA = new Zona("Zona A");
            Zona zonaB = new Zona("Zona B");
            zonaRepo.saveAll(List.of(zonaA, zonaB));

            Moto moto1 = new Moto("ABC1234", "Honda CG 160", "ATIVO");     moto1.setZona(zonaA);
            Moto moto2 = new Moto("XYZ5678", "Yamaha Fazer 250", "INATIVO"); moto2.setZona(zonaB);
            Moto moto3 = new Moto("JKL9123", "Honda Biz 125", "ATIVO");     moto3.setZona(zonaA);
            Moto moto4 = new Moto("QWE3456", "Yamaha XTZ 150", "ATIVO");    moto4.setZona(zonaB);
            Moto moto5 = new Moto("RTY7890", "Suzuki Burgman", "INATIVO");  moto5.setZona(zonaA);
            Moto moto6 = new Moto("UIO4567", "Haojue DK 150", "ATIVO");     moto6.setZona(zonaB);
            Moto moto7 = new Moto("MNB3210", "Shineray XY 50", "ATIVO");    moto7.setZona(zonaA);
            Moto moto8 = new Moto("ZXC9876", "Honda Pop 110i", "INATIVO");  moto8.setZona(zonaB);
            Moto moto9 = new Moto("ASD6543", "Yamaha NMax 160", "ATIVO");   moto9.setZona(zonaA);
            Moto moto10 = new Moto("FGH8520", "Honda PCX 150", "INATIVO");  moto10.setZona(zonaB);

            motoRepo.saveAll(List.of(
                moto1, moto2, moto3, moto4, moto5,
                moto6, moto7, moto8, moto9, moto10
            ));

            Gateway gateway1 = new Gateway("00:11:22:33:44:55", "Pátio Central", zonaA);
            Gateway gateway2 = new Gateway("AA:BB:CC:DD:EE:FF", "Manutenção Lateral", zonaB);
            gatewayRepo.saveAll(List.of(gateway1, gateway2));

            EventoWifi evento1 = new EventoWifi(moto1, gateway1, -45);
            EventoWifi evento2 = new EventoWifi(moto2, gateway2, -60);
            eventoWifiRepo.saveAll(List.of(evento1, evento2));

            EventoAlpr alpr1 = new EventoAlpr(moto1, "ABC1234", "http://camera1/imagem.jpg", LocalDateTime.now());
            EventoAlpr alpr2 = new EventoAlpr(moto2, "XYZ5678", "http://camera2/imagem.jpg", LocalDateTime.now());
            eventoAlprRepo.saveAll(List.of(alpr1, alpr2));

            System.out.println("***Dados inseridos com sucesso.****");
        };
    }
}
