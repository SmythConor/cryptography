import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.math.BigInteger;
import javax.xml.bind.DatatypeConverter;

class PasswordHasher {
	private static final String HASH = "SHA-256";

	public static byte[] hashPassword(String password) {
		MessageDigest messageDigest = null;
		
		try {
			messageDigest = MessageDigest.getInstance(HASH);
		} catch(Exception e) {
			e.printStackTrace();
		}

		messageDigest.update(password.getBytes());
		byte[] digest = null;

		for(int hashCounter = 0; hashCounter < 200; hashCounter++) {
			digest = messageDigest.digest();

			messageDigest.update(digest);
		}

		return digest;
	}
}
