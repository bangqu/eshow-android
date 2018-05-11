package com.bangqu.lib.slipload.callback;

public interface IFooterCallBack {

    /**
     * 正常状态
     */
    void onStateNormal();

    /**
     * 准备刷新
     */
    void onStateReady();

    /**
     * 正在加载
     */
    void onStateLoading();

    /**
     * 刷新成功
     */
    void onStateSuccess();

    /**
     * 刷新失败
     */
    void onStateFail();

    /**
     * 已无更多数据
     */
    void onStateComplete();
}
