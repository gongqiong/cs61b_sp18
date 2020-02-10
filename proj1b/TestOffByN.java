import org.junit.Test;

import static org.junit.Assert.*;

public class TestOffByN {
    OffByN obn5 = new OffByN(5);

    @Test
    public void testOffByN() {
        assertTrue(obn5.equalChars('a', 'f'));
        assertTrue(obn5.equalChars('f', 'a'));
        assertFalse(obn5.equalChars('h', 'f'));
        assertFalse(obn5.equalChars('a', 'b'));

    }
}
