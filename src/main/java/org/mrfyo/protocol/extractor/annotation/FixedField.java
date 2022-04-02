package org.mrfyo.protocol.extractor.annotation;

import org.mrfyo.protocol.extractor.enums.JavaDataType;
import org.mrfyo.protocol.extractor.enums.RawDataType;

import java.lang.annotation.*;

/**
 * @author Feyon
 * @date 2021/5/30
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface FixedField {

    int order() default 0;

    int size() default 0;

    RawDataType type() default RawDataType.BYTES;

    JavaDataType javaType() default JavaDataType.ANY;
}
