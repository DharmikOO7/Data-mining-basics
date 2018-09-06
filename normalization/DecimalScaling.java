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

public class DecimalScaling {

	public static void main(String[] args) {
		int minA,maxA;
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter minimum value of attribute A: ");
		minA=sc.nextInt();
		System.out.println("Enter maximum value of attribute A: ");
		maxA=sc.nextInt();
		int j=(int) Math.ceil(Math.log10(Math.max(Math.abs(minA), Math.abs(maxA))));
		System.out.println("The normalized range of attribute A is:"+minA/Math.pow(10, j)+","+maxA/Math.pow(10, j));
		sc.close();
	}

}
