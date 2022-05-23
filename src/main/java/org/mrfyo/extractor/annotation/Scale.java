package org.mrfyo.extractor.annotation;

import java.lang.annotation.*;

/**
 * @author Feyon
 * @date 2021/5/31
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Scale {

    /**
     * 倍数
     */
    double mul() default 1.0;

    /**
     * 偏移量
     */
    double offset() default 0.0;

    int scale() default 0;

}
