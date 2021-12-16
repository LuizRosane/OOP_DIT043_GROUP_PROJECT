package Util;


import java.util.Scanner;

public class Input {

    //declaring Scanner as a global variable
    public static Scanner input = new Scanner(System.in);

    public static int readInt(String message) { //based on code example 4 from lecture 5 of perusall

        //reading and storing the user's answer
        System.out.print(message);
        int answer = input.nextInt();
        input.nextLine();

        //adding a '\n' for display reasons
        System.out.println();

        //returning the user's answer
        return answer;
    }

    public static double readDouble(String message) { //based on code example 4 from lecture 5 of perusall

        //reading and storing the user's answer
        System.out.print(message);
        double answer = input.nextDouble();
        input.nextLine();

        //adding a '\n' for display reasons
        System.out.println();

        //returning the user's answer
        return answer;
    }

    public static String readString(String message) { //based on code example 4 from lecture 5 of perusall

        //reading and storing the user's answer
        System.out.print(message);
        String answer = input.nextLine();

        //adding a '\n' for display reasons
        System.out.println();

        //returning the user's answer
        return answer;
    }


}


