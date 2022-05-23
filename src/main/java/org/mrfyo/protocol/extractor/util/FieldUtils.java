package org.mrfyo.protocol.extractor.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author Feyon
 * @date 2021/8/14
 */
public class FieldUtils {


    public static List<Field> getDeclaredFields(Class<?> clazz, boolean includeSupper, Predicate<Field> matcher) {
        List<Field> fields = new ArrayList<>();
        if(includeSupper) {
            Class<?> superclass = clazz.getSuperclass();
            if(superclass != null) {
                Field[] fs = superclass.getDeclaredFields();
                for (Field f : fs) {
                    if(matcher.test(f)){
                        fields.add(f);
                    }
                }
            }
        }
        Field[] fs = clazz.getDeclaredFields();
        for (Field f : fs) {
            if(matcher.test(f)){
                fields.add(f);
            }
        }
        return fields;
    }
}
