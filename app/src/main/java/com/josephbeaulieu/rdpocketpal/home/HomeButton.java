package com.josephbeaulieu.rdpocketpal.home;

public class HomeButton {
    private int mImageResourceId;
    private int mTextResourceId;
    private Class mClazz;

    public HomeButton(int imageResourceId, int textResourceId, Class clazz) {
        mImageResourceId = imageResourceId;
        mTextResourceId = textResourceId;
        mClazz = clazz;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }

    public int getTextResourceId() {
        return mTextResourceId;
    }

    public Class getClazz() {
        return mClazz;
    }
}
