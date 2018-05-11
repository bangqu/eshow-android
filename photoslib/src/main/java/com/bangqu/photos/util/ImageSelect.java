package com.bangqu.photos.util;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/21.
 */
public class ImageSelect {

    public static final int MAX_SIZE = 9;
    /**
     * 用户选择的图片，存储为图片的完整路径
     */
    public static List<String> mSelectedImage = new LinkedList<>();

    public static boolean addSelectedImage(String path) {
        if (mSelectedImage.size() < MAX_SIZE) {
            if (!mSelectedImage.contains(path))
                mSelectedImage.add(path);
            return true;
        }
        return false;
    }
}
