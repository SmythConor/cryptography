/**
 * @author Conor Smyth <conor.smyth39@mail.dcu.ie>
 * @since 2015-10-19
 * All work is my own
 */
class Password {
	private String pass;
	private byte[] salt;

	/**
	 * No Args constructor
	 */
	public Password() {
		this.pass = "ahph3chei5Ju";
	}

	/**
	 * One args constructor
	 * @param pass password as a string
	 */
	public Password(String pass) {
		this.pass = pass;
	}

	/**
	 * All args constructor
	 * @param pass password as a string
	 * @param salt salt for password as a BigInteger
	 */
	public Password(String pass, byte[] salt) {
		this.pass = pass;
		this.salt = salt;
	}

	/**
	 * Set password
	 * @param pass password to set as a string
	 */
	public void setPassword(String pass) {
		this.pass = pass;
	}

	/**
	 * Return password
	 * @return password as a string
	 */
	public String getPassword() {
		return this.pass;
	}

	/**
	 * Set salt for password
	 * @param salt salt for password as a BigInteger
	 */
	public void setSalt(byte[] salt) {
		this.salt = salt;
	}

	/**
	 * Return salt
	 * @return salt as a BigInteger
	 */
	public byte[] getSalt() {
		return this.salt;
	}

	/**
	 * Get the password concat with the salt for the password
	 * @return String password concatenated with the salt
	 */
	public String getSaltPassword() {
		String password = pass + new String(salt);

		return password;
	}
}
