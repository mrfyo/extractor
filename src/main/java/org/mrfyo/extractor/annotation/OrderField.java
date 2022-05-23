package org.mrfyo.extractor.annotation;

import org.mrfyo.extractor.enums.DataType;

import java.lang.annotation.*;

/**
 * @author Feyon
 * @date 2021/5/30
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface OrderField {

    int order() default 0;

    int size() default 0;

    DataType type() default DataType.BYTES;
}
