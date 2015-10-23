import java.math.BigInteger;
import java.lang.reflect.Field;
import javax.crypto.Cipher;
import static javax.crypto.Cipher.ENCRYPT_MODE;
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
		Cipher cipher = initialiseCipher(ENCRYPT_MODE, encryptionKey);

		dataToEncrypt = Padder.applyPadding(dataToEncrypt);

		byte[] encryptedData = executeCipher(cipher, dataToEncrypt);

		return cipher;
	}

	/* Encrypt the file with given cipher and data as byte array */
	private static byte[] executeCipher(Cipher cipher, byte[] data) {
		try {
			return cipher.doFinal(data);
		} catch(Exception e) {
			System.out.println("Error encrypting/decrypting data");
			e.printStackTrace();
			exit();

			return null;
		}
	}

	public static void rsaEncrypt(String password) {
		BigInteger exponent = new BigInteger("65537");
		BufferedReaderFacade reader = new BufferedReaderFacade("/home/conor/work/college/year4/cryptography/mod");

		String key = reader.readLine();
		BigInteger modulus = new BigInteger(key.getBytes());
		BigInteger dataToEncrypt = new BigInteger(password.getBytes());

		BigInteger encryptedData = dataToEncrypt.modPow(exponent, modulus);

		System.out.println(encryptedData);
	}

	public static BigInteger tempRsaEncrypt(String password) {
		BigInteger exponent = new BigInteger("65537");
		BufferedReaderFacade pub = new BufferedReaderFacade("/home/conor/work/college/year4/cryptography/src/main/java/new_mod");
		BufferedReaderFacade prv = new BufferedReaderFacade("/home/conor/work/college/year4/cryptography/src/main/java/n_mod");
		String mod = pub.readLine();
		BigInteger n = new BigInteger(mod.getBytes());

		BigInteger result = new BigInteger(password.getBytes());

		result = result.modPow(exponent, n);
		System.out.println(result);

		exponent = exponent.modInverse(new BigInteger("65536"));
		mod = prv.readLine();
		n = new BigInteger(mod.getBytes());
		result = result.modPow(exponent, n);
		byte[] bbs = result.toByteArray();
		for(byte b : bbs) {
			System.out.print(b);
		}

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
