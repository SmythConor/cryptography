import java.security.MessageDigest;

import java.security.NoSuchAlgorithmException;

/**
 * Class to hash data
 * All work is my own
 * @author Conor Smyth <conor.smyth39@mail.dcu.ie>
 * @since 2015-11-17
 */
class Hasher {
	private static final String HASH = "SHA-256";

	/**
	 * Hash the data using SHA-256
	 * @param dataToHash data to hash
	 * @return data hashed as byte array
	 */
	public static byte[] hash(byte[] dataToHash) {
		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance(HASH);
		} catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		messageDigest.update(dataToHash);

		byte[] digest = messageDigest.digest();

		return digest;
	}
}
