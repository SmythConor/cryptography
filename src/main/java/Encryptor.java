import java.math.BigInteger;
import java.lang.reflect.Field;
import javax.crypto.Cipher;
import java.security.Key;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
* @author Conor Smyth <conor.smyth39@mail.dcu.ie>
* @since 2015-10-20
* All work is my own
*/
class Encryptor {
	private static final int STD_BITS = 128;
	private static final String CIPHER_TYPE = "AES";
	private static final String CIPHER_INSTANCE = "AES/CBC/PKCS5Padding";

	/* initialise Cipher with the key supplied */
	private static Cipher initialiseCipher(int mode, byte[] encryptionKey) {
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
		Cipher cipher = initialiseCipher(Cipher.ENCRYPT_MODE, encryptionKey);

		byte[] dataToWrite = dataToEncrypt;
		byte[] encryptedData = null;

		//if message if less than block size finish block with 1000...
		//if message is the same as block size add block 1000000000....
		if(dataToWrite.length % 16 == 0) {
			System.out.println("Data before: " + dataToWrite.length + " " + PrintUtils.bytesAsString(dataToWrite));
			applyPadding(dataToWrite);
			System.out.println("Data after: " + dataToWrite.length + " " + PrintUtils.bytesAsString(dataToWrite));
			encryptedData = encryptFile(cipher, dataToWrite);
		} else {
			System.out.println("Data before: " + PrintUtils.bytesAsString(dataToWrite));
			dataToWrite = applyPadding(dataToWrite);
			System.out.println("Data after: " + PrintUtils.bytesAsString(dataToWrite));
			encryptedData = encryptFile(cipher, dataToWrite);
		}

		System.out.println("Encrypted Data: " + PrintUtils.bytesAsString(encryptedData));
		return cipher;
	}

	/* Encrypt the file with given cipher and data as byte array */
	private static byte[] encryptFile(Cipher cipher, byte[] dataToWrite) {
		try {
			return cipher.doFinal(dataToWrite);
		} catch(Exception e) {
			System.out.println("Error encrypting data");
			e.printStackTrace();
			exit();

			return null;
		}
	}

	private static byte[] applyPadding(byte[] dataToPad) {
		if(dataToPad.length % 16 == 0) {
			byte padding = (byte) Integer.parseInt("1000000", 2);

			byte[] pad = {padding};
			byte[] paddedData = new byte[dataToPad.length + pad.length];

			System.arraycopy(dataToPad, 0, paddedData, 0, dataToPad.length);
			System.arraycopy(pad, 0, paddedData, dataToPad.length, pad.length);

			System.out.println(PrintUtils.bytesAsString(pad));

			return paddedData;
		}

		return dataToPad;
	}

	/* Generate an IV */
	private static byte[] getIntialisationVector() {
		return KeyGenerator.generateKey(STD_BITS).toByteArray();
	}

	/* Update java security defaults to allow for 256 key size */
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

	/* exit method to exit program if exception caught */
	private static void exit() {
		System.out.println("Exiting.");
		System.exit(0);
	}
}
