package com.probie.Factory;

import com.probie.Picture.Picture;
import com.probie.Interface.IPictureAttribute;

public class PictureAttributeFactory implements IPictureAttribute, Cloneable {

    private Picture smallPicture;
    private Picture bigPicture;

    public PictureAttributeFactory() {

    }

    @Override
    public PictureAttributeFactory setSmallPicture(Picture smallPicture) {
        this.smallPicture = smallPicture;
        return this;
    }

    @Override
    public Picture getSmallPicture() {
        return this.smallPicture;
    }

    @Override
    public PictureAttributeFactory setBigPicture(Picture bigPicture) {
        this.bigPicture = bigPicture;
        return this;
    }

    @Override
    public Picture getBigPicture() {
        return this.bigPicture;
    }

}