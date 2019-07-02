package tbc.util;

import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class UUIDUtilTest {

    @Test
    public void getUUID() {
        UUID uuid1 = UUIDUtil.getUUID();
        UUID uuid2 = UUIDUtil.getUUID();

        assertNotEquals(uuid1.toString(), uuid2.toString());
    }
}