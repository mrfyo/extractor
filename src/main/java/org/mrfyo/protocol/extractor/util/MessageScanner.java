package org.mrfyo.protocol.extractor.util;

import cn.hutool.core.util.ClassUtil;
import org.mrfyo.protocol.extractor.annotation.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Feng Yong
 */
public class MessageScanner {
    public static <T> Map<Integer, Class<? extends T>> scanUploadMessage(Class<T> baseType, boolean reply) {
        String packageName = baseType.getPackageName();
        Set<Class<?>> classSet = ClassUtil.scanPackageBySuper(packageName, baseType);
        Map<Integer, Class<? extends T>> map = new HashMap<>(16);
        for (Class<?> clazz : classSet) {
            Message annotation = clazz.getAnnotation(Message.class);
            if (annotation == null) {
                continue;
            }
            if (reply && annotation.reply()) {
                continue;
            }
            map.put(annotation.id(), clazz.asSubclass(baseType));
        }
        return map;
    }
}
