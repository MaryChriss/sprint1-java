package fiap.com.br.future_stack.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserUpdateDTO {

    @Email(message = "E-mail inválido")
    private String email;

    @NotBlank(message = "Senha não pode estar em branco")
    private String password;
}