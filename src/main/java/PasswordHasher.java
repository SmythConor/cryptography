import java.security.MessageDigest;

import java.security.NoSuchAlgorithmException;

/**
 * @author Conor Smyth <conor.smyth39@mail.dcu.ie>
 * @since 2015-10-19
 * All work is my own
 */
class PasswordHasher {
	private static final String HASH = "SHA-256";

	/**
	 * Hash Password 200 times using SHA-256 algorithm
	 * @param password Password to hash as a String
	 * @return byte[] with 256 bit password hashed
	 */
	public static byte[] hashPassword(byte[] password) {
		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance(HASH);
		} catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		messageDigest.update(password);
		byte[] digest = null;

		for(int hashCounter = 0; hashCounter < 200; hashCounter++) {
			digest = messageDigest.digest();

			messageDigest.update(digest);
		}

		return digest;
	}
}
