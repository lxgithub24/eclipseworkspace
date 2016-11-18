package networdStructure;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class InitUserRelation {

	public static void main(String[] args) {
		File file1 = new File("/home/SNA/Lx/gralab/repost");
		File file2 = new File("/home/SNA/Lx/gralab/initUserRelation");
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			br = new BufferedReader(new FileReader(file1));
			bw = new BufferedWriter(new FileWriter(file2));
			String in = null;
			while((in = br.readLine())!=null){
				if(in.contains("null")){
					String[] ins = in.split("	");
					in = ins[0]+"	"+ins[0];
				}
				double a = Math.random();
				bw.write(in+"	"+a);
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
