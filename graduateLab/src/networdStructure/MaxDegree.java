package networdStructure;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class MaxDegree {

	public static void main(String[] args) {
		int numOfRelation = 1000000;
		File file1 = new File("/home/SNA/Lx/gralab/repost");
		File file2 = new File("/home/SNA/Lx/gralab/nodeset");
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			String in = null;
			String[] ins;
			br = new BufferedReader(new FileReader(file1));
			bw = new BufferedWriter(new FileWriter(file2));
			Map<String, Integer> map = new HashMap<String, Integer>();
			while ((in = br.readLine()) != null) {
				ins = in.split("	");
				for (int i = 0; i < 2; i++) {
					if (!ins[i].equals("null")) {
						if (map.containsKey(ins[i])) {
							map.put(ins[i], map.get(ins[i]) + 1);
						}else{
							map.put(ins[i], 1);
						}
					}
				}
			}
			Iterator it = map.entrySet().iterator();
			String[] rank = new String[numOfRelation];
			for(int i = 0 ; i < 20000 ; i++){
				rank[i]="";
			}
			
			while(it.hasNext()){
				Entry me = (Map.Entry)it.next();
				rank[(int) me.getValue()]+=(me.getKey()+",");
			}
//			for(int i = 0 ; i < map.size() ; i++){
//				System.out.println(rank[i]);
//			}
			for(int i = 20000-1 ; i >= 0 ; i--){
				if(rank[i]!=""){
					String[] ranks = rank[i].split(",");
					for(int j = 0 ; j < ranks.length ; j++){
						bw.write(ranks[j]/*+"	"+i*/);
						bw.newLine();
						bw.flush();
					}
				}
			}
			br.close();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
