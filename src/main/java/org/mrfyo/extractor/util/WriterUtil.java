package org.mrfyo.extractor.util;

import org.mrfyo.extractor.ExtractException;
import org.mrfyo.extractor.enums.DataType;
import org.mrfyo.extractor.io.Writer;

/**
 * @author Feng Yong
 */
public class WriterUtil {

    public static void writeInt(Writer w, int value, DataType dataType) {
        writeInt(w, value, dataType.getSize());
    }

    public static void writeInt(Writer w, int value, int size) {
        if (size == DataType.BYTE.getSize()) {
            w.writeUint8(value);
        } else if (size == DataType.WORD.getSize()) {
            w.writeUint16(value);
        } else if (size == DataType.DWORD.getSize()) {
            w.writeUint32(value);
        } else {
            throw new ExtractException("cannot marshal " + size + " to byte");
        }
    }


}
