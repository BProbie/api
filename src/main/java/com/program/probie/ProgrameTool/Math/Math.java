package com.program.probie.ProgrameTool.Math;

import java.util.Calendar;
import com.program.probie.ProgrameTool.Type.Time;

public class Math {

    public static double getPai() {return java.lang.Math.PI;}
    public static double getNum(double rad) {return (rad/getPai())*180;}
    public static double getRad(double num) {return num*(getPai()/180);}
    public static double sin(double num) {return java.lang.Math.sin(num);}
    public static double cos(double num) {return java.lang.Math.cos(num);}
    public static double tan(double num) {return java.lang.Math.tan(num);}

    public static long getMillisTime() {
        return System.currentTimeMillis();
    }

    public static long getSecondTime(int year,int month,int day,int hour,int minute,int second) {
        return year*31536000L+
                month*2592000L+
                day*86400L+
                hour*3600L+
                minute*60L+
                second;
    }
    public static long getSecondTime() {
        return getSecondTime(Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH)+1,
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE),
                Calendar.getInstance().get(Calendar.SECOND));
    }

    public static int getTime(long secondTime,Time time) {
        switch (time) {
            case Year: return getTime(secondTime)[0];
            case Month: return getTime(secondTime)[1];
            case Day: return getTime(secondTime)[2];
            case Hour: return getTime(secondTime)[3];
            case Minute: return getTime(secondTime)[4];
            case Second: return getTime(secondTime)[5];
        }
        return -1;
    }
    public static int[] getTime(long secondTime) {
        int year,month,day,hour,minute,second;
        second = java.lang.Math.toIntExact(secondTime%31536000L%2592000L%86400L%3600L%60L);
        minute = java.lang.Math.toIntExact((secondTime%31536000L%2592000L%86400L%3600L-second)/60L);
        hour = java.lang.Math.toIntExact((secondTime%31536000L%2592000L%86400L-minute*60L)/3600L);
        day = java.lang.Math.toIntExact((secondTime%31536000L%2592000L-hour*3600L)/86400L);
        month = java.lang.Math.toIntExact((secondTime%31536000L-day*86400L)/2592000L);
        year = java.lang.Math.toIntExact((secondTime-month*2592000L)/31536000L);
        return new int[] {year,month,day,hour,minute,second};
    }

}