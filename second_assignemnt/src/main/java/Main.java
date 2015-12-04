import java.math.BigInteger;

/**
 * Main class to run all code for the assignment
 * All work is my own
 * @author Conor Smyth <conor.smyth39@mail.dcu.ie>
 * @since 2015-11-16
 */
class Main {
	private static final String JAVA_DIR = "../src/main/java/";

	private static final String I_FILE = "src.zip";
	private static final String SUBMISSION_FILE = "submission_data";

	public static void main(String[] args) {
		/* Initialise input and output streams */
		FileStreamFacade io = new FileStreamFacade(I_FILE, null);

		/* Read in the data */
		byte[] dataToHash = io.readFile();

		/* Close IO */
		io.close();

		/* File to hash */
		byte[] hashedData = Hasher.hash(dataToHash);

		/* Get the p/generator etc */
		BigInteger p = KeyGenerator.getModulus();
		BigInteger g = KeyGenerator.getGenerator();
		BigInteger p1 = p.subtract(BigInteger.ONE);

		/* Generate public and private keys */
		BigInteger privateKey = KeyGenerator.generatePrivateKey(p);
		BigInteger publicKey = KeyGenerator.generatePublicKey(privateKey);

		/* Declare these cause Java */
		BigInteger k = null;
		BigInteger r = null;
		BigInteger s = null;

		do {
			/* Get value k coprime with p - 1 */
			k = Arithmetic.getCoPrimeValue(p, p1);

			/* Get Digital signiture R */
			r = Arithmetic.getDigitalSignitureR(g, k, p);

			/* Get Digital signiture S */
			s = Arithmetic.getDigitalSignitureS(hashedData, privateKey, r, k, p1);
		} while(s.equals(BigInteger.ZERO));

		/* Write the submission data */
		writeSubmissionData(publicKey, r, s);
	}

	/**
	 * Write the submission information to a file
	 * @param publicKey the public key for the assignment
	 * @param r the digital signiture R
	 * @param s the digital signiture S
	 */
	private static void writeSubmissionData(BigInteger publicKey, BigInteger r, BigInteger s) {
		PrintWriterFacade writer = new PrintWriterFacade(SUBMISSION_FILE);

		String data = PrintUtils.formatData("Public Key", publicKey);
		writer.writeLine(data);

		data = PrintUtils.formatData("r", r);
		writer.writeLine(data);

		data = PrintUtils.formatData("s", s);
		writer.writeLine(data);

		writer.close();
	}
}
