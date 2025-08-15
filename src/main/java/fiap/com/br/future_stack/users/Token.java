package fiap.com.br.future_stack.users;

public record Token(
    String token, 
    String type,
    String email
) {}
