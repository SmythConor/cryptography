import java.math.BigInteger;

import static javax.crypto.Cipher.ENCRYPT_MODE;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
* Main class to execute code
* @author Conor Smyth <conor.smyth39@mail.dcu.ie>
* @since 2015-11-05
*/
class Main {
	private static final int STD_BITS = 128;
	private static final String FILE = "/home/conor/work/college/year4/cryptography/submission_data";

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

		/* Name of file to encrypt */
		String fileNameToEncrypt = "src.zip";

		/* Create encryptor to encrypt the data */
		Encryptor encryptor = new Encryptor(ENCRYPT_MODE, encryptionKey);
		encryptor.encryptFile(fileNameToEncrypt);

		/* Get password as byte array */
		byte[] password = p.getPassword().getBytes(UTF_8);

		/* Encrypt password using RSA */
		byte[] encryptedPassword = Encryptor.rsaEncrypt(password);

		/* Get the IV of the cipher */
		byte[] iv = encryptor.getIV();

		/* Create writer to print to file */
		PrintWriterFacade writer = new PrintWriterFacade(FILE);

		/* Print Everything */
		writer.writeLine("Salt: " + PrintUtils.bytesAsString(salt) + " Number of bits: " + salt.length * 8);
		writer.writeLine("IV: " + PrintUtils.bytesAsString(iv) + " Number of bits: " + iv.length * 8);
		writer.writeLine("Encrypted Password: " + PrintUtils.bytesAsString(encryptedPassword) + " Number of bits: " + encryptedPassword.length * 8);

		/* Close writer */
		writer.close();
	}
}
