import java.math.BigInteger;
import java.io.FileOutputStream;
import static java.nio.charset.StandardCharsets.UTF_8;
import javax.crypto.Cipher;
import java.util.BitSet;

//Password + salt(128-bit) done
//Concat password and slat done 
//Hash 200 times = key (k) done 
//generate IV(128-bit) done
//encrypt file using (k) with block size 128-bit
////use IV encryption of 128-bit generated
////pad with if 1011 is final block make it 10111000000etc
////if full pad with new block 1000000000000etc

//check number of bits short, flip index d

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
		BigInteger me = KeyGenerator.generateKey(128);
		byte[] temp = me.toByteArray();
		byte[] ll = new byte[16];
		for(int i = 1; i < ll.length - 1; i++) {
			ll[i] = temp[i];
		}

		/* Create Cipher; to be changed to just encrpyt file */
		Cipher cipher = Encryptor.encryptFile(hashedPassword, ll);

		byte[] iv = cipher.getIV();
		writer.writeLine("IV: " + PrintUtils.bytesAsString(iv) + " Number of bits: " + iv.length * 8);

		writer.close();
	}
}
