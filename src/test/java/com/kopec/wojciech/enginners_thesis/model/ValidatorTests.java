package com.kopec.wojciech.enginners_thesis.model;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


public class ValidatorTests {
private static Logger logger = LoggerFactory.getLogger(ValidatorTests.class);

    private Validator createValidator() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        return localValidatorFactoryBean;
    }

    @Test
    public void shouldValidateEMailProperly() {

        User user = new User();
        String[] invalidInputs = new String[]{
                "invalidEMail",
                "invalidEMail@",
                "@invalidEMail",
                "invalid@EMail",
                "invalid@EMail@"
        };
        Set<String> invalidEmails = new HashSet<>(Arrays.asList(invalidInputs));
        for (String email : invalidEmails) {
            user.setEmail(email);
            logger.info("Testing for value: " + email);
            validate(user);
        }
    }

    private void validate(User user) {
        Validator validator = createValidator();
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

//        assertThat(constraintViolations.size()).isEqualTo(1);
        ConstraintViolation<User> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("email");
        assertThat(violation.getMessage()).isEqualTo("may not be empty");
    }
}

/*

        LocaleContextHolder.setLocale(Locale.ENGLISH);
                Person person = new Person();
                person.setFirstName("");
                person.setLastName("smith");

                Validator validator = createValidator();
                Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);

        assertThat(constraintViolations.size()).isEqualTo(1);
        ConstraintViolation<Person> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("firstName");
        assertThat(violation.getMessage()).isEqualTo("may not be empty");
        */
