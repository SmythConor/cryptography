import java.math.BigInteger;
import java.util.Random;

/**
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
	public static BigInteger generateKey() {
		BigInteger i = null;

		do {
			i = generateBits(STD_BITS);
		} while(i.bitLength() != 128);

		return i;
	}

	/**
	 * Generate key with specified number of bits
	 * @param bits the number of bits to generate
	 * @return BigInteger salt with the specified number of bits
	 */
	public static BigInteger generateKey(int bits) {
		BigInteger i = null;

		do {
			i = generateBits(bits);
		} while(i.bitLength() != 128);

		return i;
	}

	private static BigInteger generateBits(int bits) {
		return new BigInteger(bits, new Random());
	}
}
