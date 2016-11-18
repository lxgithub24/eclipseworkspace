package networdStructure;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class repostratio {

	public static void main(String[] args) {
		File file1 = new File("/home/liuxianga/graduate/nodeset");
		File file2 = new File("/home/liuxianga/graduate/repost");
		File file3 = new File("/home/liuxianga/graduate/repostratio1");
		BufferedReader br1 = null;
		BufferedReader br2 = null;
		BufferedWriter bw = null;
		try {
			Map<String, List<Integer>> map = new HashMap<String, List<Integer>>();
			br1 = new BufferedReader(new FileReader(file1));
			bw = new BufferedWriter(new FileWriter(file3));
			String in = null;
			String[] ins = null;
			while((in = br1.readLine())!=null){
				List<Integer> list = new ArrayList<Integer>();
				list.add(0);
				list.add(0);
				map.put(in, list);
			}
			in=null;
			br2 = new BufferedReader(new FileReader(file2));
			while((in = br2.readLine())!=null){
				ins = in.split("	");
				map.get(ins[0]).set(0, map.get(ins[0]).get(0)+1);
				if(!ins[1].equals("null")){
					map.get(ins[1]).set(1, map.get(ins[1]).get(1)+1);
				}
			}
			Iterator iterator = map.entrySet().iterator();
			Map<String, Double> repostratioMap = new HashMap<String, Double>();
			while(iterator.hasNext()){
				Entry entry = (Entry)(iterator.next());
				String key = (String) entry.getKey();
				List<Integer> list = new ArrayList<Integer>();
				list.add( (int) ((List)(entry).getValue()).get(0));
				list.add( (int) ((List)(entry).getValue()).get(1));
				repostratioMap.put(key, ((double)list.get(1)/(list.get(0)+list.get(1))));
			}
			repostratioMap = sortByValue(repostratioMap);
			iterator = repostratioMap.entrySet().iterator();
			while(iterator.hasNext()){
				Entry entry = (Entry)(iterator.next());
				bw.write(entry.getKey()+"	"+entry.getValue());
				bw.newLine();
				bw.flush();
			}
			br1.close();
			br2.close();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(
			Map<K, V> map) {
		List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(
				map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
			public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
				return (-1)*(o1.getValue()).compareTo(o2.getValue());
			}
		});

		Map<K, V> result = new LinkedHashMap<K, V>();
		for (Map.Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}
}