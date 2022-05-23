package org.mrfyo.protocol.model;

import lombok.Data;
import org.mrfyo.protocol.extractor.annotation.OrderField;
import org.mrfyo.protocol.extractor.annotation.Message;
import org.mrfyo.protocol.extractor.enums.InternalMessageType;
import org.mrfyo.protocol.extractor.enums.DataType;

/**
 * @author Feng Yong
 */
@Data
@Message(id = 0x01, desc = "位置消息", type = InternalMessageType.ORDER)
public class Position {

    @OrderField(type = DataType.DWORD)
    private int x;

    @OrderField(type = DataType.DWORD)
    private int y;

    @OrderField(type = DataType.DWORD)
    private int z;

    public Position() {
    }

    public Position(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
