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
	private static final String CIPHER_INSTANCE = "AES/CBC/NoPadding";

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

		System.out.println("Data before: " + dataToEncrypt.length + " " + PrintUtils.bytesAsString(dataToEncrypt));
		dataToEncrypt = Padder.applyPadding(dataToEncrypt);
		System.out.println("Data after : " + dataToEncrypt.length + " " + PrintUtils.bytesAsString(dataToEncrypt));

		byte[] encryptedData = encryptFile(cipher, dataToEncrypt);
		System.out.println("Encrypted Data: " + PrintUtils.bytesAsString(encryptedData));

		return cipher;
	}

	/* Encrypt the file with given cipher and data as byte array */
	private static byte[] encryptFile(Cipher cipher, byte[] dataToWrite) {
		try {
			return cipher.doFinal(dataToWrite);
		} catch(Exception e) {
			System.out.println("Error encrypting data");
			//e.printStackTrace();
			exit();

			return null;
		}
	}

	private static byte[] applyPadding(byte[] dataToPad) {
		byte[] paddedData = dataToPad;
		if(dataToPad.length % 16 == 0) {
		} else {
			int bytesToPad = dataToPad.length % 16;
			int bitsToPad = (dataToPad.length * 8) % 128;

			byte[] padding = createPadding(bytesToPad, bitsToPad, dataToPad);
		}

		return paddedData;
	}

	private static byte[] createPadding(int bytesToPad, int bitsToPad, byte[] dataToPad) {
		int leftoverBits = bytesToPad % bitsToPad;
		int oddByteBits = leftoverBits % 8;
		String bits = "1";
		for(int i = 0; i < oddByteBits - 1; i++) {
			bits += "0";
		}

		byte firstByte = (byte) Integer.parseInt(bits, 2);
		System.out.println("Bytes: " + bytesToPad + " Bits: " + bitsToPad + " Data Bytes: " + dataToPad.length);
		byte lastByte = dataToPad[dataToPad.length - bytesToPad];
		String ss = Byte.toString(lastByte);
		int i = Integer.parseInt(ss);
		System.out.println(i);
		String binI = Integer.toBinaryString(i);
		byte newLastByte = (byte) Integer.parseInt(ss, 2);

		System.out.println(binI);
		System.out.println(oddByteBits);
		return null;
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
