package fiap.com.br.future_stack.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(repository.findById(id));
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody @Valid User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = repository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody @Valid UserUpdateDTO dto) {
    User user = repository.findById(id)
        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

    if (dto.getEmail() != null && !dto.getEmail().isBlank()) {
        user.setEmail(dto.getEmail());
    }

    if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
    }

    return repository.save(user);
}

}
