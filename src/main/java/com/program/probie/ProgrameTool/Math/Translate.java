package com.program.probie.ProgrameTool.Math;

public class Translate {

    public static String replaceComplain(String value,String oldValue,String newValue) {
        return value.toString().replaceAll("(?<!"+oldValue+")"+oldValue+"(?!"+oldValue+")",String.valueOf(newValue));
    }

    public static String backslashUp(Object value) {
        value = value.toString().replaceAll("(?<!\\\\)\\\\\\\\(?!\\\\)","_TwoBackSlash_");
        value = value.toString().replaceAll("\\\\+","$0$0\\\\");
        return value.toString().replaceAll("_TwoBackSlash_","\\\\\\\\");
    }

    public static String backslashDown(Object value) {
        value = value.toString().replaceAll("(?<!\\\\)\\\\\\\\(?!\\\\)","_TwoBackSlash_");
        if (value.toString().contains("\\")) {
            char[] chars = value.toString().toCharArray();
            value = "";
            for (int i = 0; i < chars.length; i++) {
                if (chars[i] == '\\') {
                    try {
                        if (chars[i+1]=='\\') {
                            value = value + "\\";
                            i++;
                        }
                    } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {}
                } else {
                    value = value.toString() + chars[i];
                }
            }
        }
        return value.toString().replaceAll("_TwoBackSlash_","\\\\\\\\");
    }

}