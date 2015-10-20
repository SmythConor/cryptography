import java.lang.reflect.Field;
import javax.crypto.Cipher;
import java.security.Key;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;

class Encryptor {
	private static final int STD_BITS = 128;
	private static final String CIPHER_TYPE = "AES";
	private static final String CIPHER_INSTANCE = "AES/CBC/PKCS5Padding";

	private static Cipher initialiseCipher(byte[] encryptionKey) {
		Cipher c = null;
		try {
			c = Cipher.getInstance(CIPHER_INSTANCE);
		} catch(Exception e) {
			System.out.println("Error creating instance of cipher " + CIPHER_INSTANCE);
			e.printStackTrace();
		}

		byte[] iv = getIntialisationVector();

		Key cipherKey = new SecretKeySpec(encryptionKey, CIPHER_TYPE);

		updateKeyLimit();

		try {
			c.init(Cipher.ENCRYPT_MODE, cipherKey, new IvParameterSpec(iv));
		} catch(Exception e) {
			System.out.println("Error initialising cipher");
			e.printStackTrace();
		}

		return c;
	}

	public static Cipher encryptFile(byte[] encryptionKey) {
		Cipher cipher = initialiseCipher(encryptionKey);
		return cipher;
	}

	private static byte[] getIntialisationVector() {
		return KeyGenerator.generateKey(STD_BITS).toByteArray();
	}

	private static void updateKeyLimit() {
		try {
			Field field = Class.forName("javax.crypto.JceSecurity").
				getDeclaredField("isRestricted");
			field.setAccessible(true);
			field.set(null, false);
		} catch(Exception e) {
			System.out.println("Error modifying Security limit");
			e.printStackTrace();
		}
	}
}
