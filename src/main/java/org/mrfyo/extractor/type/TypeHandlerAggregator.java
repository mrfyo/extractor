package org.mrfyo.extractor.type;

import cn.hutool.core.util.ReflectUtil;
import org.mrfyo.extractor.ExtractException;
import org.mrfyo.extractor.annotation.Embedded;
import org.mrfyo.extractor.bean.FieldDescriptor;
import org.mrfyo.extractor.io.Writer;
import org.mrfyo.extractor.annotation.Support;
import org.mrfyo.extractor.io.Reader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * 字段解析器简单实现类
 *
 * @author Feyon
 * @date 2021/8/4
 */
public class TypeHandlerAggregator implements TypeHandler<Object> {

    private final Logger log = LoggerFactory.getLogger(TypeHandlerAggregator.class);

    private final TypeHandlerRegistry registry;

    public TypeHandlerAggregator(TypeHandlerRegistry registry) {
        this.registry = registry;
    }

    @Override
    public Object unmarshal(Reader reader, FieldDescriptor descriptor) throws ExtractException {
        return getFieldExtractor(descriptor).unmarshal(reader, descriptor);
    }

    @Override
    public void marshal(Writer writer, FieldDescriptor descriptor, Object value) throws ExtractException {
        if (value == null) {
            return;
        }
        if (writer == null || descriptor == null) {
            log.warn("writer | descriptor cannot be null");
            return;
        }
        ReflectUtil.invoke(getFieldExtractor(descriptor), "marshal", writer, descriptor, value);
    }

    private TypeHandler<?> getFieldExtractor(FieldDescriptor descriptor) throws ExtractException {
        TypeHandler<?> typeHandler = findTypeHandler(descriptor);
        if (typeHandler == null) {
            throw new ExtractException("cannot handle " + descriptor);
        }
        return typeHandler;
    }

    private TypeHandler<?> findTypeHandler(FieldDescriptor descriptor) {
        try {
            Class<?> fieldType = descriptor.getFieldType();
            Support support = descriptor.getAnnotation(Support.class);
            if (support != null) {
                Class<? extends TypeHandler<?>> customizedExtractor = support.value();
                return createTypeHandler(customizedExtractor, descriptor);
            }
            Embedded embedded = descriptor.getAnnotation(Embedded.class);
            if (embedded != null) {
                return registry.getExtractor(Object.class);
            }

            TypeHandler<?> extractor = registry.getExtractor(fieldType);
            if (extractor != null) {
                return extractor;
            }

            if (fieldType.isEnum()) {
                TypeHandler<?> typeHandler = createTypeHandler(EnumOriginTypeHandler.class, descriptor);
                registry.register(fieldType, typeHandler);
                return typeHandler;
            }
        } catch (Exception e) {
            throw new ExtractException(e);
        }
        return null;
    }

    private <T extends TypeHandler<?>> T createTypeHandler(Class<T> type, FieldDescriptor descriptor) {
        Constructor<?> constructor = type.getDeclaredConstructors()[0];
        constructor.setAccessible(true);
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        Object[] params = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            Class<?> pt = parameterTypes[i];
            if (pt.isAssignableFrom(FieldDescriptor.class)) {
                params[i] = descriptor;
            } else if (pt.isAssignableFrom(Class.class)) {
                params[i] = descriptor.getFieldType();
            } else if (pt.isAssignableFrom(Field.class)) {
                params[i] = descriptor.getField();
            } else {
                throw new IllegalArgumentException("cannot construct instance " + type.getTypeName());
            }
        }
        return ReflectUtil.newInstance(type, params);
    }
}
