import java.math.BigInteger;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

//Password + salt(128-bit)
//Concat password and slat
//Hash 200 times = key (k)
//encrypt file using (k) with block size 128-bit
////use IV encryption of 128-bit
////pad with if 1011 is final block make it 10111000000etc
////if full pad with new block 1000000000000etc

class Test {
	private static final int BITS = 128;
	private static final String FILE = "../src/main/java/keys";

	public static void main(String[] args) {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(FILE);
		} catch(Exception e) {
			e.printStackTrace();
		}

		BigInteger salt = KeyGenerator.generateKey(BITS);
		writer.println("Salt: " + salt);

		Password p = new Password();
		p.setSalt(salt);

		writer.println("Password: " + p.getPassword());
		writer.println("Salted Password: " + p.getSaltPassword());

		byte[] hashedPassword = PasswordHasher.hashPassword(p.getSaltPassword());

		writer.println("Hashed Password: " + PrintUtils.bytesAsString(hashedPassword));

		writer.close();
	}
}
