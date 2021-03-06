package CosinSim;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class SortFileByKey {

	public static void main(String[] args) {
		File file1 = new File("/home/liuxianga/test/in");
		File file2 = new File("/home/liuxianga/test/out");
//		File file1 = new File("/home/SNA/Lx/gralab/pagerank/"+args[0]);
//		File file2 = new File("/home/SNA/Lx/gralab/pagerank/"+args[1]);
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			String in = null;
			Map<String, String> map = new HashMap<String, String>();
			String[] ins = null;
			br = new BufferedReader(new FileReader(file1));
			bw = new BufferedWriter(new FileWriter(file2));
			while((in = br.readLine())!=null){
				ins = in.split("	");
				map.put(ins[0], ins[1]);
			}
			map = sortByKey(map);
			for(String key : map.keySet()){
				bw.write(key+"	"+map.get(key));
				bw.newLine();
				bw.flush();
			}
			br.close();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByKey(
			Map<K, V> map) {
		List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(
				map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
			public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
				return ((String) o1.getKey()).compareTo((String) o2.getKey());
			}
		});

		Map<K, V> result = new LinkedHashMap<K, V>();
		for (Map.Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}
}
