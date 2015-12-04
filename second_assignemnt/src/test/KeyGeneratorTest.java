import org.junit.Test;
import org.junit.Ignore;

import static org.junit.Assert.assertNotNull;

import java.math.BigInteger;

@Ignore
public class KeyGeneratorTest {

	@Test
	public void testGetModulus() {
		BigInteger modulus = KeyGenerator.getModulus();

		assertNotNull(modulus);
	}
}
