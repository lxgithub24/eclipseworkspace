package networdStructure;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
public class MapSortByValue {

	public static void main(String[] args) {
		HashMap<String, Double> map = new HashMap<String, Double>();
		map.put("A", 99.5);
		map.put("B", 67.4);
		map.put("C", 67.4);
		map.put("D", 67.3);
		map.put("E",  100.0);
		map = (HashMap<String, Double>) sortByValue(map);
		Iterator it = map.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry me = (Map.Entry)it.next();
			System.out.print(me.getKey()+" ");
			System.out.println(me.getValue());
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
