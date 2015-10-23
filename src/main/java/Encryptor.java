import java.math.BigInteger;
import java.lang.reflect.Field;
import javax.crypto.Cipher;
import java.security.Key;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;

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
		BigInteger exponent = new BigInteger("65537");
		BufferedReaderFacade reader = new BufferedReaderFacade("/home/conor/work/college/year4/cryptography/mod");

		String key = reader.readLine();
		BigInteger modulus = new BigInteger(key.getBytes());
		BigInteger dataToEncrypt = new BigInteger(password.getBytes());

		BigInteger encryptedData = dataToEncrypt.modPow(exponent, modulus);

		System.out.println(encryptedData);
	}

	private static BigInteger modPow(BigInteger exponent, BigInteger modulus) {
		return null;
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
