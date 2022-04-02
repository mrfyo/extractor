package org.mrfyo.protocol.extractor.annotation;


import org.mrfyo.protocol.extractor.field.FieldExtractor;

import java.lang.annotation.*;

/**
 * @author Feng Yong
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Support {
    Class<? extends FieldExtractor<?>> value();
}
