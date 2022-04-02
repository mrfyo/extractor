package org.mrfyo.protocol.extractor.enums;

import org.mrfyo.protocol.extractor.field.FieldExtractor;

/**
 * @author Feng Yong
 */
public enum JavaDataType {
    /**
     * any type and use {@link FieldExtractor} to handle
     */
    ANY,

    /**
     * integer/long type support BYTE, WORD, DWORD
     */
    INT,

    /**
     * string type support BYTES
     */
    STRING,

    /**
     * double type and use
     */
    DOUBLE,

    /**
     * data type
     *
     * @see java.time.LocalDateTime
     */
    DATE
}
