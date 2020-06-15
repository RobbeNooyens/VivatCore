package net.vivatcreative.core.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TextUtilTest {

    @Test
    public void shouldMakeFirstLetterUppercase() {
        String tResult = TextUtil.toFirstUpper("test");
        assertEquals(tResult, "Test");
    }

    @Test
    public void shouldColorString() {
        String tResult = TextUtil.toColor("&aTest");
        assertEquals(tResult, "§aTest");
    }

    @Test
    public void shouldRemoveColorFromStringWithParagraph() {
        String tResult = TextUtil.removeColor("§aTest");
        assertEquals(tResult, "Test");
    }

    @Test
    public void shouldRemoveColorFromStringWithAnd() {
        String tResult = TextUtil.removeColor("&aTest");
        assertEquals(tResult, "Test");
    }

    @Test
    public void shouldReplacePlaceholders() {
        Object[] tPlaceholders = {"%p1%", "placeholder1", "%p2%", "placeholder2"};
        String tInput = "%p1% and %p2%";
        String tResult = TextUtil.replacePlaceholders(tInput, tPlaceholders);
        assertEquals(tResult, "placeholder1 and placeholder2");
    }

}
