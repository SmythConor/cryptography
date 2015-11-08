import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;

/**
 * @author Conor Smyth <conor.smyth39@mail.dcu.ie>
 * @since 2015-10-19
 * All work is my own
 */
class PrintUtils {

	/**
	 * Return the byte array supplied as a hex string
	 * @return String representation of byte array
	 */
	public static String bytesAsString(byte[] bytes) {
		return DatatypeConverter.printHexBinary(bytes).toLowerCase();
	}

	public static void printHexBytes(byte[] bytes) {
		System.out.println(bytesAsString(bytes));
	}

	public static void printHexAsString(byte[] bytes) {
		try {
			System.out.println(new String(bytes, "UTF-8"));
		} catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
