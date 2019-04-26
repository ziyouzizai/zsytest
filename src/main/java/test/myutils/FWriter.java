package test.myutils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FWriter {
	private File target = null;
	private BufferedWriter bw = null;
	private long lineNum = 0;
	private boolean inited = false;
	private String LS = "\n";
	
	public FWriter(String filePathName) {
		this(new File(filePathName));
		init();
	}
	
	public FWriter(File file) {
		this.target = file;
	}

	public boolean init() {
		if(target == null) 
			return false;
		if(!target.exists()) {
			try {
				target.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		try {
			bw = new BufferedWriter(new FileWriter(target));
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		inited = true;
		return true;
	}
	
	public boolean writeLine(String line) {
		if(!inited)
			return false;
		lineNum ++;
		try {
			bw.write(line);
			bw.write(LS);
			if(lineNum % 100 == 0) 
				bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean close() {
		if(!inited)
			return false;
		try {
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
