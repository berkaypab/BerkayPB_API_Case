package com.petstore.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.petstore.api.enums.CategoryType;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface FrameworkAnnotation {
	CategoryType[] category();
}
