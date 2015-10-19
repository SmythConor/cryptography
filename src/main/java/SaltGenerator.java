import java.math.BigInteger;
import java.util.Random;

class SaltGenerator {
	/**
	* Generate salt for specified number of bits
	* @param bits the number of bits to generate
	* @return BigInteger salt with the specified number of bits
	*/
	public static BigInteger generateSalt(int bits) {
		return new BigInteger(bits, new Random());
	}
}
