package net.vivatcreative.core.ranks;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BuildRankTest {

    @Test
    public void shouldGetBuildRankFromString() {
        BuildRank tRank = BuildRank.DEFAULT;
        BuildRank tResult = BuildRank.fromString("DEFAULT");
        assertEquals(tRank, tResult);

        tRank = BuildRank.DIAMOND;
        tResult = BuildRank.fromString("DIAMOND");
        assertEquals(tRank, tResult);

    }

}
