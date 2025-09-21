package fiap.com.br.future_stack.config;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.*;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;

@Configuration
@Profile("repair-once")
public class FlywayRepairConfig {
    @Bean
    FlywayMigrationStrategy repairThenMigrate() {
        return flyway -> { flyway.repair(); flyway.migrate(); };
    }
}