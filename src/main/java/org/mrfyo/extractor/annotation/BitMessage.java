package org.mrfyo.extractor.annotation;


import org.mrfyo.extractor.enums.DataType;

import java.lang.annotation.*;

/**
 * @author Feng Yong
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface BitMessage {

    DataType dataType();

}
