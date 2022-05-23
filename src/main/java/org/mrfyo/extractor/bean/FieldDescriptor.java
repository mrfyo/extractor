package org.mrfyo.extractor.bean;


import org.mrfyo.extractor.enums.DataType;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Field Descriptor
 *
 * @author Feyon
 * @date 2021/6/1
 */
public interface FieldDescriptor {
    /**
     * return field name
     *
     * @return field name
     */
    String getName();

    /**
     * return field
     *
     * @return field
     */
    Field getField();

    /**
     * return field write method
     *
     * @return field write method
     */
    Method getWriteMethod();

    /**
     * return field read method
     *
     * @return field write method
     */
    Method getReadMethod();

    /**
     * return field type
     *
     * @return field type
     */
    Class<?> getFieldType();

    /**
     * return field annotation
     *
     * @param annotationType annotation class
     * @param <T>            annotation type
     * @return field annotation
     */
    <T extends Annotation> T getAnnotation(Class<T> annotationType);

    /**
     * return is or not contains the annotation
     *
     * @param annotationType annotation class
     * @return is or not contains the annotation
     */
    boolean hasAnnotation(Class<? extends Annotation> annotationType);

    /**
     * return byte size of this field possessed in binary message.
     *
     * @return byte size
     */
    int getSize();

    /**
     * return raw type of this field in binary message
     *
     * @return {@link  DataType}
     */
    DataType getDataType();
}
