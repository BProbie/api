package com.probie.Factory;

import java.io.File;
import java.util.HashMap;
import com.probie.MyEyes;
import com.probie.Database.Data;
import com.probie.Picture.Picture;
import com.probie.Database.APIData;

public class EyesFactory extends AIFactory {

    @Override
    public Picture study(Picture picture) {
        MyEyes.data(picture);
        return picture;
    }

    @Override
    public Object guess(Picture picture) {
        if (picture.getUID() != null) {
            Data data = (Data) MyEyes.getData().clone();
            String path = data.getPath();
            File filePath = new File(path);
            if (filePath.exists()) {
                if (filePath.isDirectory()) {
                    File[] files = filePath.listFiles();
                    if (files != null) {
                        HashMap<String, int[][]> studyDataMap = new HashMap<>();
                        HashMap<String, Integer> guessRateMap = new HashMap<>();
                        int count = 0;
                        for (File file : files) {
                            if (file.getName().contains(APIData.DataTail)) {
                                count++;
                                String key = file.getName().replace(APIData.DataTail,"");
                                data.setFileName(file.getName());
                                if (data.connect()) {
                                    studyDataMap.put(key, APIData.turnIntList(data.getValue(key)));
                                }
                            }
                        }
                        if (count != 0) {
                            // TODO
                            int[][] simpleRGB = picture.getSimpleRGB();
                            for (String key : studyDataMap.keySet()) {
                                int[][] checkRGB = studyDataMap.get(key);
                                int weight = Math.min(simpleRGB[0].length, checkRGB[0].length);
                                int height = Math.min(simpleRGB.length, checkRGB.length);
                                int error = 0;
                                for (int y = 0; y < height; y++) {
                                    for (int x = 0; x < weight; x++) {
                                        int temp = Math.abs(Math.abs(simpleRGB[x][y])-Math.abs(checkRGB[x][y]));
                                        if (!(temp <= APIData.GuessMistakeRange)) {
                                            error++;
                                        }
                                    }
                                }
                                int temp = guessRateMap.getOrDefault(key, -1);
                                if (temp == -1) {
                                    guessRateMap.put(key, error);
                                } else {
                                    if (temp > error) {
                                        guessRateMap.put(key, error);
                                    }
                                }
                            }

                            Object[] result = new Object[] {APIData.Null,APIData.Null};
                            for (String key : guessRateMap.keySet()) {
                                int value = guessRateMap.get(key);
                                if (value/(simpleRGB.length*simpleRGB[0].length) <= (APIData.GuessMistakeRate/100)) {
                                    if (result[1].equals(APIData.Null)) {
                                        result[0] = key;
                                        result[1] = value;
                                    } else {
                                        if ((value < Integer.parseInt(result[1].toString()))) {
                                            result[0] = key;
                                            result[1] = value;
                                        }
                                    }
                                }
                            }
                            return result[0];
                        } else {
                            APIData.printError("There Are Not Any Data File In The Path "+path);
                        }
                    } else {
                        APIData.throwError("Can Not Get Any Thing Maybe The Path "+path+" Is Air Or Not Enough Promission");
                    }
                } else {
                    APIData.throwError("The Path "+path+" Is Not A Directory");
                }
            } else {
                APIData.throwError("The Path "+path+" Is Not Exists");
            }
        } else {
            APIData.throwError("The Picture Had Not Define");
        }
        return null;
    }

}