import java.io.File;
import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author Conor Smyth <conor.smyth39@mail.dcu.ie>
 * @since 2015-10-23
 * All work is my own
 * Make reading/writing bytes from/to a file easier
 */
class FileStreamFacade {
	File inputFile;

	FileInputStream input;
	PrintWriterFacade writer;

	public FileStreamFacade(String inputFileName, String outputFileName) {
		this.inputFile = new File(inputFileName);
		this.writer = new PrintWriterFacade(outputFileName);

		try {
			this.input = new FileInputStream(this.inputFile);
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public byte[] readFile() {
		byte[] file = new byte[(int) this.inputFile.length()];

		try {
			input.read(file);
		} catch(IOException e) {
			e.printStackTrace();
		}

		return file;
	}

	public void writeFile(byte[] dataToWrite) {
		writer.writeLine(PrintUtils.bytesAsString(dataToWrite));
	}

	public void close() {
		try {
			this.input.close();
		} catch(IOException e) {
			e.printStackTrace();
		}

		this.writer.close();
	}
}
