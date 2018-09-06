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
package binning;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.function.UnaryOperator;

public class Binning {
	public static void main(String[] args) {
		Scanner sc =new Scanner(System.in);
		System.out.print("Enter no. of values: ");
		int n=sc.nextInt();
		List<Integer> a=new ArrayList<>(n);
		System.out.print("Enter the data values: ");
		for (int i = 0; i < n; i++) {
			a.add(sc.nextInt());
		}
		Collections.sort(a);
		System.out.print("Enter no of bins: ");
		int noOfBins=sc.nextInt();
		List<ArrayList<Integer>> bins=new ArrayList<>();
		System.out.println("Select partition method: ");
		System.out.println("1. Equal-width");
		System.out.println("2. Equal-frequency");
		byte choice =sc.nextByte();
		if(choice == 1) {
			//equal width
			bins=equalWidth(bins,a,noOfBins);

		}//end if
		else {
			//equal frequency
			bins=equalFrequency(bins,a,noOfBins);
		}// end else

		//print bins
		for (int i=0; i<noOfBins;i++) {
			System.out.println("Bin "+(i+1)+": "+bins.get(i));
		}
		//smoothing part
		System.out.println("Select smoothing methods: ");
		System.out.println("1. Smoothing by bin means");
		System.out.println("2. Smoothing by bin medians");
		System.out.println("3. Smoothing by bin boundaries");
		choice=sc.nextByte();
		sc.close();
		if (choice==1) {
			//bin means
			bins=smoothingBinMean(bins);
		} else if (choice==2) {
			//bin medians
			bins=smoothingBinMedian(bins);
		}else {
			//bin boundaries
			bins=smoothingBinBoundaries(bins);
		}
		//print bins after smoothing
		System.out.println("After smoothing");
		for (int i=0; i<noOfBins;i++) {
			System.out.println("Bin "+(i+1)+": "+bins.get(i));
		}

	}

	private static List<ArrayList<Integer>> smoothingBinMean(List<ArrayList<Integer>> bins) {
		for (Iterator<ArrayList<Integer>> iterator = bins.iterator(); iterator.hasNext();) {
			ArrayList<Integer> arrayList = iterator.next();
			float mean=binMean(arrayList);
			UnaryOperator<Integer> operator=i->i-i+(int)mean;
			arrayList.replaceAll(operator);
		}
		return bins;
	}
	
	private static List<ArrayList<Integer>> smoothingBinMedian(List<ArrayList<Integer>> bins) {
		for (Iterator<ArrayList<Integer>> iterator = bins.iterator(); iterator.hasNext();) {
			ArrayList<Integer> arrayList = iterator.next();
			int middle=arrayList.size()/2;
			int median;
			if(arrayList.size()%2==1)
				median=arrayList.get(middle);
			else
				median=(arrayList.get(middle-1)+arrayList.get(middle))/2;
			UnaryOperator<Integer> operator=i->i-i+median;
			arrayList.replaceAll(operator);
		}
		return bins;
	}

	private static List<ArrayList<Integer>> smoothingBinBoundaries(List<ArrayList<Integer>> bins) {
		for (Iterator<ArrayList<Integer>> iterator = bins.iterator(); iterator.hasNext();) {
			ArrayList<Integer> arrayList = (ArrayList<Integer>) iterator.next();
			int max=arrayList.get(arrayList.size()-1);
			int min=arrayList.get(0);
			for(int i=0;i<arrayList.size();i++) {
				UnaryOperator<Integer> operator=j->j-j+(Math.abs(j-min)<Math.abs(j-max)?(min):(max));
				arrayList.replaceAll(operator);
			}
		}
		return bins;
	}

	private static float binMean(ArrayList<Integer> arrayList) {
		int sum=0;
		for (Integer integer : arrayList) {
			sum+=integer;
		}
		return (float)sum/arrayList.size();
	}

	private static List<ArrayList<Integer>> equalFrequency(List<ArrayList<Integer>> bins, List<Integer> a,int noOfBins) {
		int binsize=(int)Math.ceil((double)a.size()/noOfBins);
		for(int i=0;i<noOfBins;i++) {
			ArrayList<Integer> bin=new ArrayList<>();
			for (int j = 0; j < binsize; j++) {
				if(a.isEmpty())
					break;
				bin.add(a.remove(0));
			}
			bins.add(bin);
		}
		return bins;
	}

	private static List<ArrayList<Integer>> equalWidth(List<ArrayList<Integer>> bins, List<Integer> a, int noOfBins) {
		int binRange;
		{
			int maxA=a.get(a.size()-1);
			int minA=a.get(0);
			binRange=(int) Math.ceil( (double)( maxA - minA ) / noOfBins);
		}
		int binMaxBoundary=a.get(0)+binRange-1;

		for (int i = 0; i < noOfBins; i++) {

			ArrayList<Integer> bin=new ArrayList<>();
			while(!a.isEmpty() && a.get(0)<=binMaxBoundary) {
				bin.add(a.remove(0));
			}
			if(!bin.isEmpty())
				bins.add(bin);
			binMaxBoundary+=binRange;
		}
		while(!a.isEmpty()) {
			//add leftover elements to the last bin
			bins.get(bins.size()-1).add(a.remove(0));
		}
		return bins;
	}
}
