package CosinSim;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CosinOfTwoFile {

	public static void main(String[] args) {
		 File file1 = new File("/home/liuxianga/test/out");
		 File file2 = new File("/home/liuxianga/test/out1");
//		File file1 = new File("/home/SNA/Lx/gralab/pagerank/" + args[0]);
//		File file2 = new File("/home/SNA/Lx/gralab/pagerank/" + args[1]);
		BufferedReader br1 = null;
		BufferedReader br2 = null;
		final int lenoffile = 874761;
		try {
			double[] v1 = new double[lenoffile];
			double[] v2 = new double[lenoffile];
			String in = null;
			int i = 0;
			String[] ins = null;
			br1 = new BufferedReader(new FileReader(file1));
			br2 = new BufferedReader(new FileReader(file2));
			while ((in = br1.readLine()) != null) {
				ins = in.split("\t");
				v1[i++] = Double.valueOf(ins[1]);
			}
			i = 0;
			while ((in = br2.readLine()) != null) {
				ins = in.split("\t");
				v2[i++] = Double.valueOf(ins[1]);
			}
			double lenv1 = 0, lenv2 = 0, mulv1v2 = 0, sim = 0;
			for (int j = 0; j < lenoffile; j++) {
				if (v1[j] != 0 && v2[j] != 0) {
					lenv1 += v1[j] * v1[j];
					lenv2 += v2[j] * v2[j];
					mulv1v2 += v1[j] * v2[j];
				}
			}
			sim = mulv1v2 / ((Math.sqrt(lenv1)) * (Math.sqrt(lenv2)));
			System.out.println(sim);
			br1.close();
			br2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
