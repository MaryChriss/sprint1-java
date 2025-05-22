package fiap.com.br.future_stack.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fiap.com.br.future_stack.model.Gateway;

public interface GatewayRepository extends JpaRepository<Gateway, Long> {}