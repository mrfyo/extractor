package org.mrfyo.extractor.bean;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Feyon
 * @date 2021/5/31
 */
public abstract class AbstractFieldDescriptor implements FieldDescriptor {
    /**
     * field
     */
    private final Field field;

    /**
     * write method
     */
    private final Method writeMethod;

    /**
     * read method
     */
    private final Method readMethod;


    public AbstractFieldDescriptor(Field field, PropertyDescriptor pd) {
        this.field = field;
        this.writeMethod = pd.getWriteMethod();
        this.readMethod = pd.getReadMethod();
    }

    @Override
    public Field getField() {
        return field;
    }

    @Override
    public Method getWriteMethod() {
        return writeMethod;
    }

    @Override
    public Method getReadMethod() {
        return readMethod;
    }

    @Override
    public Class<?> getFieldType() {
        return field.getType();
    }

    @Override
    public String getName() {
        return field.getName();
    }

    @Override
    public <T extends Annotation> T getAnnotation(Class<T> annotationType) {
        return field.getAnnotation(annotationType);
    }

    public <T extends Annotation> T getAnnotation(Class<T> annotationType, String message) {
        T annotation = getAnnotation(annotationType);
        if (annotation == null) {
            throw new NullPointerException(message);
        }
        return annotation;
    }

    @Override
    public boolean hasAnnotation(Class<? extends Annotation> annotationType) {
        return getAnnotation(annotationType) != null;
    }
}
