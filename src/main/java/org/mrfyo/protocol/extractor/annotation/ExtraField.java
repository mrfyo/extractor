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
@Target(ElementType.FIELD)
public @interface ExtraField {

    int id() default 0;

    int size() default 0;

    RawDataType rawType();

    JavaDataType javaType() default JavaDataType.ANY;
}
