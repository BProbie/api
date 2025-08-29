package com.probie.Factory;

import java.util.HashMap;
import java.util.ArrayList;
import com.probie.Picture.Picture;
import java.util.concurrent.Future;
import com.probie.Database.APIData;
import java.awt.image.BufferedImage;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ExecutionException;
import com.probie.Attribute.PictureMatchAttribute;
import com.probie.Interface.IPictureAttributeManager;

public class PictureAttributeManagerFactory implements IPictureAttributeManager {

    private static HashMap<String, PictureMatchAttribute> pictureMaatchAttributeHashMap = new HashMap<>();

    @Override
    public PictureMatchAttribute getPictureMatchAttribute(Picture smallPicture, Picture bigPicture) {
        if (smallPicture.getUID() != null && bigPicture.getUID() != null) {
            PictureMatchAttribute pictureMatchAttribute = pictureMaatchAttributeHashMap.getOrDefault(smallPicture.getUID()+APIData.SplitMark+bigPicture.getUID(), null);
            if (pictureMatchAttribute == null) {
                BufferedImage smallBufferedImage = smallPicture.getBufferedImage();
                BufferedImage bigBufferedImage = bigPicture.getBufferedImage();
                if (smallBufferedImage.getWidth() < bigBufferedImage.getWidth() && smallBufferedImage.getHeight() < bigBufferedImage.getHeight()) {
                    int[][] smallRGB = smallPicture.getRGB();
                    int[][] bigRGB = bigPicture.getRGB();
                    PictureMatchAttribute attribute = new PictureMatchAttribute();
                    ArrayList<Future<?>> futureArrayList = new ArrayList<>();
                    try (ExecutorService pool = Executors.newFixedThreadPool(bigBufferedImage.getHeight())) {
                        for (int i = 0; i < bigBufferedImage.getHeight(); i++) {
                            final int bigY = i;
                            Future<?> future = pool.submit(() -> {
                                for (int bigX = 0; bigX < bigBufferedImage.getWidth(); bigX++) {
                                    int temp = Math.abs(Math.abs(bigRGB[bigX][bigY])-Math.abs(smallRGB[0][0]));
                                    if (temp <= APIData.RGBMistakeRange) {
                                        int error = 0;
                                        for (int smallY = 0; smallY < smallBufferedImage.getHeight(); smallY++) {
                                            for (int smallX = 0; smallX < smallBufferedImage.getWidth(); smallX++) {
                                                int smallTemp = Math.abs(smallRGB[smallX][smallY]);
                                                int bigTemp = Math.abs(bigRGB[bigX+smallX][bigY+smallY]);
                                                if (!(Math.abs(bigTemp-smallTemp) <= APIData.RGBMistakeRange)) {
                                                    error++;
                                                }
                                            }
                                        }
                                        if (error <= smallBufferedImage.getWidth()*smallBufferedImage.getHeight()*(APIData.RGBMistakeRate/100)) {
                                            attribute.setIsInclude(true);
                                            if (attribute.getInXY() == null) {
                                                attribute.setInXY(new int[] {bigX, bigY});
                                            } else {
                                                attribute.setInXY(new int[] {(attribute.getInX()+bigX)/2,(attribute.getInY()+bigY)/2});
                                            }
                                        }
                                    }
                                }
                            });
                            futureArrayList.add(future);
                        }
                        for (Future<?> future : futureArrayList) {
                            try {
                                future.get();
                            } catch (InterruptedException | ExecutionException exception) {
                                throw new RuntimeException(exception);
                            }
                        }
                        pool.shutdown();
                    } catch (IllegalArgumentException illegalArgumentException) {
                        throw new RuntimeException(illegalArgumentException);
                    }
                    return attribute;
                } else {
                    return new PictureMatchAttribute().setIsInclude(false);
                }
            } else {
                return pictureMatchAttribute;
            }
        } else {
            APIData.throwError("The Picture Had Not Define");
        }
        return null;
    }

    public static HashMap<String, PictureMatchAttribute> getPictureMaatchAttributeHashMap() {
        return pictureMaatchAttributeHashMap;
    }

}