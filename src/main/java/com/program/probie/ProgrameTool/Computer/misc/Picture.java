package com.program.probie.ProgrameTool.Computer.misc;

import java.io.File;
import javax.swing.*;
import java.util.Objects;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import javax.swing.filechooser.FileSystemView;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Picture {

    public static Object[] getAttribute(BufferedImage findPicture,BufferedImage atPicture) {
        AtomicBoolean isAt = new AtomicBoolean(false);
        AtomicInteger atX = new AtomicInteger(-1);
        AtomicInteger atY = new AtomicInteger(-1);
        ExecutorService pool = Executors.newFixedThreadPool(atPicture.getHeight());
        for (int i = 0; i < atPicture.getHeight(); i++) {
            int atPictureY = i;
            pool.submit(() -> {
                for (int atPictureX = 0; atPictureX < atPicture.getWidth(); atPictureX++) {
                    if (atPicture.getRGB(atPictureX,atPictureY)==findPicture.getRGB(0,0)) {
                        int error = 0;
                        for (int findPictureY = 0; findPictureY < findPicture.getHeight(); findPictureY++) {
                            for (int findPictureX = 0; findPictureX < findPicture.getWidth(); findPictureX++) {
                                if (atPicture.getRGB(atPictureX+findPictureX,atPictureY+findPictureY)==findPicture.getRGB(findPictureX,findPictureY)) {
                                    error++;
                                }
                                if (error>=(findPicture.getWidth())*(findPicture.getHeight())) {
                                    isAt.set(true);
                                    atX.set(atPictureX+findPicture.getWidth()/2);
                                    atY.set(atPictureY+findPicture.getHeight()/2);
                                }
                            }
                        }
                    }
                }
            });
        }
        pool.shutdown();
        while (!pool.isTerminated()) {}
        return new Object[] {isAt,atX,atY};
    }

    public static JFrame showPicture(BufferedImage bufferedImage) {
        JFrame frame = new JFrame("");
        try {frame.setSize(bufferedImage.getWidth()+10,bufferedImage.getHeight()+35);} catch (Exception exception) {exception.printStackTrace();}
        frame.setLocation(Objects.requireNonNull(Screen.getScreen()).getWidth()/2-frame.getWidth()/2,Screen.getScreen().getHeight()/2-frame.getHeight()/2);
        frame.getRootPane().setWindowDecorationStyle(JRootPane.INFORMATION_DIALOG);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(new JLabel(new ImageIcon(bufferedImage)));
        frame.setUndecorated(true);
        frame.setOpacity(0.8f);
        return frame;
    }

    public static JFrame showPicture(BufferedImage bufferedImage,int pictureWidth,int pictureHeight) {
        JFrame frame = new JFrame("");
        frame.setSize(pictureWidth+10,pictureHeight+35);
        frame.setUndecorated(true);
        frame.setOpacity(0.8f);
        frame.setLocation(Screen.getScreen().getWidth()/2-frame.getWidth()/2,Screen.getScreen().getHeight()/2-frame.getHeight()/2);
        frame.getRootPane().setWindowDecorationStyle(JRootPane.INFORMATION_DIALOG);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        BufferedImage image = new BufferedImage(pictureWidth,pictureHeight,BufferedImage.TYPE_3BYTE_BGR);
        image.createGraphics().drawImage(bufferedImage,0,0,pictureWidth,pictureHeight,null);
        frame.add(new JLabel(new ImageIcon(image)));
        return frame;
    }

    public static Icon getIcon(String iconPath) {
        return FileSystemView.getFileSystemView().getSystemIcon(new File(iconPath));
    }

    public static BufferedImage getPicture(String imagePath) {
        try {
            return ImageIO.read(new File(imagePath));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return null;
    }

    public static void outPicture(BufferedImage bufferedImage,String path) {
        File file = new File(path);
        if (!file.exists()) {file.mkdirs();}
        try {
            ImageIO.write(bufferedImage,path.split("\\.")[path.split("\\.").length-1],file);
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }

}