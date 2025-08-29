package com.probie.Interface;

import com.probie.Picture.Picture;
import com.probie.Attribute.PictureMatchAttribute;

public interface IPictureAttributeManager {
    PictureMatchAttribute getPictureMatchAttribute(Picture smallPicture, Picture bigPicture);
}