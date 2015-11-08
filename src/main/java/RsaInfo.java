class RsaInfo {
	private static final String EXPONENT = "65537";

	//	private static final String PUBLIC_KEY = "c406136c12640a665900a9df4df63a84fc855927b729a3a106fb3f379e8e4190ebba442f67b93402e535b18a5777e6490e67dbee954bb02175e43b6481e7563d3f9ff338f07950d1553ee6c343d3f8148f71b4d2df8da7efb39f846ac07c865201fbb35ea4d71dc5f858d9d41aaa856d50dc2d2732582f80e7d38c32aba87ba9";

	private static final String PUBLIC_KEY = "a88845cb21cf2cc241f33484167eab1aca2a0a5b6c9ac84ce2b1f55f488944101ad77279f5c7e3627918114355f6886e50eea533e533ad92cea6c7ba9e6534172e1a784e609fb0aa5471d8e8ad5427092e0d2669de919a63d461f896da74510033b9ac808d75a1d36870eeec77d0b308776bc2614131003ca9cae9e0acfdae4f";

	private static final String PRIVATE_KEY = "";

	/**
	 * Get the exponent for rsa encryption
	 * @return exponent as a string
	 */
	public static String getExponent() {
		return EXPONENT;
	}

	/**
	 * Get the public key for the rsa encryption
	 * @return public key as a string
	 */
	public static String getPublicKey() {
		return PUBLIC_KEY;
	}

	/**
	 * Get the private key for the rsa decryption, not currently implemented
	 * @return private key as a string
	 */
	public static String getPrivateKey() {
		throw new UnsupportedOperationException();
	}
}
