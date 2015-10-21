import java.math.BigInteger;
import static java.nio.charset.StandardCharsets.UTF_8;

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

	/**
	 * Get the password concat with the salt for the password
	 * @return String password concatenated with the salt
	 */
	public String getSaltPassword() {
		String password = pass + salt.toString();

		return password;
	}
}
