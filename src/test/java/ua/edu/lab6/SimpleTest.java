import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SimpleTest {
    
    @Test
    public void testBasic() {
        assertEquals(4, 2 + 2);
        assertTrue(true);
        assertNotNull("Hello");
        System.out.println("Test passed!");
    }
    
    @Test
    public void testString() {
        String name = "GitHub Actions";
        assertNotNull(name);
        assertTrue(name.contains("Actions"));
        assertEquals(14, name.length());
        System.out.println("String test passed!");
    }
}
