package org.mrfyo.protocol.extractor.bean;

import cn.hutool.core.convert.Convert;

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
     * filed name
     */
    private final String name;

    /**
     * filed type
     */
    private final Class<?> fieldType;

    private final Field field;

    /**
     * write method
     */
    private final Method writeMethod;

    /**
     * read method
     */
    private final Method readMethod;

    /**
     * annotation arrays
     */
    private final List<Annotation> annotations;


    public AbstractFieldDescriptor(Field field, PropertyDescriptor pd) {
        this.name = field.getName();
        this.field = field;
        this.writeMethod = pd.getWriteMethod();
        this.readMethod = pd.getReadMethod();
        this.annotations = initAnnotations(field);
        this.fieldType = initFieldType(field.getType());
    }


    private List<Annotation> initAnnotations(Field field) {
        return new ArrayList<>(Arrays.asList(field.getAnnotations()));
    }

    /**
     * 将基本类型统一转换为其对应的包装类型
     */
    protected Class<?> initFieldType(Class<?> fieldType) {
        return Convert.wrap(fieldType);
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
        return fieldType;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public <T extends Annotation> T getAnnotation(Class<T> annotationType) {
        for (Annotation annotation : annotations) {
            if (annotationType.isInstance(annotation)) {
                return annotationType.cast(annotation);
            }
        }
        return null;
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
