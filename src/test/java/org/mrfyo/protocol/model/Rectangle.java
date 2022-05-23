package org.mrfyo.protocol.model;

import lombok.Data;
import org.mrfyo.protocol.extractor.annotation.OrderField;
import org.mrfyo.protocol.extractor.annotation.ListField;
import org.mrfyo.protocol.extractor.annotation.Message;

import java.util.List;

/**
 * @author Feng Yong
 */
@Data
@Message
public class Rectangle {

    @OrderField(size = 48)
    @ListField(itemType = Position.class)
    private List<Position> positions;
}
