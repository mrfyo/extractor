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
     * filed name
     */
    private final String name;

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
    }


    private List<Annotation> initAnnotations(Field field) {
        return new ArrayList<>(Arrays.asList(field.getAnnotations()));
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
