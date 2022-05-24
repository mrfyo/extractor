package org.mrfyo.extractor.annotation;

import java.lang.annotation.*;

/**
 * @author Feng Yong
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface BitField {

    int index();
}
