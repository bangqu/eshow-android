package com.bangqu.lib.volley;

import com.google.gson.JsonObject;

/**
 * Created by renruigang on 2015/8/25.
 * 自定义请求返回接口
 */
public interface ResponseCallBack {
    /*  成功  */
    void onResponseSuccess(String tag, JsonObject response, String code, String msg);

    /*  查询暂无数据  */
    void onResponseNoData(String tag, String code, String msg);

    /*  失败  */
    void onResponseError(String tag, String code, String msg);

    /*  用户Token过期  */
    void onResponseOverDue(String tag, String code, String msg);
}
