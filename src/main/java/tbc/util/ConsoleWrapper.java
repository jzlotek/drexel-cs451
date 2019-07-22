package tbc.util;

import java.util.Scanner;

/*
 * Wrapper for Console I/O
 */
public class ConsoleWrapper {
    public ConsoleWrapper() {

    }

    public static void Write(Object message) {
        System.out.print(message);
    }

    public static void WriteLn(Object message) {
        System.out.println(message);
    }

    public static String Read() {
        try {
            Scanner input = new Scanner(System.in);
            while (input.hasNextLine()) {
                return input.nextLine();
            }
        } catch (Exception ex) {
            System.out.println("Console Input Invalid");
        }
        return "";
    }

    public static boolean CheckRead() {
        Scanner input = new Scanner(System.in);
        if (input.hasNext()) {
            return true;
        } else {
            return false;
        }
    }

}