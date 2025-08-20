package com.program.probie.ProgrameTool.Math;

public class Random {

    public static int getRange(int min,int max) {
        if (max>=min) {
            if (min>=0) {
                return (int)(java.lang.Math.random()*(max-min+1)+min);
            } else {
                if (max>=0) {
                    return (int)(java.lang.Math.random()*(max-min+2)+min-1);
                } else {
                    return (int)(java.lang.Math.random()*(max-min+1)+min-1);
                }
            }
        }
        return -1;
    }

}