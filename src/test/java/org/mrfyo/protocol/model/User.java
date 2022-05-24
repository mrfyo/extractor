package org.mrfyo.protocol.model;

import lombok.Data;
import org.mrfyo.extractor.annotation.OrderField;
import org.mrfyo.extractor.annotation.Message;
import org.mrfyo.extractor.annotation.Support;
import org.mrfyo.extractor.enums.DataType;
import org.mrfyo.extractor.type.EnumOriginTypeHandler;

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
    private Sex sex;


    public enum Sex {
        MAN, WOMAN
    }

}
