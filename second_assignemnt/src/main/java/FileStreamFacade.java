import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Make reading/writing bytes from/to a file easier
 * Handles all Try/Catch scenarios
 * All work is my own
 * @author Conor Smyth <conor.smyth39@mail.dcu.ie>
 * @since 2015-11-08
 */
class FileStreamFacade {
	File inputFile;
	File outputFile;

	FileInputStream input;
	FileOutputStream output;

	/**
	 * All-args constructor
	 * @param inputFileName name of the file to read from
	 * @param outputFileName name of the file to print out to
	 */
	public FileStreamFacade(String inputFileName, String outputFileName) {
		if(inputFileName != null) {
			this.inputFile = new File(inputFileName);

			try {
				this.input = new FileInputStream(this.inputFile);
			} catch(FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		if(outputFileName != null) {
			this.outputFile = new File(outputFileName);
			try {
				this.output = new FileOutputStream(this.outputFile);
			} catch(FileNotFoundException e) {
				e.printStackTrace();
			}
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
		try {
			output.write(dataToWrite);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void writeHexFile(byte[] dataToWrite) {
		PrintWriterFacade writer = new PrintWriterFacade(outputFile.getName());

		writer.write(PrintUtils.bytesAsString(dataToWrite));

		writer.close();
	}

	public void close() {
		if(this.input != null) {
			try {
				this.input.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}

		if(this.output != null) {
			try {
				this.output.close(); 
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
}
