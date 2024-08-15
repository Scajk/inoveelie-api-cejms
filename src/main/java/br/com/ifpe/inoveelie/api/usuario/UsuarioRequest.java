package br.com.ifpe.inoveelie.api.usuario;

import java.util.Arrays;

import br.com.ifpe.inoveelie.modelo.usuario.Usuario;
import br.com.ifpe.inoveelie.validator.SenhaValida;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequest {

    @NotBlank(message = "O e-mail é de preenchimento obrigatório")
    @Email
    private String email;

    @NotBlank(message = "A senha é de preenchimento obrigatório")
    @SenhaValida
    private String password;

    @NotBlank(message = "A senha é de preenchimento obrigatório")
    private String confirmaPassword;

    private String nome;

    private String sobrenome;

    public Usuario build() {

        Usuario c = Usuario.builder()
                .username(email)
                .password(password)
                .confirmaPassword(confirmaPassword)
                .roles(Arrays.asList(Usuario.ROLE_USUARIO))
                .nome(nome)
                .email(email)
                .sobrenome(sobrenome)
                .build();

        return c;
    }

}
