import java.math.BigInteger;

import java.lang.reflect.Field;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;

import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;
import java.security.InvalidAlgorithmParameterException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.BadPaddingException;

/**
 * Encryptor class to execute encryptor functions
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

	/**
	 * Return the cipher used for encryption
	 * @return cipher 
	 */
	public Cipher getCipher() {
		return this.cipher;
	}

	/**
	 * Return the key used by the cipher
	 * @return key as byte array
	 */
	public byte[] getKey() {
		return this.key;
	}

	/**
	 * Return the IV used by the cipher
	 * @return the IV for the cipher as a byte array
	 */
	public byte[] getIV() {
		return this.cipher.getIV();
	}

	/**
	 * Initialise the cipher with the mode and key
	 * @param mode Mode for the cipher
	 * @param iv IV as a byte array
	 */
	private Cipher initialiseCipher(int mode, byte[] iv) {
		try {
			cipher = Cipher.getInstance(CIPHER_INSTANCE);
		} catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch(NoSuchPaddingException e) {
			e.printStackTrace();
		}

		/* check if iv is needed */
		if(iv == null) {
			iv = getIntialisationVector();
		}

		Key cipherKey = new SecretKeySpec(this.key, this.CIPHER_TYPE);

		/* allow for larger key sizes */
		updateKeyLimit();

		try {
			cipher.init(mode, cipherKey, new IvParameterSpec(iv));
		} catch(InvalidKeyException e) {
			e.printStackTrace();
		} catch(InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		}

		return cipher;
	}

	/**
	 * Encrypt the file, resulting file name will be encrypted_fileName
	 * @param fileName name of the file to encrypt
	 * @return the name of the encrypted file as a string
	 */
	public String encryptFile(String fileName) {
		String encryptedFileName = String.format("encypted_%s", fileName);
		FileStreamFacade io = new FileStreamFacade(fileName, encryptedFileName);

		byte[] dataToEncrypt = io.readFile();

		dataToEncrypt = Padder.applyPadding(cipher.getBlockSize(), dataToEncrypt);

		byte[] encryptedData = executeCipher(dataToEncrypt);

		/* write bytes to file */
		//io.writeFile(encryptedData);

		/* write bytes to file as hex */
		io.writeHexFile(encryptedData);

		io.close();

		return encryptedFileName;
	}

	/**
	 * Decrypt the file, resulting file name will be decrypted_fileName
	 * @param fileName name of the file to decrypt
	 * @return the name of the decrypted file
	 */
	public String decryptFile(String fileName) {
		String decryptedFileName = String.format("decrypted_%s", fileName);
		FileStreamFacade io = new FileStreamFacade(fileName, decryptedFileName);

		byte[] dataToDecrypt = io.readFile();

		byte[] decryptedData = executeCipher(dataToDecrypt);

		io.writeFile(decryptedData);

		io.close();

		return decryptedFileName;
	}

	/**
	 * Execute cipher on data
	 * @param data data to be encrypted/decrypted
	 * @return data encrypted/decrypted as byte array
	 */
	private byte[] executeCipher(byte[] data) {
		try {
			return cipher.doFinal(data);
		} catch(IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch(BadPaddingException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Encrypt the string passed using RSA
	 * @param data as a byte array
	 * @return encrypted data as a byte array
	 */
	public static byte[] rsaEncrypt(byte[] data) {
		BigInteger exponent = new BigInteger(RsaInfo.getExponent());
		BigInteger modulus = new BigInteger(RsaInfo.getPublicKey(), 16);
		BigInteger dataToEncrypt = new BigInteger(data);

		BigInteger encryptedData = modPow(dataToEncrypt, exponent, modulus);

		return encryptedData.toByteArray();
	}

	/**
	 * modPow function to replace to java.math.BigInteger#modPow, Uses right to left bit square and multiply algorithm
	 * @param data data to encrypt/decrypt
	 * @param exponent exponent for power
	 * @param modulus modulus for mod
	 * @return BigInteger result of applying 
	 */
	private static BigInteger modPow(BigInteger data, BigInteger exponent, BigInteger modulus) {
		BigInteger y = BigInteger.ONE;

		for(BigInteger i = BigInteger.ZERO; i.compareTo(exponent) < 0; exponent = exponent.shiftRight(1)) {
			if(exponent.testBit(0)) {
				y = (y.multiply(data)).mod(modulus);
			}

			data = (data.multiply(data)).mod(modulus);
		}

		return y.mod(modulus);
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
		Field field = null;

		try {
			field = Class.forName("javax.crypto.JceSecurity").
				getDeclaredField("isRestricted");
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch(NoSuchFieldException e) {
			e.printStackTrace();
		}

		field.setAccessible(true);

		try {
			field.set(null, false);
		} catch(IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
