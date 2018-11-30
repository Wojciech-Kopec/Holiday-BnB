package com.kopec.wojciech.enginners_thesis.model;

import com.kopec.wojciech.enginners_thesis.validation.ExtendedEmailValidator;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;


public class ValidatorTests {
    private static Logger logger = LoggerFactory.getLogger(ValidatorTests.class);

    private Validator createValidator() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        return localValidatorFactoryBean;
    }

    @Test
    public void shouldNotValidateEmail() {
        User user = new User();

        String[] invalidInputs = new String[]{
                "invalidEMail",
                "invalidEMail@",
                "@invalidEMail",
                "invalid@EMail",
                "invalid@EMail@",
                ".@...",
                "plainaddress",
                "#@%^%#$@#$@#.com",
                "@example.com",
                "Joe Smith <email@example.com>",
                "email.example.com",
                "email@example@example.com",
                ".email@example.com",
                "email.@example.com",
                "email..email@example.com",
                "email@example.com (Joe Smith)",
                "email@example",
                "email@-example.com",
                "email@example..com",
                "Abc..123@example.com",
                "\"(),:;<>[\\]@example.com",
                "just\"not\"right@example.com",
                "this\\ is\"really\"not\\\\allowed@example.com",
//                "あいうえお@example.com",
//                "email@example.web",
//                "email@111.222.333.44444",
        };
        Set<String> invalidEmails = new HashSet<>(Arrays.asList(invalidInputs));
        for (String email : invalidEmails) {
            user.setEmail(email);
            logger.info("Testing for value: " + email);
            assertValidation(user, false);
        }
    }

    @Test
    public void shouldValidateEmail() {
        User user = new User();

        String[] validInputs = new String[]{
                "email@example.com",
                "firstname.lastname@example.com",
                "email@subdomain.example.com",
                "firstname+lastname@example.com",
                "email@123.123.123.123",
                "email@[123.123.123.123]",
                "\"email\"@example.com",
                "1234567890@example.com",
                "email@example-one.com",
                "_______@example.com",
                "email@example.name",
                "email@example.museum",
                "email@example.co.jp",
                "firstname-lastname@example.com",
                "very.unusual.\"@\".unusual.com@example.com",
//                "much.\"more\\ unusual\"@example.com",
//                "very.\"(),:;<>[]\".VERY.\"very@\\\\\\ \"very\".unusual@strange.example.com",
        };
        Set<String> invalidEmails = new HashSet<>(Arrays.asList(validInputs));
        for (String email : invalidEmails) {
            user.setEmail(email);
            logger.info("Testing for value: " + email);
            assertValidation(user, true);
        }

    }

    private void assertValidation(User user, boolean shouldBeValid) {
        final String EMAIL_NOT_VALID_MSG = "Please provide a valid email address";

        Validator validator = createValidator();
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

        if (!shouldBeValid) {
            //sometimes more than one violation is returned, can't tell why
            assertThat(constraintViolations.size(), is(greaterThanOrEqualTo(1)));
            ConstraintViolation<User> violation = constraintViolations.iterator().next();
            assertThat(violation.getPropertyPath().toString(), is("email"));
            assertThat(violation.getMessage(), is(EMAIL_NOT_VALID_MSG));
        } else if (shouldBeValid) {
            assertThat(constraintViolations.size(), is(0));
        }
    }
}
