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
			System.out.println("Error creating instance of cipher " + this.CIPHER_INSTANCE);
			e.printStackTrace();
		}
		
		if(iv == null) {
			iv = getIntialisationVector();
		}

		Key cipherKey = new SecretKeySpec(this.key, this.CIPHER_TYPE);

		updateKeyLimit();

		try {
			cipher.init(mode, cipherKey, new IvParameterSpec(iv));
		} catch(Exception e) {
			System.out.println("Error initialising cipher");
			e.printStackTrace();
		}

		return cipher;
	}

	/**
	 * Encrypt the data
	 * @param dataToEncrypt Data to be encrypted
	 * @return data encrypted by cipher
	 */
	public byte[] encrypt(byte[] dataToEncrypt) {
//		System.out.println("Before padding: ");
//		PrintUtils.printHexString(dataToEncrypt);
		dataToEncrypt = Padder.applyPadding(cipher.getBlockSize(), dataToEncrypt);
//		System.out.println("After padding: and bits: " + dataToEncrypt.length * 8);
//		PrintUtils.printHexString(dataToEncrypt);

		byte[] encryptedData = executeCipher(dataToEncrypt);

		return encryptedData;
	}

	public byte[] decrypt(byte[] dataToDecrypt) {
		byte[] decryptedData = executeCipher(dataToDecrypt);

		return decryptedData;
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

			return null;
		}
	}

	public static byte[] rsaEncrypt(String password) {
		BigInteger exponent = new BigInteger(RsaInfo.getExponent());
		BigInteger modulus = getModulus();

		BigInteger dataToEncrypt = new BigInteger(password.getBytes(UTF_8));

		BigInteger encryptedData = modPow(dataToEncrypt, exponent, modulus);

		return encryptedData.toByteArray();
	}

	public static byte[] rsaDecrypt() {
		throw new UnsupportedOperationException();
	}

	private static BigInteger modPow(BigInteger dataToEncrypt, BigInteger exponent, BigInteger modulus) {
		BigInteger y = BigInteger.ONE;

		for(BigInteger i = BigInteger.ZERO; i.compareTo(exponent) < 0; exponent = exponent.shiftRight(1)) {
			if(exponent.testBit(0)) {
				y = (y.multiply(dataToEncrypt)).mod(modulus);
			}

			dataToEncrypt = (dataToEncrypt.multiply(dataToEncrypt)).mod(modulus);
		}

		return y.mod(modulus);
	}

	private static BigInteger getModulus() {
		return new BigInteger(RsaInfo.getKey(), 16);
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
		}
	}
}
