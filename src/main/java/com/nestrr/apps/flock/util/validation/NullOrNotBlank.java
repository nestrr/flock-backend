package com.nestrr.apps.flock.util.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.FIELD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = NullOrNotBlankValidator.class)
public @interface NullOrNotBlank {
  String message() default
      "{com.nestrr.apps.flock.util.validation.NullOrNotBlank: Value can either be null or non-blank.}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
