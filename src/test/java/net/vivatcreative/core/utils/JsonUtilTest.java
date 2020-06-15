package net.vivatcreative.core.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JsonUtilTest {

    @Test
    public void shouldConvertBetweenStringAndJsonObject(){
        TestObject oldInst = new TestObject();
        oldInst.number = 1;
        oldInst.string = "test";
        String toJSON = JsonUtil.toJSON(oldInst);
        String tJsonString = "{\"number\":1,\"string\":\"test\"}";
        String stringVal = JsonUtil.getString(tJsonString, "string");
        assertEquals(stringVal, "test");
        assertEquals(toJSON, tJsonString);
        TestObject newInst = JsonUtil.fromJSON(tJsonString, TestObject.class);
        assertEquals(newInst, oldInst);
    }
}
