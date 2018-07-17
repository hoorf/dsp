package com.github.ruifengho.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.transaction.annotation.Transactional;

@Documented
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Transactional
public @interface DspTxTransaction {

	boolean isStart() default false;

	Class<? extends Throwable>[] rollbackFor() default {};

	Class<? extends Throwable>[] noRollbackFor() default {};

}
