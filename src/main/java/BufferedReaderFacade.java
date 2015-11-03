import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Conor Smyth <conor.smyth39@mail.dcu.ie>
 * @since 2015-10-23
 * All work is my own
 * Make Reading from file cleaner from main class
 */
class BufferedReaderFacade {
	BufferedReader reader;

	/**
	 * All Args constructor
	 * @param file name of the file to read from
	 */
	public BufferedReaderFacade(String file) {
		try {
			reader = new BufferedReader(new FileReader(file));
		} catch(Exception e) {
			System.out.println("Error initialising BufferedReader");
			e.printStackTrace();
		}
	}

	/**
	 * Read a line from file
	 */
	public String readLine() {
		try {
			return reader.readLine();
		} catch(IOException e) {
			System.out.println("Error reading from file");
			e.printStackTrace();

			return null;
		}
	}

	/**
	 * Close the buffered reader
	 */
	public void close() {
		try {
			reader.close();
		} catch(IOException e) {
			System.out.println("Error closing reader");
			e.printStackTrace();
		}
	}
}
