package com.probie.Database;

import java.awt.*;

public class APIData {

    public static String APIName = "MyEyes";
    public static String APIVersion = "1.0.0";
    public static String JDKVersion = "21.0.8";
    public static String Author = "Probie";

    public static String Main = "com.probie.Main";

    public static String ErrorHead = "MyEyes.Error>"+" ";

    public static String MainName = "MyEyes";
    public static String DataName = "Data";
    public static String CacheName = "Cache";

    public static String MainTail = ".db";
    public static String DataTail = ".data";
    public static String CacheTail = ".cache";

    public static String Null = "null";
    public static String ChoseAll = "*";
    public static String SimpleMark = "-";
    public static String SplitMark = "|-|";

    public static String HerePath = System.getProperty("user.dir");

    public static int RGBMistakeRange = 10;
    public static int RGBMistakeRate = 10;

    public static int GuessMistakeRange = 10;
    public static int GuessMistakeRate = 60;

    public static int SimpleSize = 40;
    public static int AmplifyTime = 3;

    public static void printError(Object error) {
        System.out.println(ErrorHead+error.toString());
    }

    public static void throwError(Object error) {
        new Error(ErrorHead+error.toString()).printStackTrace();
    }

    public static Dimension getScreenSize() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }

    public static int[][] turnIntList(Object value) {
        if (value != null) {
            value = value.toString().substring(2,value.toString().length()-2);
            value = value.toString().replace(",","");
            String[] values = value.toString().split("]");

            int[][] result = new int[values[0].split(" ").length][values.length];
            for (int i = 0; i < values.length; i++) {
                String[] elements = values[i].split(" ");
                for (int j = 0; j < elements.length; j++) {
                    try {
                        result[j][i] = Integer.parseInt(elements[j]);
                    } catch (NumberFormatException | ArrayIndexOutOfBoundsException ignored) {}
                }
            }
            return result;
        }
        return null;
    }

}