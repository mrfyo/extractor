package org.mrfyo.protocol.model;

import lombok.Data;
import org.mrfyo.protocol.extractor.annotation.ExtraField;
import org.mrfyo.protocol.extractor.annotation.Message;
import org.mrfyo.protocol.extractor.annotation.Support;
import org.mrfyo.protocol.extractor.enums.DataType;
import org.mrfyo.protocol.extractor.MessageType;
import org.mrfyo.protocol.extractor.support.EmptyFieldSupport;

/**
 * @author Feng Yong
 */
@Data
@Message(type = MessageType.EXTRA)
public class Alarm {

    @ExtraField(id = 1, rawType = DataType.BYTES)
    @Support(EmptyFieldSupport.class)
    private boolean overSpeed;

    @ExtraField(id = 2, rawType = DataType.BYTES)
    @Support(EmptyFieldSupport.class)
    private boolean idle;

    @ExtraField(id = 3, rawType = DataType.WORD)
    private int speed;

}
