import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

public class ElectionTest {
    @Test
    void mainTest() {
        Election e = new Election();
        assertNotEquals(e, null); 
    }
}
