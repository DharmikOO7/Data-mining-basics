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
import java.util.List;
import java.util.Scanner;
import java.util.function.UnaryOperator;

public class Binning {
    enum Choices{MEANS, MEDIANS, BOUNDARIES}
	public static void main(String[] args) {
		Scanner sc =new Scanner(System.in);
		System.out.print("Enter no. of values: ");
		int n=sc.nextInt();
        System.out.print("Enter the data values: ");
        List<Integer> data = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
			data.add(sc.nextInt());
		}
		Collections.sort(data);
		System.out.print("Enter no of bins: ");
		int noOfBins=sc.nextInt();
		List<ArrayList<Integer>> bins;
		System.out.println("Select partition method: ");
		System.out.println("1. Equal-width");
		System.out.println("2. Equal-frequency");
		if(sc.nextByte()==1) {
			bins=equalWidth(data,noOfBins);
		}
		else {
			bins=equalFrequency(data,noOfBins);
		}
		//print partitioned bins
		for (int i=0; i<noOfBins;i++) {
			System.out.println("Bin "+(i+1)+": "+bins.get(i));
		}
		//smoothing part
		System.out.println("Select smoothing methods: ");
		System.out.println("1. Smoothing by bin means");
		System.out.println("2. Smoothing by bin medians");
		System.out.println("3. Smoothing by bin boundaries");
        Choices choice =Choices.values()[ sc.nextByte()];
		sc.close();
        switch (choice){
            case MEANS: smoothingBinMean(bins);
                        break;
            case MEDIANS: smoothingBinMedian(bins);
                          break;
            case BOUNDARIES: smoothingBinBoundaries(bins);
                             break;
            default: System.out.println("defaulting to smoothing by means");
                     smoothingBinMean(bins);
                     break;
        }
		//print bins after smoothing
		System.out.println("After smoothing");
		for (int i=0; i<noOfBins;i++) {
			System.out.println("Bin "+(i+1)+": "+bins.get(i));
		}

	}

	private static List<ArrayList<Integer>> equalFrequency(List<Integer> dataValues,int noOfBins) {
        List<ArrayList<Integer>> bins=new ArrayList<>();
		int binsize=(int)Math.ceil((double)dataValues.size()/noOfBins);
		for(int i=0;i<noOfBins;i++) {
			ArrayList<Integer> bin=new ArrayList<>();
			for (int j = 0; j < binsize; j++) {
				if(dataValues.isEmpty())
					break;
				bin.add(dataValues.remove(0));
			}
			bins.add(bin);
		}
		return bins;
	}

	private static List<ArrayList<Integer>> equalWidth(List<Integer> data, int noOfBins) {
        List<ArrayList<Integer>> bins=new ArrayList<>();
		int binRange;
		{
			int maxA=data.get(data.size()-1);
			int minA=data.get(0);
			binRange=(int) Math.ceil( (double)( maxA - minA ) / noOfBins);
		}
		int binMaxBoundary=data.get(0)+binRange-1;

		for (int i = 0; i < noOfBins; i++) {

			ArrayList<Integer> bin=new ArrayList<>();
			while(!data.isEmpty() && data.get(0)<=binMaxBoundary) {
				bin.add(data.remove(0));
			}
			if(!bin.isEmpty())
				bins.add(bin);

			binMaxBoundary+=binRange;
		}
        //add leftover elements to the last bin
		while(!data.isEmpty()) {
			bins.get(bins.size()-1).add(data.remove(0));
		}
		return bins;
	}

    private static void smoothingBinMean(List<ArrayList<Integer>> bins) {
        bins.forEach(bin -> {
            float mean = binMean(bin);
            UnaryOperator<Integer> operator = i -> (int) mean;
            bin.replaceAll(operator);
        });
    }

    private static void smoothingBinMedian(List<ArrayList<Integer>> bins) {
        for (ArrayList<Integer> bin : bins) {
            int middle = bin.size() / 2;
            int median;
            if (bin.size() % 2 == 1)
                median = bin.get(middle);
            else
                median = (bin.get(middle - 1) + bin.get(middle)) / 2;
            UnaryOperator<Integer> operator = i -> median;
            bin.replaceAll(operator);
        }
    }

    private static void smoothingBinBoundaries(List<ArrayList<Integer>> bins) {
        for (ArrayList<Integer> bin : bins) {
            int max = bin.get(bin.size() - 1);
            int min = bin.get(0);
            //operator to replace bin value with nearest boundary
            UnaryOperator<Integer> operator = j -> (Math.abs(j - min) < Math.abs(j - max)) ? min : max;
            bin.replaceAll(operator);
        }
    }

    private static float binMean(ArrayList<Integer> bin) {
	    //calculates sum of all elements in the bin
        int sum = bin.stream().mapToInt(integer -> integer).sum();
        //return bin mean
        return (float)sum / bin.size();
    }
}
