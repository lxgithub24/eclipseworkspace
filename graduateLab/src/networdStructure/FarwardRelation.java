package networdStructure;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FarwardRelation {

	public static void main(String[] args) {
		File file1 = new File("/home/SNA/Lx/gralab/rewrite_million");
		File file2 = new File("/home/SNA/Lx/gralab/repost");
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			String in = null;
			String[] ins;
			br = new BufferedReader(new FileReader(file1));
			bw = new BufferedWriter(new FileWriter(file2));
			while ((in = br.readLine()) != null) {
				ins = in.split("	");
				bw.write(ins[2]+"	"+ins[8]);
				bw.newLine();
				bw.flush();
			}
			br.close();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}