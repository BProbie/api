package com.program.probie.ProgrameTool.Computer.misc;

import java.awt.*;
import java.awt.event.InputEvent;

public class Mouse {

    public static void clickMouse(int x,int y,int count) {
        try {
            Robot robot = new Robot();
            robot.mouseMove(x,y);
            for (int i = 0; i < count; i++) {
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            }
        } catch (AWTException awtException) {
            awtException.printStackTrace();
        }
    }

    public static void clickKey(int keyCode,int Count) {
        try {
            Robot robot = new Robot();
            for (int i = 0; i < Count; i++) {
                robot.keyPress(keyCode);
                robot.keyRelease(keyCode);
            }
        } catch (AWTException awtException) {
            awtException.printStackTrace();
        }
    }

    public static Point getMousePoint() {
        return MouseInfo.getPointerInfo().getLocation();
    }

}