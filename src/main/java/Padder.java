class Padder {
	public static byte[] applyPadding(byte[] dataToPad) {
		byte[] paddedData = null;
		if(dataToPad.length % 16 == 0) {
			byte[] padding = createPadding();

			paddedData = new byte[dataToPad.length + padding.length];

			System.arraycopy(dataToPad, 0, paddedData, 0, dataToPad.length);
			System.arraycopy(padding, 0, paddedData, dataToPad.length, padding.length);
		} else {
			paddedData = dataToPad;
		}

		return paddedData;
	}

	/* Create 128-bit block of padding */
	private static byte[] createPadding() {
		byte[] padding = new byte[16];
		byte pad = (byte) Integer.parseInt("10000000", 2);

		padding[0] = pad;

		return padding;
	}
}
