import java.math.BigInteger;
import javax.xml.bind.DatatypeConverter;

class PrintUtils {

	public static String bytesAsString(byte[] bytes) {
		return DatatypeConverter.printHexBinary(bytes).toLowerCase();
	}
	
	public static void printByteArray(byte[] bytes) {
		for(byte b : bytes) {
			System.out.print(b);
		}

		System.out.println();
	}
}
