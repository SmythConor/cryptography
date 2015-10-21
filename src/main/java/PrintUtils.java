import java.math.BigInteger;
import javax.xml.bind.DatatypeConverter;

class PrintUtils {

	/**
	 * Return the byte array supplied as a hex string
	 * @return String representation of byte array
	 */
	public static String bytesAsString(byte[] bytes) {
		return DatatypeConverter.printHexBinary(bytes).toLowerCase();
	}
}
