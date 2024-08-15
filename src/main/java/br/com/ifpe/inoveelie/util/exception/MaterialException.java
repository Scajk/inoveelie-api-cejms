package br.com.ifpe.inoveelie.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class MaterialException extends RuntimeException {
  
  public static final String MSG_QTD_INSUFICIENTE = "Materiais em falta ou não cadastrados.";

    public MaterialException(String msg) {

	super(String.format(msg));
    }
}