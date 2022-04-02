package org.mrfyo.protocol.model;

import lombok.Data;
import org.mrfyo.protocol.extractor.annotation.FixedField;
import org.mrfyo.protocol.extractor.annotation.ListField;
import org.mrfyo.protocol.extractor.annotation.Message;
import org.mrfyo.protocol.extractor.annotation.Support;
import org.mrfyo.protocol.extractor.support.ListFieldSupport;

import java.util.List;

/**
 * @author Feng Yong
 */
@Data
@Message
public class Rectangle {

    @FixedField(size = 48)
    @ListField(itemSize = 12, itemType = Position.class)
    @Support(ListFieldSupport.class)
    private List<Position> positions;
}
