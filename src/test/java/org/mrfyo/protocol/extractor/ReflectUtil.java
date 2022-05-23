package org.mrfyo.protocol.extractor;

import org.junit.jupiter.api.Test;
import org.mrfyo.protocol.model.Rectangle;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author Feng Yong
 */
public class ReflectUtil {

    @Test
    void test() {
        Field[] fields = Rectangle.class.getDeclaredFields();
        Field field = fields[0];
        Type genericType = field.getGenericType();
        if(genericType instanceof ParameterizedType pt) {
            Type[] arguments = pt.getActualTypeArguments();
            for (Type argument : arguments) {
                System.out.println(argument);
            }
        }
    }
}
