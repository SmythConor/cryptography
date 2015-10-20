import java.math.BigInteger;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class KeyGeneratorTest {

	public KeyGeneratorTest() {}
	
	@Test
	public void testGenerateKeyNoArgs() {
		int bits = 128;

		BigInteger i = KeyGenerator.generateKey();

		int bitCount = i.bitLength();
		System.out.println("Bit count " + bitCount);
		System.out.println("Bit Count " + i.bitCount());

		assertEquals(bits, bitCount);
	}

	@Test
	public void testGenerateKeyAllArgs() {
		int bits = 128;

		BigInteger i = KeyGenerator.generateKey(bits);
		assertTrue(true);
	}
}
