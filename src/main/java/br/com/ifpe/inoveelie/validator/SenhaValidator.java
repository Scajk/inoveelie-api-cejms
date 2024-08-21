/* package br.com.ifpe.inoveelie.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SenhaValidator implements ConstraintValidator<SenhaValida, String> {

    private static final String NUMEROS_SEQUENCIA_REGEX = ".*(012|123|234|345|456|567|678|789).*";

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null || password.length() < 6) {
            return false;
        }
        if (!password.matches(".*[A-Za-z].*") || !password.matches(".*\\d.*")) {
            return false;
        }
        if (password.matches(NUMEROS_SEQUENCIA_REGEX)) {
            return false;
        }
        return true;
    }
} */

    

package br.com.ifpe.inoveelie.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SenhaValidator implements ConstraintValidator<SenhaValida, String> {

    private static final String NUMEROS_SEQUENCIA_REGEX = ".*(012|123|234|345|456|567|678|789).*";

    @Override
    public void initialize(SenhaValida constraintAnnotation) {
        // Inicialização, se necessário
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        StringBuilder mensagensErro = new StringBuilder();

        // Verifica se a senha é nula ou tem menos de 6 caracteres
        if (password == null || password.length() < 6) {
            mensagensErro.append("A senha deve ter pelo menos 6 caracteres. ");
        }

        // Verifica se a senha contém pelo menos uma letra e um número
        if (!password.matches(".*[A-Za-z].*")) {
            mensagensErro.append("A senha deve conter pelo menos uma letra. ");
        }
        if (!password.matches(".*\\d.*")) {
            mensagensErro.append("A senha deve conter pelo menos um número. ");
        }

        // Verifica se a senha contém números em sequência
        if (password.matches(NUMEROS_SEQUENCIA_REGEX)) {
            mensagensErro.append("A senha não deve conter números em sequência. ");
        }

        return true;
    }
}
