import java.math.BigInteger;
import static javax.crypto.Cipher.ENCRYPT_MODE;
import static javax.crypto.Cipher.DECRYPT_MODE;
import static java.nio.charset.StandardCharsets.UTF_8;
import java.io.FileInputStream;
import java.io.File;

class Main {
	private static final int BITS = 128;
	private static final String FILE = "../src/main/java/data";

	public static void main(String[] args) {
		PrintWriterFacade writer = new PrintWriterFacade(FILE);

		/* Generate Salt and write to file */
		byte[] salt = KeyGenerator.generateKey(BITS);

		/* Generate Password, add salt and write to file */
		Password p = new Password();
		p.setSalt(salt);

		/* Hash Password and write to file */
		byte[] encryptionKey = PasswordHasher.hashPassword(p.getSaltPassword());

		byte[] dataToEncrypt = null;
		/* Message to encrypt */
		try { //will be able to remove this
			File f = new File("/home/conor/work/college/year4/cryptography/src/main/java/test");
			byte[] file = new byte[(int) (f.length())];
			FileInputStream fis = new FileInputStream(f);
			fis.read(file);
			dataToEncrypt = file;
			fis.close();
		} catch(Exception e) {}

		//System.out.println("Before: " + PrintUtils.bytesAsString(dataToEncrypt) + " Number of bits: " + dataToEncrypt.length * 8);
//		PrintUtils.med(dataToEncrypt);

		/* Create encryptor to encrypt the data */
		Encryptor encryptor = new Encryptor(ENCRYPT_MODE, encryptionKey);
		byte[] encryptedData = encryptor.encrypt(dataToEncrypt); //change to encrypt file
		System.out.println("ENCRYPT_MODE: " + PrintUtils.bytesAsString(encryptedData) + " Number of bits: " + encryptedData.length * 8);
		//PrintUtils.med(encryptedData);
		Encryptor e = new Encryptor(DECRYPT_MODE, encryptionKey);
		byte[] decryptedData = e.decrypt(encryptedData);
		System.out.println("DECRYPT_MODE: " + PrintUtils.bytesAsString(decryptedData) + " Number of bits: " + decryptedData.length * 8);
		//PrintUtils.med(decryptedData);
		

		/* Encrypt the Password using RSA */
		byte[] encryptedPassword = Encryptor.rsaEncrypt(p.getPassword());

		/* Get the IV of the cipher */
		byte[] iv = encryptor.getIV();

		/* Print Everything */
		writer.writeLine("Salt: " + PrintUtils.bytesAsString(salt) + " Number of bits: " + salt.length * 8);
		writer.writeLine("IV: " + PrintUtils.bytesAsString(iv) + " Number of bits: " + iv.length * 8);
		writer.writeLine("Encrypted Password: " + PrintUtils.bytesAsString(encryptedPassword) + " Number of bits: " + encryptedPassword.length * 8);

		writer.close();
	}
}
