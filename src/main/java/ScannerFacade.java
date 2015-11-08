import java.util.Scanner;
import java.io.File;
import java.io.IOException;

/**
 * @author Conor Smyth <conor.smyth39@mail.dcu.ie>
 * @since 2015-10-23
 * All work is my own
 * Make reading from a file easier
 */
class ScannerFacade {
	Scanner scanner;

	/**
	 * All args constructor
	 * @param file Name of the file as a String
	 */
	public ScannerFacade(String file) {
		try {
			this.scanner = new Scanner(new File(file));
		} catch(IOException e) {
			System.out.println("Error opening file");
			e.printStackTrace();
		}
	}

	/**
	 * @see java.util.Scanner#hasNext
	 */
	public boolean hasNext() {
		return scanner.hasNext();
	}

	/**
	 * @see java.util.Scanner#next
	 */
	public String next() {
		return scanner.next();
	}

	/**
	 * Close the scanner
	 */
	public void close() {
		this.scanner.close();
	}
}
