import java.io.PrintWriter;

/**
 * @author Conor Smyth <conor.smyth39@mail.dcu.ie>
 * @since 2015-10-21
 * All work is my own
 * Make printing to a file cleaner in main class
 */
class PrintWriterFacade {
	private PrintWriter writer;

	/**
	 * Default constructor for printwriter
	 * @param file Name of the file as a String
	 */
	public PrintWriterFacade(String file) {
		try {
			writer = new PrintWriter(file);
		} catch(Exception e) {
			System.out.println("Error opening writer");
			e.printStackTrace();
		}
	}

	/**
	 * Write the message supplied to file
	 * @param message String to write to file
	 */
	public void write(String message) {
		writer.print(message);
	}

	/**
	 * Write the message supplied to file on new line
	 * @param message String to write to file
	 */
	public void writeLine(String message) {
		writer.println(message);
	}

	/**
	 * Close printwriter
	 */
	public void close() {
		writer.close();
	}
}
