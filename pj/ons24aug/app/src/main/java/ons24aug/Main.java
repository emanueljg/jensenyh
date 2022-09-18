package ons24aug;

import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Supplier;

import ons24aug.Solutions;

public class Main {

    private static void printer(Function<String, Object> solution) {
    Scanner in = new Scanner(System.in);
    String str = in.nextLine();
    in.close();
    System.out.println(solution.apply(str)); 
    }

    private static void printer(Supplier<Object> solution) {
    System.out.println(solution.get());
    }
    
    public static void main(String[] args) {
    printer(Solutions::getTheClockWithSeconds);
    }
}


