package fiap.com.br.future_stack.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserUpdateDTO {

    @Email(message = "E-mail inválido")
    private String email;

    @Pattern(
            regexp = "^[A-Z].*",
            message = "deve começar com maiúscula"
    )
    private String nomeUser;

    @Pattern(
            regexp = "^\\+?\\d{10,14}$",
            message = "Telefone deve conter apenas dígitos (com opcional +código do país) e ter 10 a 14 dígitos."
    )
    private String phone;
}