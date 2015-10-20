import java.math.BigInteger;
import java.util.Random;

class KeyGenerator {
	private static final int STD_BITS = 128;

	/**
	 * Generate key with 128 bits
	 * @return BigInteger with 128 bits
	 */
	public static BigInteger generateKey() {
		return generateBits(STD_BITS);
	}

	/**
	 * Generate key with specified number of bits
	 * @param bits the number of bits to generate
	 * @return BigInteger salt with the specified number of bits
	 */
	public static BigInteger generateKey(int bits) {
		return generateBits(bits);
	}

	private static BigInteger generateBits(int bits) {
		return new BigInteger(bits, new Random());
	}
}
