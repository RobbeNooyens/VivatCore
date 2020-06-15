package net.vivatcreative.core.utils;

import net.vivatcreative.core.exceptions.WorldNotFoundException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VivatWorldTest {

    @Test
    public void shouldGetVivatWorldFromString() throws WorldNotFoundException {
        String worldOfficialName = "world_Bronze";
        String worldAlias = "Bronze";
        VivatWorld wOfficial = VivatWorld.fromString(worldOfficialName);
        VivatWorld wAlias = VivatWorld.fromString(worldAlias);
        assertEquals(wOfficial, VivatWorld.BRONZE);
        assertEquals(wAlias, VivatWorld.BRONZE);
    }
}
