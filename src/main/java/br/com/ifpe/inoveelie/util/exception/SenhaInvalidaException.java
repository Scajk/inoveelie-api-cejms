package br.com.ifpe.inoveelie.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class SenhaInvalidaException extends RuntimeException {
 
    public static final String MSG_SENHAS_NAO_CONFEREM = "As senhas devem ser idênticas";

    public SenhaInvalidaException(String MSG_SENHAS_NAO_CONFEREM) {
        super(MSG_SENHAS_NAO_CONFEREM);
    }

} 


/* @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class UsuarioException extends RuntimeException {
 
    public static final String MSG_SENHAS_NAO_CONFEREM = "As senhas devem ser idênticas";

    public UsuarioException(String msg) {

	    super(String.format(msg));
    }

}  */

/* public class SenhaInvalidaException extends RuntimeException {
    public SenhaInvalidaException(String mensagem) {
        super(mensagem);
    }
} */




