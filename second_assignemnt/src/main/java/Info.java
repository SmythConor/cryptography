import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

/**
 * Information class for holding the hex values needed for assignment
 * All work is my own
 * @author Conor Smyth <conor.smyth39@mail.dcu.ie>
 * @since 2015-11-16
 */
class Info {
	private static final String MODULUS = "b59dd79568817b4b9f6789822d22594f376e6a9abc0241846de426e5dd8f6eddef00b465f38f509b2b18351064704fe75f012fa346c5e2c442d7c99eac79b2bc8a202c98327b96816cb8042698ed3734643c4c05164e739cb72fba24f6156b6f47a7300ef778c378ea301e1141a6b25d48f1924268c62ee8dd3134745cdf7323";

	private static final String GENERATOR = "44ec9d52c8f9189e49cd7c70253c2eb3154dd4f08467a64a0267c9defe4119f2e373388cfa350a4e66e432d638ccdc58eb703e31d4c84e50398f9f91677e88641a2d2f6157e2f4ec538088dcf5940b053c622e53bab0b4e84b1465f5738f549664bd7430961d3e5a2e7bceb62418db747386a58ff267a9939833beefb7a6fd68";

	/**
	 * Return the modulus value for this assignment
	 * @return Modulus in hex as a string
	 */
	public static String getModulus() {
		return MODULUS;
	}

	/**
	 * Return the bit length of the modulus
	 * @return number of bits in the modulus as an int
	 */
	public static int getModulusLength() {
		return new BigInteger(MODULUS, 16).bitLength();
	}

	/**
	 * Return the generator value for this assignment
	 * @return Generator in hex as a string
	 */
	public static String getGenerator() {
		return GENERATOR;
	}

	/**
	 * Return the bit length of the generator
	 * @return number of bits in the generator as an int
	 */
	public static int getGeneratorLength() {
		return new BigInteger(GENERATOR, 16).bitLength();
	}
}
