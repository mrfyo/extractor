package org.mrfyo.protocol.model;

import lombok.Data;
import org.mrfyo.extractor.annotation.ExtraField;
import org.mrfyo.extractor.annotation.Message;
import org.mrfyo.extractor.annotation.Support;
import org.mrfyo.extractor.enums.DataType;
import org.mrfyo.extractor.MessageType;
import org.mrfyo.extractor.support.EmptyFieldSupport;

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
