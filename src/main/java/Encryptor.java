import java.math.BigInteger;
import java.lang.reflect.Field;
import javax.crypto.Cipher;
import java.security.Key;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import static java.nio.charset.StandardCharsets.UTF_8;

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
			exit();
		}

		byte[] iv = getIntialisationVector();

		Key cipherKey = new SecretKeySpec(encryptionKey, CIPHER_TYPE);

		updateKeyLimit();

		try {
			c.init(Cipher.ENCRYPT_MODE, cipherKey, new IvParameterSpec(iv, 1, iv.length - 1));
		} catch(Exception e) {
			System.out.println("Error initialising cipher");
			e.printStackTrace();
			exit();
		}

		return c;
	}

	public static Cipher encryptFile(byte[] encryptionKey, byte[] dataToEncrypt) {
		Cipher cipher = initialiseCipher(encryptionKey);

		byte[] dataToWrite = dataToEncrypt;//.getBytes(UTF_8);
		byte[] encryptedData = null;

		if(dataToWrite.length % 16 == 0) {
			encryptedData = encrypt(cipher, dataToWrite);
		} else {
			//pad
			int bitsToPad = (dataToWrite.length * 8) % 128;

			String firstBit = Integer.toBinaryString(1);
			String lastBits = String.format("%" + bitsToPad + "s", "0");
			String padding = firstBit + lastBits;
			byte[] pad = padding.getBytes(UTF_8);
			//System.out.println(PrintUtils.bytesAsString(pad));

			encryptedData = encrypt(cipher, dataToWrite);
		}

		System.out.println(PrintUtils.bytesAsString(encryptedData));
		return cipher;
	}
	
	private static byte[] encrypt(Cipher cipher, byte[] dataToWrite) {
		try {
			return cipher.doFinal(dataToWrite);
		} catch(Exception e) {
			System.out.println("Error encrypting data");
			e.printStackTrace();
			exit();

			return null;
		}
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
			exit();
		}
	}

	private static void exit() {
		System.out.println("Exiting.");
		System.exit(0);
	}
}
