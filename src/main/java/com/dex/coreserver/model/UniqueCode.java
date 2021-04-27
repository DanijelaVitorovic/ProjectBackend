package com.dex.coreserver.model;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = UniqueCodeValidator.class)
@Retention(RUNTIME)
@Target({ FIELD, METHOD })
public @interface UniqueCode {

    public String message() default "Organizaciona jedinica sa ovim kodom vec postoji";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default{};

}