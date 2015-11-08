import java.math.BigInteger;
import static javax.crypto.Cipher.ENCRYPT_MODE;
import static javax.crypto.Cipher.DECRYPT_MODE;
import static java.nio.charset.StandardCharsets.UTF_8;
import java.io.FileInputStream;
import java.io.File;

class Main {
	private static final int STD_BITS = 128;
	private static final String FILE = "data";

	public static void main(String[] args) {
		/* Generate Salt and write to file */
		byte[] salt = KeyGenerator.generateKey(STD_BITS);

		/* Generate Password, add salt and write to file */
		Password p = new Password();
		p.setSalt(salt);

		/* Get salted password as bytes */
		byte[] saltedPassword = p.getSaltPassword().getBytes(UTF_8);

		/* Hash Password and write to file */
		byte[] encryptionKey = PasswordHasher.hashPassword(saltedPassword);

		byte[] dataToEncrypt = null;
//		String ff = "/home/conor/work/college/year4/cryptography/src/main/java/test";
		String ff = "test";

		//System.out.println("Before: " + PrintUtils.bytesAsString(dataToEncrypt) + " Number of bits: " + dataToEncrypt.length * 8);
		//		PrintUtils.med(dataToEncrypt);

		/* Create encryptor to encrypt the data */
		Encryptor encryptor = new Encryptor(ENCRYPT_MODE, encryptionKey);
		encryptor.encryptFile(ff);

		//System.out.println("ENCRYPT_MODE: " + PrintUtils.bytesAsString(encryptedData) + " Number of bits: " + encryptedData.length * 8);
		//PrintUtils.med(encryptedData);
		Encryptor e = new Encryptor(DECRYPT_MODE, encryptionKey);
		//byte[] decryptedData = e.decrypt(encryptedData);
		//System.out.println("DECRYPT_MODE: " + PrintUtils.bytesAsString(decryptedData) + " Number of bits: " + decryptedData.length * 8);
		//PrintUtils.med(decryptedData);

		/* Get password as byte array */
		byte[] password = p.getPassword().getBytes(UTF_8);

		/* Encrypt password using RSA */
		byte[] encryptedPassword = Encryptor.rsaEncrypt(password);

		/* Get the IV of the cipher */
		byte[] iv = encryptor.getIV();

		PrintWriterFacade writer = new PrintWriterFacade(FILE);

		/* Print Everything */
		writer.writeLine("Salt: " + PrintUtils.bytesAsString(salt) + " Number of bits: " + salt.length * 8);
		writer.writeLine("IV: " + PrintUtils.bytesAsString(iv) + " Number of bits: " + iv.length * 8);
		writer.writeLine("Encrypted Password: " + PrintUtils.bytesAsString(encryptedPassword) + " Number of bits: " + encryptedPassword.length * 8);

		writer.close();
	}
}
