/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package ons31aug;

import ons31aug.Helpers;

import java.util.Scanner;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.stream.Stream;

public class Solutions {
	private static <T> T promptRepeat(String question,
									  Function<T, String> answerer,
									  Function<String, T> mapper,
									  Function<T, Boolean> pred) {
		Scanner in = new Scanner(System.in);
		T valid = null;
		while (valid == null) {
			System.out.println(question);
			String input = in.nextLine();
			try {
				T current = mapper.apply(input);
				if (pred.apply(current)) {
					valid = current;  
				}
			} catch (IllegalArgumentException ignore) {
				continue;
			}
		}
		System.out.println(answerer.apply(valid));
		return valid;
	}	
	
	public static int inputInt(String question, int min, int max) {
		return Solutions.
				 <Integer>promptRepeat(question,
						 			   n -> "got: " + n,
									   Integer::parseInt,
									   n -> n >= min && n <= max
						 			  );
	}

	protected static void doAgePrompt() {
		String birthDate = Solutions.
						     <String>promptRepeat("Skriv in ditt födelsedatum (YYYY-MM-DD)",
												  str -> "Datum: " + str,
												  str -> str,
												  str -> str.matches(
														   "([1-9]\\d{3}-[01]\\d-[0-3]\\d)"
														 )
									 			 );
		System.out.println("Du är " + age(birthDate) + " år gammal");
	}		
	
	public static int age(String birthDate) {
		Helpers h = new Helpers();
		// non-static?
		int birthY = h.yearOfBirth(birthDate);	
		int birthM = h.monthOfBirth(birthDate);	
		int birthD = h.dateOfBirth(birthDate);	

		int nowY = Helpers.yearNow();
		int nowM = Helpers.monthNow();
		int nowD = Helpers.dateNow();

		// baseAge assumes birthday happened
		int baseAge = nowY - birthY;
		// therefore, the coming code checks if the birthday 
		// actually occured and if not, lowers the age by 1.
		//
		// the birthday check checks 2 cases:
		// case 1: strictly passed the actual month (birthday occured)
		// case 2: same month but passed or match the birth day (birthday occured)
		// case 3: none of these apply (birthday did not occur)
		return (nowM > birthM || (nowM == birthM && nowD >= birthD)) 
				  ? baseAge 
				  : baseAge - 1; 
	}
}