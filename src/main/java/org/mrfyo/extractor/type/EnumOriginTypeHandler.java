package org.mrfyo.extractor.type;

import org.mrfyo.extractor.ExtractException;
import org.mrfyo.extractor.bean.FieldDescriptor;
import org.mrfyo.extractor.io.Reader;
import org.mrfyo.extractor.io.Writer;


/**
 * @author Feng Yong
 */
public class EnumOriginTypeHandler<E extends Enum<E>> implements TypeHandler<E> {

    private final Class<E> type;
    private final E[] enums;

    public EnumOriginTypeHandler(Class<E> type) {
        this.type = type;
        this.enums = type.getEnumConstants();
        if (this.enums == null) {
            throw new IllegalArgumentException(type.getTypeName() + " is not enum type");
        }
    }


    @Override
    public void marshal(Writer writer, FieldDescriptor descriptor, E value) throws ExtractException {
        boolean exists = false;
        for (int i = 0; i < enums.length; i++) {
            if (enums[i].equals(value)) {
                writer.writeUint8(i);
                exists = true;
                break;
            }
        }
        if (!exists) {
            writer.writeUint8(0);
        }
    }

    @Override
    public E unmarshal(Reader reader, FieldDescriptor descriptor) throws ExtractException {
        int i = reader.readUint8();
        if (i < enums.length) {
            return enums[i];
        }
        throw new ExtractException("cannot convert " + i + " to " + type.getSimpleName() + " by origin value");
    }
}
