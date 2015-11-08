import java.security.SecureRandom;

/**
 * Class to generate random keys
 * @author Conor Smyth <conor.smyth39@mail.dcu.ie>
 * @since 2015-10-19
 * All work is my own
 */
class KeyGenerator {
	private static final int STD_BITS = 128;

	/**
	 * Generate key with 128 bits
	 * @return BigInteger with 128 bits
	 */
	public static byte[] generateKey() {
		return generateBits(STD_BITS);
	}

	/**
	 * Generate key with specified number of bits
	 * @param bits the number of bits to generate
	 * @return BigInteger salt with the specified number of bits
	 */
	public static byte[] generateKey(int bits) {
		return generateBits(bits);
	}

	private static byte[] generateBits(int bits) {
		SecureRandom secureRandom = new SecureRandom();
		byte[] bytes = new byte[bits / 8];

		secureRandom.nextBytes(bytes);

		return bytes;
	}
}
