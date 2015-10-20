import java.math.BigInteger;

class PrintUtils {

	public static String bytesAsString(byte[] bytes) {
		return String.format("%064x", new BigInteger(1, bytes));
	}
	
	public static void printByteArray(byte[] bytes) {
		for(byte b : bytes) {
			System.out.print(b);
		}

		System.out.println();
	}
}
