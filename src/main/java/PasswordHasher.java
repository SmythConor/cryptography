import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.math.BigInteger;
import javax.xml.bind.DatatypeConverter;

class PasswordHasher {
	private static final String HASH = "SHA-256";

	public static void hashPassword(String password) {
		MessageDigest messageDigest = null;
		
		try {
			messageDigest = MessageDigest.getInstance(HASH);
		} catch(Exception e) {
			e.printStackTrace();
		}

		messageDigest.update(password.getBytes());

		for(int hashCounter = 0; hashCounter < 200; hashCounter++) {
			byte[] digest = messageDigest.digest();

			PrintUtils.printStringAsHex(digest);

			messageDigest.update(digest);
		}
	}
}
