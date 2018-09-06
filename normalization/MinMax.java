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

public class MinMax {

	public static void main(String[] args) {
		int minA,maxA,vA;
		float newMaxA,newMinA;
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter minimum value of A: ");
		minA=sc.nextInt();
		System.out.println("Enter maximum value of A: ");
		maxA=sc.nextInt();
		System.out.println("Enter new minimum value of A: ");
		newMinA=sc.nextFloat();
		System.out.println("Enter new maximum value of A: ");
		newMaxA=sc.nextFloat();
		System.out.println("Enter value to normalzie: ");
		vA=sc.nextInt();
		float normalizedVA=((float)(vA-minA)/(maxA-minA))*(newMaxA-newMinA)+newMinA;
		System.out.println("Normalized vA:"+normalizedVA);
		sc.close();
	}

}
