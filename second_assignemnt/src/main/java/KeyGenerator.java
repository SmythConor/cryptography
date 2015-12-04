import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Class to generate keys and handle returning of info
 * All work is my own
 * @author Conor Smyth <conor.smyth39@mail.dcu.ie>
 * @since 2015-11-16
 */
class KeyGenerator {

	/**
	 * Generate a public key from the given private key x
	 * @param x the private key to use to generate a public key
	 * @return BigInteger public key
	 */
	public static BigInteger generatePublicKey(BigInteger x) {
		BigInteger p = getModulus();
		BigInteger g = getGenerator();

		BigInteger publicKey = g.modPow(x, p);

		return publicKey;
	}

	/**
	 * Generate a private key within given range
	 * @param range
	 * @return BigInteger private key inside of a given range
	 */
	public static BigInteger generatePrivateKey(BigInteger range) {
		BigInteger x = generateRandomKey(range);

		return x;
	}

	/**
	 * Generate a random secret key.
	 * @return BigInteger with a random secret key
	 */
	public static BigInteger generateRandomKey(BigInteger range) {
		BigInteger x = BigInteger.ZERO;

		int bitLength = range.bitLength();

		do {
			x = new BigInteger(bitLength, new SecureRandom());
		} while(x.bitLength() != bitLength && inRange(x, range));

		return x;
	}

	/**
	 * Get the range
	 * @return BigInteger range
	 */
	public static BigInteger getModulus() {
		return new BigInteger(Info.getModulus(), 16);
	}

	/**
	 * Get the generator
	 * @return BigInteger generator
	 */
	public static BigInteger getGenerator() {
		return new BigInteger(Info.getGenerator(), 16);
	}

	/**
	 * Checks if x is in range of y and one
	 * @param x value to check
	 * @param y range to check
	 * @return BigInteger true if x in the range of y and one
	 */
	private static boolean inRange(BigInteger x, BigInteger y) {
		return x.compareTo(y) < 1 && x.compareTo(BigInteger.ONE) > 1;
	}
}
