package fiap.com.br.future_stack.config;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import fiap.com.br.future_stack.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

                System.out.println("passando pelo filtro");

                //pegar o header
                var header = request.getHeader("Authorization");
                if (header == null){
                    filterChain.doFilter(request, response);
                    return;
                }

                //verificar se é Bearer
                if(!header.startsWith("Bearer ")){
                    response.setStatus(401);
                    response.getWriter().write("""
                            { "message": "token deve começar com Bearer" }
                    """);
                    return;
                }

                //validar JWT
                var jwt = header.replace("Bearer ", "");
                var user = tokenService.getUserFromToken(jwt);
                System.out.println(user);

                //autenticar
                var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);

                filterChain.doFilter(request, response);
        
    }


    
}
