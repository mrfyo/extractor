package org.mrfyo.protocol.extractor.util;

import java.lang.reflect.Field;

/**
 * @author Feyon
 * @date 2021/8/14
 */
@FunctionalInterface
public interface FieldMatcher {

    /**
     * return ture if the field need
     * @param field {@link Field}
     * @return ture if the field need
     */
    boolean matches(Field field);

}
