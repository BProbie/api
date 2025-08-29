package com.probie.Attribute;

import com.probie.Picture.Picture;
import com.probie.Factory.PictureAttributeFactory;

public class PictureMatchAttribute extends PictureAttributeFactory {

    private boolean isInclude;
    private int[] xy;
    private int x;
    private int y;

    @Override
    public PictureMatchAttribute setSmallPicture(Picture smallPicture) {
        super.setSmallPicture(smallPicture);
        return this;
    }

    @Override
    public PictureMatchAttribute setBigPicture(Picture bigPicture) {
        super.setBigPicture(bigPicture);
        return this;
    }

    public PictureMatchAttribute setIsInclude(boolean isInclude) {
        this.isInclude = isInclude;
        if (!isInclude) {
            setInXY(new int[] {-1,-1});
        }
        return this;
    }

    public boolean getIsInclude() {
        return this.isInclude;
    }

    public PictureMatchAttribute setInXY(int[] xy) {
        this.xy = xy;
        return setInX(xy[0]).setInY(xy[1]);
    }

    public int[] getInXY() {
        return this.xy;
    }

    public PictureMatchAttribute setInX(int x) {
        this.x = x;
        return this;
    }

    public int getInX() {
        return this.x;
    }

    public PictureMatchAttribute setInY(int y) {
        this.y = y;
        return this;
    }

    public int getInY() {
        return this.y;
    }

}