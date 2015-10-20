import java.math.BigInteger;

class Test {
	private static final BITS = 128;

	public static void main(String[] args) {
		BigInteger salt = KeyGenerator.generateKey(BITS);

		Password p = new Password();
		p.setSalt(salt);

		PasswordHasher.hashPassword(p.getSaltPassword());
	}
}
