/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package mån29aug;

import java.util.Scanner;
import java.util.stream.Stream;

public class Solutions {
	public static String fizzBuzz() {
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= 100; i++) {
			if (i % 15 == 0) {
				sb.append("FizzBuzz");
			} else if (i % 3 == 0) {
				sb.append("Fizz");
			} else if (i % 5 == 0) {
				sb.append("Buzz");
			} else {
				sb.append(i);
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	public static int realToFake(int real) {
  		int thirteenSkips = (real + 77) / 89;
  		int fourSkips = (real + 5 + thirteenSkips) / 9;
  		return real + thirteenSkips + fourSkips;
	}

	public static int fakeToReal(int fake) throws IllegalArgumentException {
		if (fake % 10 == 4 || fake % 100 == 13) {
			throw new IllegalArgumentException("Fake number can't be unlucky");
		} else {
			int thirteenSkips = (fake + 85) / 100;
			int fourSkips = (fake + 5) / 10;
			return fake - (thirteenSkips + fourSkips);
		}
	}

	// 1	2	3	4	5	6	7    squares
	// 1	2	4	8	16	32	64	 only at square
	// 1	3	7	15  31  63	127  total at square
	public static int riceGrainBoard(long riceGrains) {
		return (int) Math.ceil(Math.log(riceGrains + 1) / Math.log(2));
	}	
}
