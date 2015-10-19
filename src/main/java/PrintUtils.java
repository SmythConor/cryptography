class PrintUtils {

	public static void printStringAsHex(byte[] bytes) {
		System.out.println(String.format("%064x", new java.math.BigInteger(1, bytes)));
	}
	
	public static void printByteArray(byte[] bytes) {
		for(byte b : bytes) {
			System.out.print(b);
		}

		System.out.println();
	}
}
