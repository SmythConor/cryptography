import java.math.BigInteger;

class Test {
	public static void main(String[] args) {
		int bits = 128;
		BigInteger salt = SaltGenerator.generateSalt(bits);
		Password p = new Password();
		p.setSalt(salt);
		p.getPassword();
	}
}
