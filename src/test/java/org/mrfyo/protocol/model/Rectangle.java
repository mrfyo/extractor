package org.mrfyo.protocol.model;

import lombok.Data;
import org.mrfyo.protocol.extractor.annotation.OrderField;
import org.mrfyo.protocol.extractor.annotation.Message;

import java.util.List;
import java.util.Map;

/**
 * @author Feng Yong
 */
@Data
@Message
public class Rectangle {

    @OrderField
    private List<Position> positions;
}
