package qy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Format {

	public static void main(String[] args) {
		File file1 = new File("/home/liuxianga/qy/in");
		File file2 = new File("/home/liuxianga/qy/out");
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			String in = null;
			String[] ins;
			br = new BufferedReader(new FileReader(file1));
			bw = new BufferedWriter(new FileWriter(file2));
			while ((in = br.readLine()) != null) {
				ins = in.split("	");
				if(ins.length>=6)
				bw.write('\n'+in);
				else bw.write(in);
			}
			br.close();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}