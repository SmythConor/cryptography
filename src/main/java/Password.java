import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

class Password {
	private String pass;
	private BigInteger salt;

	public Password() {
		this.pass = "password";
	}

	public Password(String pass) {
		this.pass = pass;
	}

	public Password(String pass, BigInteger salt) {
		this.pass = pass;
		this.salt = salt;
	}

	public void setPassword(String pass) {
		this.pass = pass;
	}

	public String getPassword() {
		return this.pass;
	}

	public void setSalt(BigInteger salt) {
		this.salt = salt;
	}

	public BigInteger getSalt() {
		return this.salt;
	}

	public String getSaltPassword() {
		String password = pass + salt.toString();

		byte[] bytePassword = new byte[0];

		try {
			bytePassword = password.getBytes(StandardCharsets.UTF_8);
		} catch(Exception e) {
			System.out.println("Error with getSaltPassword");
		}

		return password;
	}
}
