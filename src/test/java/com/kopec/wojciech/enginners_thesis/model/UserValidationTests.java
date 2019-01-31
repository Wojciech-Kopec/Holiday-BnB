package com.kopec.wojciech.enginners_thesis.model;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class UserValidationTests extends AbstractValidationTest<User> {
    private final Logger logger = LoggerFactory.getLogger(UserValidationTests.class);

    @Test
    public void shouldNotValidateNulls() {
        User user = new User();
        assertObjectsConstraintsViolationCount(user, 6);
    }

    @Test
    public void shouldNotValidateSizesNotInRange() {
        User user1 = User.builder()
                .username(stringWithSize(4))
                .firstName(stringWithSize(4))
                .lastName(stringWithSize(4))
                .phoneNumber(stringWithSize(6))
                .password(stringWithSize(4))
                .email("valid@email.com")
                .build();

        User user2 = User.builder()
                .username(stringWithSize(51))
                .firstName(stringWithSize(51))
                .lastName(stringWithSize(51))
                .phoneNumber(stringWithSize(15))
                .password(stringWithSize(101))
                .email("valid@email.com")
                .build();

        assertObjectsConstraintsViolationCount(user1, 6);
        assertObjectsConstraintsViolationCount(user2, 6);
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
        };

        Set<String> invalidEmails = new HashSet<>(Arrays.asList(invalidInputs));
        for (String email : invalidEmails) {
            user.setEmail(email);
            logger.info("Testing for value: " + email);
            final String EMAIL_NOT_VALID_MSG = "{constraint.emailNotValid}";
            assertFieldValidation(user, "email", false, EMAIL_NOT_VALID_MSG);
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
                "very.unusual.\"@\".unusual.com@example.com"
        };

        Set<String> invalidEmails = new HashSet<>(Arrays.asList(validInputs));
        for (String email : invalidEmails) {
            user.setEmail(email);
            logger.info("Testing for value: " + email);
            assertFieldValidation(user, "email", true, null);
        }
    }
}
