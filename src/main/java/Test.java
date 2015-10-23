import java.math.BigInteger;
import javax.crypto.Cipher;

//Password + salt(128-bit) done
//Concat password and slat done 
//Hash 200 times = key (k) done 
//generate IV(128-bit) done
//encrypt file using (k) with block size 128-bit done
////use IV encryption of 128-bit generated
////pad with if 1011 is final block make it 10111000000etc
////if full pad with new block 1000000000000etc

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
		byte[] hashedPassword = PasswordHasher.hashPassword(p.getSaltPassword());
		writer.writeLine("Hashed Password: " + PrintUtils.bytesAsString(hashedPassword) + " Number of bits: " + hashedPassword.length * 8);

		/* Message to encrypt */
		ScannerFacade scanner = new ScannerFacade("/home/conor/work/college/year4/cryptography/src/main/java/binf");
		String file = "";

		while(scanner.hasNext()) {
			file += scanner.next();
		}

		scanner.close();

		
		byte[] dataToEncrypt = file.getBytes();

		/* Create Cipher; to be changed to just encrpyt file */
		Cipher cipher = Encryptor.encryptFile(hashedPassword, dataToEncrypt);

		byte[] iv = cipher.getIV();
		writer.writeLine("IV: " + PrintUtils.bytesAsString(iv) + " Number of bits: " + iv.length * 8);

		//BigInteger result = Encryptor.tempRsaEncrypt(p.getPassword());
		//Encryptor.rsaEncrypt(p.getPassword());

		writer.close();
	}
}
