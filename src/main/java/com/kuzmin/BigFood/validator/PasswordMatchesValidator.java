package com.kuzmin.BigFood.validator;

import com.kuzmin.BigFood.model.PasswordMatches;
import com.kuzmin.BigFood.model.RegistrationForm;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator
        implements ConstraintValidator<PasswordMatches, RegistrationForm> {

    @Override
    public boolean isValid(RegistrationForm form, ConstraintValidatorContext context) {
        if (form == null || form.getUser() == null) {
            return true;
        }

        String password = form.getUser().getPassword();
        String confirm = form.getConfirmPassword();

        if (password == null || confirm == null) {
            return true;
        }

        boolean matches = password.equals(confirm);

        if (!matches) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Пароли не совпадают")
                    .addPropertyNode("confirmPassword")
                    .addConstraintViolation();
        }

        return matches;
    }

}

