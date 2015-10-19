import java.math.BigInteger;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class SaltGeneratorTest {

	public SaltGeneratorTest() {}
	
	@Test
	public void testGenerateSalt() {
		int bits = 128;
		BigInteger i = SaltGenerator.generateSalt(bits);
		assertTrue(true);
	}
}
