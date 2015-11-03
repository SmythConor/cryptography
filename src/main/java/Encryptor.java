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
	private Cipher cipher;
	private byte[] key;

	private static final int STD_BITS = 128;
	private static final String CIPHER_TYPE = "AES";
	private static final String CIPHER_INSTANCE = "AES/CBC/NoPadding";

	private static final String EXPONENT = "65537";

	public Encryptor(int mode, byte[] key) {
		this.key = key;
		this.cipher = initialiseCipher(mode, this.key);
	}

	public Cipher getCipher() {
		return this.cipher;
	}

	public void setCipher(Cipher cipher) {
		this.cipher = cipher;
	}

	public byte[] getKey() {
		return this.key;
	}

	public void setKey(byte[] key) {
		this.key = key;
	}

	public byte[] getIV() {
		return this.cipher.getIV();
	}

	/* initialise Cipher with the key supplied */
	/**
	 * Initialise the cipher with the mode and key
	 */
	private Cipher initialiseCipher(int mode, byte[] key) {
		try {
			cipher = Cipher.getInstance(CIPHER_INSTANCE);
		} catch(Exception e) {
			System.out.println("Error creating instance of cipher " + CIPHER_INSTANCE);
			e.printStackTrace();
			exit();
		}

		byte[] iv = getIntialisationVector();

		Key cipherKey = new SecretKeySpec(key, CIPHER_TYPE);

		updateKeyLimit();

		try {
			cipher.init(mode, cipherKey, new IvParameterSpec(iv, 1, iv.length - 1));
		} catch(Exception e) {
			System.out.println("Error initialising cipher");
			e.printStackTrace();
			exit();
		}

		return cipher;
	}

	/**
	 * Encrypt the data
	 * @param dataToEncrypt Data to be encrypted
	 @ @return data encrypted by cipher
	 */
	public byte[] encrypt(byte[] dataToEncrypt) {
		dataToEncrypt = Padder.applyPadding(cipher.getBlockSize(), dataToEncrypt);

		byte[] encryptedData = executeCipher(dataToEncrypt);

		return encryptedData;
	}

	/**
	 * Execute cipher on data
	 * @param data data to be encrypted/decrypted
	 * @return data encrypted/decrypted
	 */
	private byte[] executeCipher(byte[] data) {
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
		BigInteger exponent = new BigInteger(EXPONENT);
		ScannerFacade scanner = new ScannerFacade("../src/main/java/mod");
		String key = "";

		while(scanner.hasNext()) {
			key += scanner.next();
		}

		scanner.close();

		BigInteger modulus = new BigInteger(key, 16);
		BigInteger dataToEncrypt = new BigInteger(password.getBytes(UTF_8));

		BigInteger encryptedData = dataToEncrypt.modPow(exponent, modulus);
		System.out.println(PrintUtils.bytesAsString(encryptedData.toByteArray()));
	}

	private static BigInteger modPow(BigInteger dataToEncrypt, BigInteger exponent, BigInteger modulus) {
		BigInteger a = (dataToEncrypt.multiply(dataToEncrypt)).mod(modulus);

		long pow;
		for(pow = 4; pow * pow <= exponent.longValue(); pow = pow * pow) {
			a = (a.multiply(a)).mod(modulus);
		}


		if(pow < exponent.longValue()) {
			for(; pow < exponent.longValue(); pow++) {
				a = (a.multiply(a)).mod(modulus);
			}
		}

		return a;
	}

	/**
	 * Generate and Initialisation vector for the cipher
	 * @return IV as a byte array
	 */
	private byte[] getIntialisationVector() {
		return KeyGenerator.generateKey(STD_BITS).toByteArray();
	}

	/* Update java security defaults to allow for 256 key size */
	private void updateKeyLimit() {
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
	private void exit() {
		System.out.println("Exiting.");
		System.exit(0);
	}
}
