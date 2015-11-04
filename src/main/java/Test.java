import java.math.BigInteger;
import static javax.crypto.Cipher.ENCRYPT_MODE;
import static java.nio.charset.StandardCharsets.UTF_8;

class Test {
	private static final int BITS = 128;
	private static final String FILE = "../src/main/java/keys";

	public static void main(String[] args) {
		PrintWriterFacade writer = new PrintWriterFacade(FILE);

		/* Generate Salt and write to file */
		BigInteger salt = KeyGenerator.generateKey(BITS);
		writer.writeLine("Salt: " + PrintUtils.bytesAsString(salt.toByteArray()));

		/* Generate Password, add salt and write to file */
		Password p = new Password();
		p.setSalt(salt);
		writer.writeLine("Password: " + p.getPassword());
		writer.writeLine("Salted Password: " + p.getSaltPassword());

		/* Hash Password and write to file */
		byte[] encryptionKey = PasswordHasher.hashPassword(p.getSaltPassword());
		writer.writeLine("Encryption Key: " + PrintUtils.bytesAsString(encryptionKey) + " Number of bits: " + encryptionKey.length * 8);

		/* Message to encrypt */
		ScannerFacade scanner = new ScannerFacade("/home/conor/work/college/year4/cryptography/src/main/java/binf");
		String file = "";

		while(scanner.hasNext()) {
			file += scanner.next();
		}

		scanner.close();
		
		byte[] dataToEncrypt = file.getBytes(UTF_8);

		/* Create encryptor to encrypt the data */
		Encryptor encryptor = new Encryptor(ENCRYPT_MODE, encryptionKey);
		encryptor.encrypt(dataToEncrypt);

		byte[] encryptedPassword = Encryptor.rsaEncrypt(p.getPassword());
		writer.writeLine("Encrypted Data: " + PrintUtils.bytesAsString(encryptedPassword) + " Number of bits: " + encryptedPassword.length * 8);

		byte[] iv = encryptor.getIV();
		writer.writeLine("IV: " + PrintUtils.bytesAsString(iv) + " Number of bits: " + iv.length * 8);

		writer.close();
	}
}
