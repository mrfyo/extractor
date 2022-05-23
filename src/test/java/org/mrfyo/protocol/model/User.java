package org.mrfyo.protocol.model;

import lombok.Data;
import org.mrfyo.protocol.extractor.annotation.OrderField;
import org.mrfyo.protocol.extractor.annotation.Message;
import org.mrfyo.protocol.extractor.annotation.Support;
import org.mrfyo.protocol.extractor.enums.DataType;
import org.mrfyo.protocol.extractor.type.EnumOriginTypeHandler;

/**
 * @author Feng Yong
 */
@Data
@Message
public class User {

    @OrderField(type = DataType.WORD)
    private int id;

    @OrderField(size = 6)
    private String mobile;

    @OrderField(type = DataType.BYTE)
    @Support(EnumOriginTypeHandler.class)
    private Sex sex;


    public enum Sex {
        MAN, WOMAN
    }

}
