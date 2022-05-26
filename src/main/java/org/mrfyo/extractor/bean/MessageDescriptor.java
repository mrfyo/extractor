package org.mrfyo.extractor.bean;



import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;
import static java.util.Objects.requireNonNull;

/**
 * @author Feyon
 * @date 2021/8/2
 */
public class MessageDescriptor<T> {
    /**
     * message id
     */
    private final int id;

    /**
     * message type
     */
    private final String type;

    /**
     * message description
     */
    private final String desc;

    /**
     * java type
     */
    private final Class<T> javaType;

    /**
     * filed list
     */
    private final List<FieldDescriptor> fieldDescriptors;


    public MessageDescriptor(int id, String type, String desc, Class<T> javaType) {
        this(id, type, desc, javaType, Collections.emptyList());
    }


    public MessageDescriptor(int id, String type, String desc, Class<T> javaType, List<FieldDescriptor> fieldDescriptors) {
        this.id = id;
        this.desc = requireNonNull(desc);
        this.javaType = requireNonNull(javaType);
        this.type = requireNonNull(type);
        this.fieldDescriptors = requireNonNull(fieldDescriptors);
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public Class<T> getJavaType() {
        return javaType;
    }



    public List<FieldDescriptor> getFieldDescriptors() {
        return Collections.unmodifiableList(fieldDescriptors);
    }



    public <A extends Annotation> A getAnnotation(Class<A> annotationType) {
        return javaType.getAnnotation(annotationType);
    }

    public boolean hasAnnotation(Class<? extends Annotation> annotationType) {
        return getAnnotation(annotationType) != null;
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(",", "(", ")");
        joiner.add("id=" + id);
        joiner.add("type=" + type);
        joiner.add("desc=" + desc);
        return "MessageDescriptor" + joiner;
    }
}
