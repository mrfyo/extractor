package org.mrfyo.protocol.extractor.annotation;

import java.lang.annotation.*;

/**
 * 列表字段支持注解
 *
 * @author Feng Yong
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@OrderField
public @interface ListField {

    Class<?> itemType();
}
