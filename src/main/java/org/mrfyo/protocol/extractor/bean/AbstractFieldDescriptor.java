package org.mrfyo.protocol.extractor.bean;

import cn.hutool.core.convert.Convert;
import org.mrfyo.protocol.extractor.annotation.ListField;
import org.mrfyo.protocol.extractor.annotation.ScaleConverter;
import org.mrfyo.protocol.extractor.annotation.Support;
import org.mrfyo.protocol.extractor.enums.JavaDataType;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
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

    /**
     * java data type
     */
    private final JavaDataType javaType;

    public AbstractFieldDescriptor(Field field, PropertyDescriptor pd) {
        this.name = field.getName();
        this.writeMethod = pd.getWriteMethod();
        this.readMethod = pd.getReadMethod();
        this.annotations = initAnnotations(field);
        this.fieldType = initFieldType(field.getType());
        this.javaType = initJavaDataType();
    }

    /**
     * 确定Java数据类型，具有一定的约束性
     */
    private JavaDataType initJavaDataType() {
        if (hasAnnotation(Support.class) || hasAnnotation(ListField.class)) {
            return JavaDataType.ANY;
        }
        if (hasAnnotation(ScaleConverter.class)) {
            return JavaDataType.DOUBLE;
        }

        if (fieldType == Integer.class || fieldType == Long.class) {
            return JavaDataType.INT;
        } else if (fieldType == String.class) {
            return JavaDataType.STRING;
        } else if (fieldType == LocalDateTime.class) {
            return JavaDataType.DATE;
        } else {
            return JavaDataType.ANY;
        }

    }


    private List<Annotation> initAnnotations(Field field) {
        List<Annotation> list = new ArrayList<>(Arrays.asList(field.getAnnotations()));
        if (field.getAnnotation(ListField.class) != null) {
//            list.add(new Support() {
//
//                @Override
//                public Class<? extends Annotation> annotationType() {
//                    return Support.class;
//                }
//
//                @Override
//                public Class<? extends FieldExtractor<?>> value() {
//                    return ListFieldSupport.class;
//                }
//            });
        }
        return list;
    }

    /**
     * 将基本类型统一转换为其对应的包装类型
     */
    protected Class<?> initFieldType(Class<?> fieldType) {
        return Convert.wrap(fieldType);
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
    public JavaDataType getJavaType() {
        return javaType;
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
