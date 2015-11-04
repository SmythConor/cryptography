import java.math.BigInteger;
import static javax.crypto.Cipher.ENCRYPT_MODE;
import static java.nio.charset.StandardCharsets.UTF_8;

class Test {
	private static final int BITS = 128;
	private static final String FILE = "../src/main/java/data";
	//private static final String 

	public static void main(String[] args) {
		PrintWriterFacade writer = new PrintWriterFacade(FILE);

		/* Generate Salt and write to file */
		BigInteger salt = KeyGenerator.generateKey(BITS);

		/* Generate Password, add salt and write to file */
		Password p = new Password();
		p.setSalt(salt);

		/* Hash Password and write to file */
		byte[] encryptionKey = PasswordHasher.hashPassword(p.getSaltPassword());

		/* Message to encrypt */
		ScannerFacade scanner = new ScannerFacade("/home/conor/work/college/year4/cryptography/src/main/java/test");
		String file = "";

		while(scanner.hasNext()) {
			file += scanner.next();
		}

		scanner.close();
		
		byte[] dataToEncrypt = file.getBytes(UTF_8);

		/* Create encryptor to encrypt the data */
		Encryptor encryptor = new Encryptor(ENCRYPT_MODE, encryptionKey);
		byte[] encryptedData = encryptor.encrypt(dataToEncrypt);
		System.out.println(PrintUtils.bytesAsString(encryptedData));

		byte[] encryptedPassword = Encryptor.rsaEncrypt(p.getPassword());

		byte[] iv = encryptor.getIV();

		/* Print Everything */
		writer.writeLine("Salt: " + PrintUtils.bytesAsString(salt.toByteArray()) + " Number of bits: " + salt.bitLength());
		writer.writeLine("IV: " + PrintUtils.bytesAsString(iv) + " Number of bits: " + iv.length * 8);
		writer.writeLine("Encrypted Password: " + PrintUtils.bytesAsString(encryptedPassword) + " Number of bits: " + encryptedPassword.length * 8);

		writer.close();
	}
}
