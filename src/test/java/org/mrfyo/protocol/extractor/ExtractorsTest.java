package org.mrfyo.protocol.extractor;

import cn.hutool.core.util.HexUtil;
import org.junit.jupiter.api.Test;
import org.mrfyo.protocol.extractor.io.ByteBufHelper;
import org.mrfyo.protocol.model.Alarm;
import org.mrfyo.protocol.model.Position;
import org.mrfyo.protocol.model.Rectangle;
import org.mrfyo.protocol.model.User;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ExtractorsTest {

    @Test
    void position() {
        Position message = new Position();
        message.setX(1);
        message.setY(2);
        message.setZ(4);

        check(message);
    }



    @Test
    void rectangle() {
        Rectangle rectangle = new Rectangle();
        rectangle.setPositions(new ArrayList<>());
        rectangle.getPositions().add(new Position(1, 2, 3));
        rectangle.getPositions().add(new Position(1, 2, 3));
        rectangle.getPositions().add(new Position(1, 2, 3));
        rectangle.getPositions().add(new Position(1, 2, 3));

        check(rectangle);

    }

    @Test
    void alarm() {
        Alarm alarm = new Alarm();
        alarm.setIdle(true);
        alarm.setSpeed(180);
        alarm.setOverSpeed(true);

        check(alarm);
    }

    @Test
    void header() {
        User user = new User();
        user.setId(1);
        user.setMobile("015722223333");
        user.setName("Jack Rose");

        check(user);
    }


    private void check(Object message) {
        ByteBufHelper buffer = ByteBufHelper.buffer(32);
        Extractors.marshal(buffer, message);

        System.out.println(HexUtil.encodeHexStr(buffer.byteArray()));

        Object obj = Extractors.unmarshal(buffer, message.getClass());

        assertEquals(message, obj);
    }
}