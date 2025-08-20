package com.program.probie.ProgrameTool.Computer.misc;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Screen {

    public static BufferedImage getScreen() {
        try {
            Dimension dimension = getSize();
            return new Robot().createScreenCapture(new Rectangle(0,0,dimension.width,dimension.height));
        } catch (AWTException awtException) {
            awtException.printStackTrace();
        }
        return null;
    }

    public static Dimension getSize() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }

}