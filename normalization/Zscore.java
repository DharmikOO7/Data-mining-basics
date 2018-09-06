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
package normalization;

import java.util.Scanner;

public class Zscore {

	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		double stdDeviation;
		double mean;
		System.out.println("Enter value to normalize: ");
		int v=sc.nextInt();
		
		System.out.println("Do you want to enter direct values of mean and std deviation(Y/N): ");
		String choice=sc.next().toUpperCase();
		
		if(choice.charAt(0)=='Y') {
			System.out.println("Enter mean: ");
			mean=sc.nextDouble();
			System.out.println("Enter standard deviation: ");
			stdDeviation=sc.nextDouble();
		}
		else {
			System.out.println("Enter number of values(=n) of attribute A: ");
			int n=sc.nextInt();
			int[] A=new int[n];
			System.out.println("Enter the values of attribute A:");
			for (int i = 0; i < n; i++) {
				A[i]=sc.nextInt();
			}
			int sum=0;
			for (int i = 0; i < n; i++) {
				sum+=A[i];
			}
			mean=(double)sum/n;
			float sumOfDeviations=0;
			for (int i = 0; i < n; i++) {
				sumOfDeviations+=((A[i]-mean)*(A[i]-mean));
			}
			stdDeviation=Math.sqrt(sumOfDeviations/n);
		}
		
		double normalizedV=(v-mean)/stdDeviation;
		System.out.println("Normalized value is:"+ normalizedV);
		sc.close();
	}

}
