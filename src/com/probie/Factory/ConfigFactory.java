package com.probie.Factory;

import java.util.List;
import java.util.Arrays;
import java.util.HashSet;
import com.probie.MyEyes;
import com.probie.Picture.Picture;
import com.probie.Database.APIData;
import com.probie.Interface.IConfig;

public class ConfigFactory implements IConfig {

    @Override
    public Picture cache(Picture picture) {
        if (picture.getUID() != null) {
            if (picture.getCache().connect()) {
                if (picture.getCache().getDefaultValue(picture.getUID(), null) == null) {
                    picture.getCache().setValue(picture.getUID(), Arrays.deepToString(MyEyes.getRGB(picture)));
                }
            }
            picture.setHadCache(true);
        } else {
            APIData.throwError("The Picture Had Not Define");
        }
        return picture;
    }

    @Override
    public Picture data(Picture picture) {
        if (picture.getUID() != null) {
            for (Object value : picture.getValues()) {
                picture.getData().setFileName(value.toString()+ APIData.DataTail);
                if (picture.getData().connect()) {
                    Object temp = picture.getData().getDefaultValue(value, null);
                    if (temp != null) {
                        if (!new HashSet<>(List.of(temp.toString().split(APIData.SplitMark))).contains(Arrays.deepToString(picture.getSimpleRGB()))) {
                            temp = temp+APIData.SplitMark+Arrays.deepToString(picture.getSimpleRGB());
                            picture.getData().setValue(value, temp);
                        }
                    } else {
                        picture.getData().setValue(value, Arrays.deepToString(picture.getSimpleRGB()));
                    }
                }
            }
        } else {
            APIData.throwError("The Picture Had Not Define");
        }
        return picture;
    }

}