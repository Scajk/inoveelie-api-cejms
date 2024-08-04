package br.com.ifpe.inoveelie.api.usuario;

import org.hibernate.validator.constraints.Length;

import br.com.ifpe.inoveelie.modelo.usuario.Usuario;
import jakarta.validation.constraints.Email;
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

    //@NotBlank -> Válida se o campo está nulo ou vazio.
    @NotNull(message = "O E-mail é de preenchimento obrigatório")
    @NotEmpty(message = "O E-mail é de preenchimento obrigatório")
    @Length(max = 50, message = "O E-mail deverá ter no máximo 50 caracteres")
    @Email
    private String email;

    @NotNull(message = "O CPF é de preenchimento obrigatório")
    @NotEmpty(message = "O CPF é de preenchimento obrigatório")
    @Length(min = 6, message = "O campo Senha tem que ter no mínimo 6 caracteres")
    private String senha;

    @Length(min = 6, message = "O campo Confirmar Senha tem que ter no mínimo 6 caracteres")
    private String confirmaSenha;

    public Usuario build() {

        Usuario c = Usuario.builder()
            .email(email)
            .senha(senha)
            .confirmaSenha(confirmaSenha)
            .build();

        return c;
    }

}
