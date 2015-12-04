import java.math.BigInteger;

/**
 * Class to handle all the arithmetic for the assignemnt
 * All work is my own
 * @author Conor Smyth <conor.smyth39@mail.dcu.ie>
 * @since 2015-11-20
 */
class Arithmetic {

	/**
	 * Get the digital signiture R from the values provided
	 * g^k (mod p)
	 * @param g Generator for this signiture
	 * @param k some random value between 0 and p - 1
	 * @param p the modulus for this signiture
	 */
	public static BigInteger getDigitalSignitureR(BigInteger g, BigInteger k, BigInteger p) {
		return g.modPow(k, p);
	}

	/**
	 * Get the digital signiture from the values provided
	 * (H(m) - xr)k^-1 (mod p - 1)
	 * @param hashedData Data to use for the digital signiture
	 * @param x privateKey Secret key, some value between 0 and p - 1
	 * @param r r = g^k (mod p) where g is a generator, k is some random value between 0 and p - 1 and p is the modulus
	 * @param k some random value between 0 and p - 1
	 * @param p1 p - 1
	 */
	public static BigInteger getDigitalSignitureS(byte[] hashedData, BigInteger x, BigInteger r, BigInteger k, BigInteger p1) {
		BigInteger m = new BigInteger(hashedData);

		BigInteger temp = m.subtract(x.multiply(r));
		BigInteger kPrime = modInverse(k, p1);

		BigInteger result = temp.multiply(kPrime).mod(p1);

		return result;
	}

	/**
	 * Get a value in specified range that is coprime with another value
	 * @param range the range to generate number
	 * @param x the value to be coprime with
	 * @return BigInteger coprime with x in the range range
	 */
	public static BigInteger getCoPrimeValue(BigInteger range, BigInteger x) {
		BigInteger value = null;

		do {
			value = KeyGenerator.generateRandomKey(range);
		} while(!isCoPrime(value, x));

		return value;
	}

	/**
	 * Calculate the GCD of two BigIntegers a and b, uses the Extended Euclidean GCD algorithm
	 * @param a the first BigInteger
	 * @param b the second BigInteger
	 * @return the GCD of the two number
	 */
	public static BigInteger[] xgcd(BigInteger a, BigInteger b) {
		BigInteger[] gcdValues = null;

		if(b.equals(BigInteger.ZERO)) {
			gcdValues = new BigInteger[]{a, BigInteger.ONE, BigInteger.ZERO};
		} else {
			gcdValues = xgcd(b, a.mod(b));

			BigInteger temp = gcdValues[2];

			gcdValues[2] = gcdValues[1].subtract(a.divide(b).multiply(gcdValues[2]));
			gcdValues[1] = temp;
		}

		return gcdValues;
	}

	/**
	 * Get the mod inverse of a mod b
	 * @param a the BigInteger to get modInverse of
	 * @param b the BigInteger modulus
	 * @return BigInteger moduluar inverse of a
	 * @throws ArithmeticException if the gcd != 1
	 */
	public static BigInteger modInverse(BigInteger a, BigInteger b) {
		BigInteger[] xGcdValues = xgcd(a, b);

		if(!xGcdValues[0].equals(BigInteger.ONE)) {
			throw new ArithmeticException();
		} else {
			return xGcdValues[1].mod(b);
		}
	}

	/**
	 * Function to check if two BigIntegers are coprime
	 * @param a the first BigInteger
	 * @param b the second BigInteger
	 * @return true if a and b are coprime i.e gcd(a, b) = 1
	 */
	private static boolean isCoPrime(BigInteger a, BigInteger b) {
		return (xgcd(a, b))[0].equals(BigInteger.ONE);
	}
}
