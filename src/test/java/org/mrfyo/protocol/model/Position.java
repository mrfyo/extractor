package org.mrfyo.protocol.model;

import lombok.Data;
import org.mrfyo.extractor.annotation.OrderField;
import org.mrfyo.extractor.annotation.Message;
import org.mrfyo.extractor.enums.DataType;

/**
 * @author Feng Yong
 */
@Data
@Message(id = 0x01, desc = "位置消息")
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
