package com.bangqu.download.presenter;

public interface DownLoadImpl {
    /**
     * 绑定版本更新下载服务
     *
     * @param apkUrl apk下载路径
     */
    void bindService(String apkUrl, String fileName);

}
