package com.probie.Factory;

import com.probie.Interface.IAI;
import com.probie.Picture.Picture;

public class AIFactory implements IAI {

    @Override
    public Picture study(Picture picture) {
        return picture;
    }

    @Override
    public Object guess(Picture picture) {
        return null;
    }

}