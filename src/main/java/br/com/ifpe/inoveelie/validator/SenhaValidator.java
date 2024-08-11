package br.com.ifpe.inoveelie.validator;

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

    /* @Override
    public void initialize(SenhaValida constraintAnnotation) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null || password.length() < 6) {
            return false;
        }

        

        boolean hasLetter = false;
        boolean hasDigit = false;

        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) {
                hasLetter = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            }
        }

        if (!hasLetter || !hasDigit) {
            return false;
        }

        // Verifica se há números em sequência
        for (int i = 0; i < password.length() - 1; i++) {
            if (Character.isDigit(password.charAt(i)) && Character.isDigit(password.charAt(i + 1))) {
                int num1 = Character.getNumericValue(password.charAt(i));
                int num2 = Character.getNumericValue(password.charAt(i + 1));
                if (num2 == num1 + 1) {
                    return false;
                }
            }
        }

        return true;
    } */ 
}
