package org.libermundi.theorcs.validation;

import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;

import com.google.common.base.Joiner;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

	@Override
	public void initialize(ValidPassword constraintAnnotation) {
	}

	@Override
	public boolean isValid(String password, ConstraintValidatorContext context) {
		PasswordValidator validator = new PasswordValidator(Arrays.asList(
           new LengthRule(8, 30), 
           new CharacterRule(EnglishCharacterData.UpperCase),
           new CharacterRule(EnglishCharacterData.Digit),
           new CharacterRule(EnglishCharacterData.Special),
           new WhitespaceRule()));
 
        RuleResult result = validator.validate(new PasswordData(password));
        
        if (result.isValid()) {
            return true;
        }
        
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(
          Joiner.on("n").join(validator.getMessages(result)))
          .addConstraintViolation();
        
        return false;
	}

}
