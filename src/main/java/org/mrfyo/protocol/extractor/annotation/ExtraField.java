package org.mrfyo.protocol.extractor.annotation;

import org.mrfyo.protocol.extractor.enums.DataType;

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

    DataType rawType();

}
