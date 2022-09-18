package mån29aug;

import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Supplier;

import mån29aug.Solutions;

public class Main {

    private static String getInput() {
        Scanner in = new Scanner(System.in);
        String str = in.nextLine();
        in.close();
        return str;
    }

    private static void printer(Function<Object, Object> solution) {
        return;
        //System.out.println(solution.apply(getInput()));
    }

    private static void printerForString(Function<String, Object> solution) {
        System.out.println(solution.apply((getInput()))); 
    }

    private static void printerForLong(Function<Long, Object> solution) {
        System.out.println(
            solution.apply(
                Long.parseLong(
                    getInput()
                )
            )
        ); 
    }

    private static void printer(Supplier<Object> solution) {
        System.out.println(solution.get());
    }
    
    public static void main(String[] args) {
        //Solutions.getRealsAndFakes();
        //printer(Solutions::getRealsAndFakes);
        //printerForLong(Solutions::squareWithBoardRiceGrains);
    }
}


