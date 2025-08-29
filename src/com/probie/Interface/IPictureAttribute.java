package com.probie.Interface;

import com.probie.Picture.Picture;
import com.probie.Factory.PictureAttributeFactory;

public interface IPictureAttribute {

    PictureAttributeFactory setSmallPicture(Picture smallPicture);
    Picture getSmallPicture();

    PictureAttributeFactory setBigPicture(Picture bigPicture);
    Picture getBigPicture();

}