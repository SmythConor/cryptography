import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class BufferedReaderFacade {
	BufferedReader reader;

	public BufferedReaderFacade(String file) {
		try {
			reader = new BufferedReader(new FileReader(file));
		} catch(Exception e) {
			System.out.println("Error initialising BufferedReader");
			e.printStackTrace();
		}
	}

	public String readLine() {
		try {
			return reader.readLine();
		} catch(IOException e) {
			System.out.println("Error reading from file");
			e.printStackTrace();

			return null;
		}
	}

	public void close() {
		try {
			reader.close();
		} catch(IOException e) {
			System.out.println("Error closing reader");
			e.printStackTrace();
		}
	}
}
