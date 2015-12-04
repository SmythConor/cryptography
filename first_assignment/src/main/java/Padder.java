/**
 * Class to add Padding to a byte array
 * @author Conor Smyth <conor.smyth39@mail.dcu.ie>
 * @since 2015-10-23
 * All work is my own
 */
class Padder {
	/**
	 * Apply Padding to data supplied
	 * Default block size is 128-bits
	 * @param blockSize size of the blocks
	 * @param dataToPad Data to be padded
	 * @return byte[] with padded data
	 */
	public static byte[] applyPadding(int blockSize, byte[] dataToPad) {
		byte[] padding = null;
		int byteCount = dataToPad.length % blockSize;
		if(byteCount == 0) {
			padding = createPadding(blockSize, byteCount);
		} else {
			int bytesToPad = blockSize - byteCount;

			padding = createPadding(blockSize, bytesToPad);
		}

		byte[] paddedData = concatArray(dataToPad, padding);

		return paddedData;
	}

	/* Create block of padding */
	private static byte[] createPadding(int blockSize, int bytes) {
		if(bytes == 0) {
			bytes = blockSize;
		}

		byte[] padding = new byte[bytes];
		byte pad = (byte) Integer.parseInt("10000000", 2);

		padding[0] = pad;

		return padding;
	}

	/**
	 * Concatenate two arrays
	 * @param first array
	 * @param second array
	 * @return byte array with first and second concatenated
	 */
	public static byte[] concatArray(byte[] first, byte[] second) {
		byte[] concat = new byte[first.length + second.length];

		System.arraycopy(first, 0, concat, 0, first.length);
		System.arraycopy(second, 0, concat, first.length, second.length);

		return concat;
	}
}
