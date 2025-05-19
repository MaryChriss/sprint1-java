package fiap.com.br.future_stack.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fiap.com.br.future_stack.model.User;

public interface UserRepository  extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String username);
}
