package ons31aug;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Helpers {
    public int yearOfBirth(String birthDate){
        return Integer.parseInt(birthDate.split("-")[0]);
    }

    public int monthOfBirth(String birthDate){
        return Integer.parseInt(birthDate.split("-")[1]);
    }

    public int dateOfBirth(String birthDate){
        return Integer.parseInt(birthDate.split("-")[2]);
    }

    public static int yearNow(){
        return now("yyyy");
    }

    public static int monthNow(){
        return now("MM");
    }

    public static int dateNow(){
        return now("dd");
    }

    public static int now(String pattern){
        return Integer.parseInt(new SimpleDateFormat(pattern).format(new Date()));
    }
}
