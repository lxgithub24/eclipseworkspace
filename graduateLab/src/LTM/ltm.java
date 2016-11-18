package LTM;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ltm {
	private Map<String, LinkedList<String>> fansToUserMap = null;
	private Map<String, Double> thresholdToUserMap = null;
	private Set<String> nodeSet = null;
	private Map<String, Integer> infNumToUserMap = null;
	private Map<String, Double> infValueToUserMap = null;
	private Map<String, Double> infValueToUserInitialMap = null;
	private String[] TKNODE = null;
	
	/*
	 * 初始数据集格式为<粉丝用户，关注用户>
	 * 
	 */
	
	
	//构造器初始化map，set对象
	public ltm() {
		fansToUserMap = new HashMap<String, LinkedList<String>>();
		thresholdToUserMap = new HashMap<String, Double>();
		nodeSet = new HashSet<String>();
		infNumToUserMap = new HashMap<String, Integer>();
		infValueToUserMap = new HashMap<String, Double>();
		infValueToUserInitialMap = new HashMap<String, Double>();
	}
	
	
	
	//初始化各个map，set对象
	public void initialMap(String addr) {// 初始化节点及其粉丝集合map和节点及其阈值map
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(addr));
			String in = null;
			String[] ins = null;
			while ((in = br.readLine()) != null) {
				ins = in.split("\t");
				nodeSet.add(ins[0]);
				if (!ins[1].equals("null"))
					nodeSet.add(ins[1]);
				if (!thresholdToUserMap.containsKey(ins[0])) {// 若激活阈值节点集合中无ins0该节点，则加入
					double threshold = Math.random();
					thresholdToUserMap.put(ins[0], threshold);
					infNumToUserMap.put(ins[0], 0);
					infValueToUserInitialMap.put(ins[0], 0.0);
				}
				if (!fansToUserMap.containsKey(ins[0])) {// 若粉丝集合的节点集合中无ins0，则直接加入
					LinkedList<String> ll = new LinkedList<String>();
					fansToUserMap.put(ins[0], ll);
				}
				if (!ins[1].equals("null")) {
					if (!thresholdToUserMap.containsKey(ins[1])) {// 若激活阈值节点集合中无ins1该节点，则加入
						double threshold = Math.random();
						thresholdToUserMap.put(ins[1], threshold);
						infNumToUserMap.put(ins[1], 0);
						infValueToUserInitialMap.put(ins[1], 0.0);
					}
					if (!fansToUserMap.containsKey(ins[1])) {// 若粉丝集合的节点集合中无ins0，则直接加入
						LinkedList<String> ll = new LinkedList<String>();
						ll.add(ins[0]);
						fansToUserMap.put(ins[1], ll);
					} else {
						if (!fansToUserMap.get(ins[1]).contains(ins[0])) {
							fansToUserMap.get(ins[1]).add(ins[0]);
						}
					}
				}
			}
			
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	
	//获取所有的节点能够影响的节点数目
	public void getAllInfNum() {
		for (String firstNodeString : nodeSet) {
			Collection<String> activatedSet = new HashSet<String>();
			activatedSet.add(firstNodeString);
			Collection<String> unActivatedSet = new HashSet<String>(nodeSet);
			unActivatedSet.remove(firstNodeString);
			infValueToUserMap = new HashMap<String, Double>(infValueToUserInitialMap);
			infValueToUserMap.put(firstNodeString, 1.0);
			// 查看unac集合中的节点能否被ac集合中的节点激活，若可以，加入激活节点集合。如果可以将节点转移如激活节点集合，直至最终unac中的节点不可以被激活。
			getOneInfNum(activatedSet, unActivatedSet);
			infNumToUserMap.put(firstNodeString, activatedSet.size());
		}
	}

	
	//获取一个节点能够影响的节点数目
	public void getOneInfNum(Collection<String> activatedSet,
			Collection<String> unActivatedSet) {
		boolean newActivated = false;
		do {
			for (String unactivatedNode : unActivatedSet) {
				// if()//如果该节点可以被激活，转移节点，break，newactivated置true,赋值节点影响力值
				if (canActive(unActivatedSet, unactivatedNode)) {
					activatedSet.add(unactivatedNode);
					unActivatedSet.remove(unactivatedNode);
//					infValueToUserMap.put(unactivatedNode, /value)
					newActivated = true;
					break;
				}
			}
		} while (newActivated);
	}

	
	
	//判断某时刻某一未被激活节点是否能够被所有激活节点中的节点激活
	public boolean canActive(Collection<String> activatedSet,
			String unactivatedNode) {
		double sumOfInf = 0;
		for (String activatedNode : activatedSet) {
			if (fansToUserMap.get(activatedNode).contains(unactivatedNode)) {
				sumOfInf += (infValueToUserMap.get(activatedNode) / fansToUserMap.get(activatedNode).size());
			}
		}
		if (sumOfInf > thresholdToUserMap.get(unactivatedNode)){
			infValueToUserMap.put(unactivatedNode, sumOfInf);
			return true;
		}
		else
			return false;
	}
	
	
	
	//为最后得到的用户影响数目排名
	public <K, V extends Comparable<? super V>> Map<K, V> sortByValue(
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

	
	
	
	public String[] findTopK(int k){
		String[] topk = new String[k];
		String firstNode = null;
		for(String key : infNumToUserMap.keySet()){
			firstNode = key;
			break;
		}
		Collection<String> activatedSet = new HashSet<String>();
		activatedSet.add(firstNode);
		Collection<String> unActivatedSet = new HashSet<String>(nodeSet);
		unActivatedSet.remove(firstNode);
		while(activatedSet.size()<k){
			String topNode = null;
			for(String unactivatedNode : unActivatedSet){
				int difference = 0;
				Collection<String> activatedSettmp = new HashSet<String>(activatedSet);
				Collection<String> unActivatedSettmp = new HashSet<String>(unActivatedSet);
				activatedSettmp.add(unactivatedNode);
				unActivatedSettmp.remove(unactivatedNode);
				//求当前activatedset能够激活的节点数目,若该值大于difference，则赋值difference，赋值topnode;否则不执行。
				getOneInfNum(activatedSettmp, unActivatedSettmp);
				if(activatedSettmp.size()>difference){
					difference = activatedSettmp.size();
					topNode = unactivatedNode;
				}
			}
			activatedSet.add(topNode);
			unActivatedSet.remove(topNode);
		}
		int i = 0;
		for(String topknode : activatedSet){
			topk[i++] = topknode;
		}
		return topk;
	}
	
	
//	public void getSetInfNum(Collection<String> activatedSet,
//			Collection<String> unActivatedSet){
//		for(String unactivatedNode : unActivatedSet){
//			if(canActive(activatedSet,unactivatedNode)){
//				
//			}
//		}
//	}
//	

	
	

	//排名结果保存在本地文件
	public void print(String lTMRankAddr){
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(lTMRankAddr));
			for(String key : infNumToUserMap.keySet()){
				bw.write(key+"\t"+infNumToUserMap.get(key));
				bw.newLine();
				bw.flush();
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void printTOPK(String topkAddr,int k){
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(topkAddr));
			for(String topknode:findTopK(k)){
				bw.write(topknode);
				bw.newLine();
				bw.flush();
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	//入口函数
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		String addr = "/home/liuxianga/ltmtest/in";
		String lTMRankAddr = "/home/liuxianga/ltmtest/rank";
		String topkAddr = "/home/liuxianga/ltmtest/topk";
		int k = 3;
		
		ltm ltm = new ltm();
		
		ltm.initialMap(addr);
		ltm.getAllInfNum();
		ltm.sortByValue(ltm.infNumToUserMap);
		ltm.findTopK(k);
		ltm.print(lTMRankAddr);
		ltm.printTOPK(topkAddr,k);
		long endTime = System.currentTimeMillis();
		long timeSpend = endTime-startTime;
		System.out.println(timeSpend/1000);
	}

}
