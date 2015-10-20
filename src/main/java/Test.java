import java.math.BigInteger;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import javax.crypto.Cipher;
import java.util.BitSet;

//Password + salt(128-bit) done
//Concat password and slat done 
//Hash 200 times = key (k) done 
//generate IV(128-bit) done
//encrypt file using (k) with block size 128-bit
////use IV encryption of 128-bit
////pad with if 1011 is final block make it 10111000000etc
////if full pad with new block 1000000000000etc

@SuppressWarnings("unchecked")
class Test {
	private static final int BITS = 128;
	private static final String FILE = "../src/main/java/keys";

	public static void main(String[] args) {
		PrintWriterFacade writer = new PrintWriterFacade(FILE);

		/* Generate Salt and write to file */
		BigInteger salt = KeyGenerator.generateKey(BITS);
		writer.writeLine("Salt: " + salt);

		/* Generate Password, add salt and write to file */
		Password p = new Password();
		p.setSalt(salt);
		writer.writeLine("Password: " + p.getPassword());
		writer.writeLine("Salted Password: " + p.getSaltPassword());

		/* Hash Password and write to file */
		byte[] hashedPassword = PasswordHasher.hashPassword(p.getSaltPassword());
		writer.writeLine("Hashed Password: " + PrintUtils.bytesAsString(hashedPassword) + " Number of bits: " + hashedPassword.length * 8);

		/* Generate IV and write to file */
		BigInteger iv = KeyGenerator.generateKey(BITS);
		writer.writeLine("IV: " + iv);

		/* Message to encrypt */
		String message = "message";

		byte dataToWrite[] = message.getBytes(StandardCharsets.UTF_8);

		/* Create Cipher; to be changed to just encrpyt file */
		Cipher cipher = Encryptor.encryptFile(hashedPassword);

		if((dataToWrite.length * 8) % 128 == 0) {
			byte[] encrypted = null;
			try {
				encrypted = cipher.doFinal(dataToWrite);
			} catch(Exception e) {
				System.out.println("Error encrypting message");
				e.printStackTrace();
			}

			System.out.println(PrintUtils.bytesAsString(encrypted));
		} 
		
		/* Messing with combining padding */
		else if(false) {
			System.out.println((dataToWrite.length * 8) % 128);
			int i = (dataToWrite.length * 8) % 128;
			BitSet b = new BitSet(i);
			b.set(i - 1);
			byte[] l = b.toByteArray();
			System.out.println("Needs padding");
			byte[] k = "Hello my name is conor".getBytes();
			byte[] inter = new byte[k.length + l.length];
			System.arraycopy(l, 0, inter, 0, l.length);
			System.arraycopy(k, 0, inter, l.length, k.length);
			System.out.println(inter.length);
			try {
				byte[] encrypted = 	cipher.doFinal(inter);
			} catch(Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Padding needed");
		}

		writer.close();
	}
	
	/* Messing with padding */
	public static void getB() {
		BitSet b = new BitSet(8);
		b.flip(7);
		b.set(7);
		System.out.println(b);
	}
}
