import java.io.PrintWriter;

class PrintWriterFacade {
	private PrintWriter writer;

	public PrintWriterFacade(String file) {
		try {
			writer = new PrintWriter(file);
		} catch(Exception e) {
			System.out.println("Error opening writer");
			e.printStackTrace();
		}
	}

	public void write(String message) {
		writer.print(message);
	}
	public void writeLine(String message) {
		writer.println(message);
	}

	public void close() {
		writer.close();
	}
}
