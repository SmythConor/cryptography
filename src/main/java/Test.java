import java.math.BigInteger;

class Test {
	public static void main(String[] args) {
		int bits = 128;
		BigInteger salt = KeyGenerator.generateKey(bits);
		Password p = new Password();
		p.setSalt(salt);
		try {
			HashPassword.hashPassword(p.getSaltPassword());
		} catch (Exception e) {}
	}
}
