package net.vivatcreative.core.players;

import net.vivatcreative.core.utils.JsonUtil;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlotcountJSONTest {

    @Test
    public void shouldFormatToJSON(){
        PlotcountObject obj = new PlotcountObject();
        obj.bronze = 2;
        obj.silver = 0;
        obj.gold = 0;
        obj.diamond = 0;
        obj.emerald = 0;
        obj.master = 0;
        obj.freebuild = 0;
        String result = null;
        assertEquals(JsonUtil.toJSON(obj), "{\"bronze\":2,\"silver\":0,\"gold\":0,\"diamond\":0,\"emerald\":0,\"master\":0,\"freebuild\":0}");
    }
}
