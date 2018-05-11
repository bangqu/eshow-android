package com.bangqu.photos.bean;

import java.util.ArrayList;

public class ImageFloder {
    /**
     * 图片的文件夹路径
     */
    private String dir;

    /**
     * 第一张图片的路径
     */
    private String firstImagePath;

    /**
     * 文件夹的名称
     */
    private String name;

    /**
     * 图片的数量
     */
    private int count;

    /**
     * 所有图片的路径
     */
    private ArrayList<String> mImgPaths;

    public ArrayList<String> getImgPaths() {
        return mImgPaths;
    }

    public void setImgPaths(ArrayList<String> mImgPaths) {
        this.mImgPaths = mImgPaths;
    }

    /*所有图片添加路径方法*/
    public void addImgPaths(ArrayList<String> mImgPaths) {
        if (this.mImgPaths == null) {
            this.mImgPaths = new ArrayList<>();
            this.firstImagePath = mImgPaths.get(0);
        }
        this.mImgPaths.addAll(mImgPaths);
    }

    public String getDir() {
        return dir;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDir(String dir) {
        this.dir = dir;
        int lastIndexOf = this.dir.lastIndexOf("/");
        if (lastIndexOf > 0) {
            this.name = this.dir.substring(lastIndexOf);
        }
    }

    public String getFirstImagePath() {
        return firstImagePath;
    }

    public void setFirstImagePath(String firstImagePath) {
        this.firstImagePath = firstImagePath;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
