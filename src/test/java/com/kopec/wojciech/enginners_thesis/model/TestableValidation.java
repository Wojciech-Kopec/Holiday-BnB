package com.kopec.wojciech.enginners_thesis.model;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;

public interface TestableValidation<T> {

    default Validator createValidator() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        return localValidatorFactoryBean;
    }

      default void assertFieldValidation(T t, String value, boolean shouldBeValid, String message) {
        Validator validator = createValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(t);
        if (!shouldBeValid) {
            assertThat(constraintViolations.size(), is(greaterThanOrEqualTo(1)));

            ConstraintViolation<T> violation = constraintViolations.stream().filter(
                    v -> v.getPropertyPath().toString().equals(value))
                    .findFirst()
                    .orElseThrow(() -> new AssertionError("Violation for field " + value + " not found"));

            if (message != null)
                assertThat(violation.getMessage(), is(message));
        } else if (shouldBeValid) {
            assertThat(constraintViolations.stream().filter(
                    v -> v.getPropertyPath().toString().equals(value)).count(), is((long) 0));
        }
    }


    default void assertObjectsConstraintsViolationCount(T t, int expectedViolationsCount) {
        Validator validator = createValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(t);
        assertThat(constraintViolations.size(), is(expectedViolationsCount));
    }

    default String stringWithSize(int size) {
        return StringUtils.repeat("a",size);
    }
}
