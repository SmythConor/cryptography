import java.math.BigInteger;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;

/**
 * Print utility class for printing hex/byte arrays
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

	/**
	 * Print out a byte array in Hex
	 */
	public static void printHexBytes(byte[] bytes) {
		System.out.println(bytesAsString(bytes));
	}

	/**
	 * Print out string value of byte array
	 */
	public static void printHexAsString(byte[] bytes) {
		try {
			System.out.println(new String(bytes, "UTF-8"));
		} catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Format data for printing to submission file
	 * @param title what to put at the start of the line
	 * @param data the data to write
	 * @return formatted data as a String
	 */
	public static String formatData(String title, BigInteger data) {
		StringBuilder builder = new StringBuilder();

		builder.append(title);
		builder.append(": ");
		builder.append(PrintUtils.bytesAsString(data.toByteArray()));
		builder.append(" Number of bits: ");
		builder.append(data.bitLength());

		return builder.toString();
	}
}
