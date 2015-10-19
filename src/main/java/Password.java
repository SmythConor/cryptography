import java.math.BigInteger;

class Password {
	private String pass;
	private BigInteger salt;

	public Password() {
		this.pass = "password";
	}

	public Password(String pass) {
		this.pass = pass;
	}

	public void setSalt(BigInteger salt) {
		this.salt = salt;
	}

	public void getPassword() {
		String password = pass + salt.toString();
		byte[] bytePassword = new byte[0];

		try {
			bytePassword = p.getBytes("UTF-8");
		} catch(Exception e) {

		}
		for(byte b : bytePassword) {
			System.out.print(b);
		}

		System.out.println();
	}
}
