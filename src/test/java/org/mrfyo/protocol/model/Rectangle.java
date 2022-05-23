package org.mrfyo.protocol.model;

import lombok.Data;
import org.mrfyo.extractor.annotation.OrderField;
import org.mrfyo.extractor.annotation.Message;

import java.util.List;

/**
 * @author Feng Yong
 */
@Data
@Message
public class Rectangle {

    @OrderField
    private List<Position> positions;
}
