/**
 * @author Conor Smyth <conor.smyth39@mail.dcu.ie>
 * @since 2015-10-23
 * All work is my own
 */
class Padder {
	private static final int BLOCK_SIZE = 16;

	/**
	 * Apply Padding to data supplied
	 * Default block size is 128-bits
	 * @param dataToPad Data to be padded
	 * @return byte[] with padded data
	 */
	public static byte[] applyPadding(byte[] dataToPad) {
		if(dataToPad.length % BLOCK_SIZE == 0) {
			byte[] padding = createPadding(BLOCK_SIZE);
		} else {
			int bytesToPad = BLOCK_SIZE - dataToPad.length;

			byte[] padding = createPadding(bytesToPad);
		}

		byte[] paddedData = new byte[dataToPad.length + padding.length];

		System.arraycopy(dataToPad, 0, paddedData, 0, dataToPad.length);
		System.arraycopy(padding, 0, paddedData, dataToPad.length, padding.length);

		return paddedData;
	}

	/* Create block of padding */
	private static byte[] createPadding(int bytes) {
		if(bytes == 0) {
			bytes = BLOCK_SIZE;
		}

		byte[] padding = new byte[bytes];
		byte pad = (byte) Integer.parseInt("10000000", 2);

		padding[0] = pad;

		return padding;
	}
}
