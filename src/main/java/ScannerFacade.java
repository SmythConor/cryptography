import java.util.Scanner;
import java.io.File;
import java.io.IOException;

class ScannerFacade {
	Scanner scanner;

	public ScannerFacade(String file) {
		try {
			this.scanner = new Scanner(new File(file));
		} catch(IOException e) {
			System.out.println("Error opening file");
			e.printStackTrace();
		}
	}

	public boolean hasNext() {
		return scanner.hasNext();
	}

	public String next() {
		return scanner.next();
	}

	public void close() {
		this.scanner.close();
	}
}
