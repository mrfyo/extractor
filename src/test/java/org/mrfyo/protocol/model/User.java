package org.mrfyo.protocol.model;

import lombok.Data;
import org.mrfyo.protocol.extractor.annotation.FixedField;
import org.mrfyo.protocol.extractor.annotation.Message;
import org.mrfyo.protocol.extractor.annotation.Support;
import org.mrfyo.protocol.extractor.enums.JavaDataType;
import org.mrfyo.protocol.extractor.enums.RawDataType;
import org.mrfyo.protocol.extractor.support.StringFieldSupport;

/**
 * @author Feng Yong
 */
@Data
@Message
public class User {

    @FixedField(type = RawDataType.WORD, javaType = JavaDataType.INT)
    private int id;

    @FixedField(size = 6)
    private String mobile;

    @FixedField
    @Support(StringFieldSupport.class)
    private String name;
}
