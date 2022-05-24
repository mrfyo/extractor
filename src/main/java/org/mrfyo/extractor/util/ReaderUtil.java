package org.mrfyo.extractor.util;

import org.mrfyo.extractor.ExtractException;
import org.mrfyo.extractor.enums.DataType;
import org.mrfyo.extractor.io.Reader;

/**
 * @author Feng Yong
 */
public class ReaderUtil {

    public static int readInt(Reader reader, DataType dataType) {
        return (int) readLong(reader, dataType.getSize());
    }

    public static long readLong(Reader reader, DataType dataType) {
        return readLong(reader, dataType.getSize());
    }

    public static long readLong(Reader reader, int size) {
        if (size == DataType.BYTE.getSize()) {
            return reader.readUint8();
        } else if (size == DataType.WORD.getSize()) {
            return reader.readUint16();
        } else if (size == DataType.DWORD.getSize()) {
            return reader.readUint32();
        } else {
            throw new ExtractException("cannot marshal " + size + " to byte");
        }
    }


}
