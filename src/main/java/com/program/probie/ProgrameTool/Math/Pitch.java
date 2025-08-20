package com.program.probie.ProgrameTool.Math;

public class Pitch {

    public static int getLR(int pitch) {
        if (pitch < 0) {
            pitch = -pitch;
            while (pitch-360 > 0) {pitch = pitch-360;}
            return 360-pitch;
        }
        while (pitch-360 > 0) {pitch = pitch-360;}
        return pitch;
    }

    public static int getUD(int pitch) {
        return -pitch;
    }

}