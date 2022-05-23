package org.mrfyo.extractor.annotation;


import org.mrfyo.extractor.type.TypeHandler;

import java.lang.annotation.*;

/**
 * @author Feng Yong
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Support {
    Class<? extends TypeHandler> value();
}
