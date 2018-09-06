/*
 * Copyright (c) 2018 Dharmik Panchal(Github userid:DharmikOO7)
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 */
package dMAlgo;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Apriori {
	
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		
		System.out.println("Enter no of transcations: ");
		int noOfTranscations=Integer.parseInt(sc.nextLine());
		ArrayList<ArrayList<Integer>> transactionsDB=new ArrayList<>();
		
		System.out.println("Enter min support: ");
		int minSupp=Integer.parseInt(sc.nextLine());
		
		System.out.println("Enter items for transcations(space separated for same transcation and new line for new transcation)");
		
		for(int i=0;i<noOfTranscations;i++) {
			ArrayList<Integer> transaction = new ArrayList<>();
			String[] transcationItems=sc.nextLine().split(" ");
			for (int j = 0; j < transcationItems.length; j++) {
				transaction.add(Integer.parseInt(transcationItems[j]));
			}
			transactionsDB.add(transaction);
		}
		sc.close();
		
		Set<Integer> distinctItems=new LinkedHashSet<>();
		for (ArrayList<Integer> transaction: transactionsDB) {
			distinctItems.addAll(transaction);
		}
		
		LinkedHashMap<ArrayList<Integer>, Integer> singleFreqSet=new LinkedHashMap<>();
		for (Integer itemInt: distinctItems) {
			int freq=0;
			for (Iterator<ArrayList<Integer>> iterator1 = transactionsDB.iterator(); iterator1.hasNext();) {
				ArrayList<Integer> arrayList = iterator1.next();
				if(arrayList.contains(itemInt))
					freq++;
			}
			if(freq>=minSupp) {
				singleFreqSet.put(new ArrayList<Integer>() {{add(itemInt);}}, freq);
			}
		}
		
		boolean areMoreItemSetsGeneratable=true;
		List<LinkedHashMap<ArrayList<Integer>, Integer>> allSets=new ArrayList<>();
		allSets.add(singleFreqSet);
		int newSetSize=2;
		while (areMoreItemSetsGeneratable) {
			areMoreItemSetsGeneratable=genSet(allSets,allSets.get(allSets.size()-1),transactionsDB,newSetSize,minSupp);
			newSetSize++;
		}
		
		//print all candidate sets
		Iterator<LinkedHashMap<ArrayList<Integer>, Integer>> iterator = allSets.iterator();
		iterator.next();
		for(int size=2;iterator.hasNext();size++) {
			LinkedHashMap<ArrayList<Integer>, Integer> linkedHashMap = iterator.next();
			System.out.println("Candidate sets of size "+size+" :"+linkedHashMap.keySet());
		}
	}

	private static boolean genSet(List<LinkedHashMap<ArrayList<Integer>, Integer>> allItemAndSupport,
			LinkedHashMap<ArrayList<Integer>, Integer> prevFreqItemAndSupport,
			ArrayList<ArrayList<Integer>> transactionsDB,
			int newItemSetSize, int minSupp) {
		
		List<ArrayList<Integer>> prevFreqItemSets=new ArrayList<>(new LinkedHashSet<>(prevFreqItemAndSupport.keySet()));
		
		LinkedHashSet<Set<Integer>> newCandidateSets=new LinkedHashSet<>();
		
		for (int i=0;i<prevFreqItemSets.size()-1;i++) {
			ArrayList<Integer> arrayList = prevFreqItemSets.get(i);
			Set<Integer> temp=new LinkedHashSet<>();
			temp.addAll(arrayList);
			for(int j=i+1;j<prevFreqItemSets.size();j++){
				ArrayList<java.lang.Integer> arrayList2 = prevFreqItemSets.get(j);
				Set<Integer> temp2=new LinkedHashSet<>(temp);
				temp2.addAll(arrayList2);
				if(temp2.size()==newItemSetSize)
					newCandidateSets.add(temp2);
			}
		}
		LinkedHashMap<ArrayList<Integer>, Integer> newItemAndSupport=new LinkedHashMap<>();
		for (Set<Integer> candidateSet: newCandidateSets) {
			int freq=0;
			for (ArrayList<Integer> currTransaction: transactionsDB) {
				if(currTransaction.containsAll(candidateSet))
					freq++;
			}
			if(freq>=minSupp) {
				ArrayList<Integer> freqItemSet = new ArrayList<>(candidateSet);
				newItemAndSupport.put(freqItemSet, freq);
			}
		}
		
		if(newItemAndSupport.isEmpty()) {
			return false;
		}
		else {
			allItemAndSupport.add(newItemAndSupport);
			return true;
		}
	}
}
