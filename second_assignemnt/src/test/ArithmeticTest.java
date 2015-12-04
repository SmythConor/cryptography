import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

public class ArithmeticTest {
	private BigInteger mod;

	@Before
	public void ArithmeticTest() {
		mod = new BigInteger(Info.getModulus(), 16);
	}
	
	@Test
	public void testGcd() {
		BigInteger x = KeyGenerator.generateRandomKey(mod);
		BigInteger y = KeyGenerator.generateRandomKey(mod);

		BigInteger actualGcd = x.gcd(y);
		BigInteger gcd = (Arithmetic.xgcd(x, y))[0];

		assertEquals(actualGcd, gcd);
	}

	@Test
	public void testModInverse() {
		BigInteger x = Arithmetic.getCoPrimeValue(mod, mod.subtract(BigInteger.ONE));

		BigInteger actualModInverse = x.modInverse(mod);
		BigInteger modInverse = Arithmetic.modInverse(x, mod);

		assertEquals(actualModInverse, modInverse);
	}
}
