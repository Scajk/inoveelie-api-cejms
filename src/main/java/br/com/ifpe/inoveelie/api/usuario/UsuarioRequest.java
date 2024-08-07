package br.com.ifpe.inoveelie.api.usuario;

import java.util.Arrays;

import br.com.ifpe.inoveelie.modelo.usuario.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

    // @NotBlank(message = "A senha é de preenchimento obrigatório")
    private String password;

    // @NotBlank -> Válida se o campo está nulo ou vazio.
    @NotNull(message = "O E-mail é de preenchimento obrigatório")
    @NotEmpty(message = "O E-mail é de preenchimento obrigatório")
    private String nome;

    private String sobrenome;

    @NotNull(message = "O E-mail é de preenchimento obrigatório")
    @NotEmpty(message = "O E-mail é de preenchimento obrigatório")
    private String foneCelular;

    public Usuario build() {

        Usuario c = Usuario.builder()
                .username(email)
                .password(password)
                .roles(Arrays.asList(Usuario.ROLE_USUARIO))
                .nome(nome)
                .email(email)
                .sobrenome(sobrenome)
                .foneCelular(foneCelular)
                .build();

        return c;
    }

}
