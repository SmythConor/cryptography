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
		this.cipher = initialiseCipher(mode, null);
	}

	public Encryptor(int mode, byte[] key, byte[] iv) {
		this.key = key;
		this.cipher = initialiseCipher(mode, iv);
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

	/**
	 * Initialise the cipher with the mode and key
	 */
	private Cipher initialiseCipher(int mode, byte[] iv) {
		try {
			cipher = Cipher.getInstance(CIPHER_INSTANCE);
		} catch(Exception e) {
			System.out.println("Error creating instance of cipher " + CIPHER_INSTANCE);
			e.printStackTrace();
			exit();
		}
		
		if(iv == null) {
			iv = getIntialisationVector();
		}

		Key cipherKey = new SecretKeySpec(this.key, CIPHER_TYPE);

		updateKeyLimit();

		try {
			cipher.init(mode, cipherKey, new IvParameterSpec(iv));
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
	 * @return data encrypted by cipher
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

	public static byte[] rsaEncrypt(String password) {
		BigInteger exponent = new BigInteger(RsaInfo.getExponent());
		BigInteger modulus = getModulus();

		BigInteger dataToEncrypt = new BigInteger(password.getBytes(UTF_8));

		System.out.println("Before: " + PrintUtils.bytesAsString(dataToEncrypt.toByteArray()));
		BigInteger encryptedData = modPow(dataToEncrypt, exponent, modulus);
		BigInteger encryptedData2 = modPow2(dataToEncrypt, exponent, modulus);

		System.out.println("First: " + PrintUtils.bytesAsString(encryptedData.toByteArray()));
		System.out.println("Second: " + PrintUtils.bytesAsString(encryptedData2.toByteArray()));
		//BigInteger pMod = getPMod();
		//BigInteger decryptedData = modPow(encryptedData, exponent, pMod);
		//System.out.println(PrintUtils.bytesAsString(decryptedData.toByteArray()));

		return encryptedData.toByteArray();
		//System.out.println(PrintUtils.bytesAsString(encryptedData.toByteArray()));
	}

	private static BigInteger modPow(BigInteger dataToEncrypt, BigInteger exponent, BigInteger modulus) {
		BigInteger result = BigInteger.ONE;

		while(exponent.compareTo(BigInteger.ZERO) > 0) {
			if(exponent.testBit(0)) {
				result = (result.multiply(dataToEncrypt)).mod(modulus);
			}

			exponent = exponent.shiftRight(1);
			dataToEncrypt = (dataToEncrypt.multiply(dataToEncrypt)).mod(modulus);
		}

		return result.mod(modulus);
	}

	private static BigInteger modPow2(BigInteger dataToEncrypt, BigInteger exponent, BigInteger modulus) {
		BigInteger result = BigInteger.ONE;
		BigInteger org = exponent;

		while(exponent.compareTo(org) < 0) {
			if(exponent.testBit(0)) {
				result = (result.multiply(dataToEncrypt)).mod(modulus);
			}

			exponent = exponent.shiftLeft(1);
			dataToEncrypt = (dataToEncrypt.multiply(dataToEncrypt)).mod(modulus);
		}

		return result.mod(modulus);
	}

	private static BigInteger getModulus() {
		//return new BigInteger(RsaInfo.getKey(), 16); //will be this
		ScannerFacade scanner = new ScannerFacade("../src/main/java/mod");
		String key = "";

		while(scanner.hasNext()) {
			key += scanner.next();
		}

		scanner.close();

		return new BigInteger(key, 16);
	}

	private static BigInteger getPMod() {
		ScannerFacade scanner = new ScannerFacade("../src/main/java/pmod");
		String key = "";

		while(scanner.hasNext()) {
			key += scanner.next();
		}

		scanner.close();

		return new BigInteger(key, 16);
	}

	/**
	 * Generate and Initialisation vector for the cipher
	 * @return IV as a byte array
	 */
	private byte[] getIntialisationVector() {
		return KeyGenerator.generateKey(STD_BITS);
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
