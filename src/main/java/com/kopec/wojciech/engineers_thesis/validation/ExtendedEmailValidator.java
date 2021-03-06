package com.kopec.wojciech.engineers_thesis.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Email(message="{constraint.emailNotValid}")
@Pattern(regexp=".+@.+\\..+", message="{constraint.emailNotValid}")
@Target( { METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface ExtendedEmailValidator {
    String message() default "{constraint.emailNotValid}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
