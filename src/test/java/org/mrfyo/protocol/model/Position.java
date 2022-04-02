package org.mrfyo.protocol.model;

import lombok.Data;
import org.mrfyo.protocol.extractor.annotation.FixedField;
import org.mrfyo.protocol.extractor.annotation.Message;
import org.mrfyo.protocol.extractor.enums.JavaDataType;
import org.mrfyo.protocol.extractor.enums.MessageType;
import org.mrfyo.protocol.extractor.enums.RawDataType;

/**
 * @author Feng Yong
 */
@Data
@Message(id = 0x01, desc = "位置消息", type = MessageType.FIX)
public class Position {

    @FixedField(type = RawDataType.DWORD, javaType = JavaDataType.INT)
    private int x;

    @FixedField(type = RawDataType.DWORD, javaType = JavaDataType.INT)
    private int y;

    @FixedField(type = RawDataType.DWORD, javaType = JavaDataType.INT)
    private int z;

    public Position() {
    }

    public Position(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
