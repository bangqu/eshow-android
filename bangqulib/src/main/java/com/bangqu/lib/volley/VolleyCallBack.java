package com.bangqu.lib.volley;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by renruigang on 2015/8/25.
 */
public class VolleyCallBack {

    private static final String NO_INTERNET = "无网络连接~！";
    private static final String GENERIC_SERVER_DOWN = "连接服务器失败~！";
    private static final String GENERIC_SERVER_ERROR = "服务器异常~！";
    private static final String GENERIC_ERROR = "网络异常,请稍后再试~！";
    private static final String DATA_FORMAT_ERROR = "数据格式错误~！";

    public Response.ErrorListener errorListener;
    public Response.Listener<JsonObject> listener;

    public VolleyCallBack(final String tag, final ResponseCallBack responseCallBack) {

        listener = new Response.Listener<JsonObject>() {
            @Override
            public void onResponse(JsonObject response) {
                if (null != response.get("code")) {
                    String code = response.get("code").toString();
                    if ("0".equals(code)) {
                        String message = response.get("message") != null ? response.get("message").toString() : "";
                        responseCallBack.onResponseNoData(tag, code, message);
                    } else if ("1".equals(code)) {
                        String message = response.get("message") != null ? response.get("message").toString() : "";
                        responseCallBack.onResponseSuccess(tag, response, code, message);
                    } else {
                        String message = response.get("message") != null ? response.get("message").toString() : "";
                        responseCallBack.onResponseError(tag, code, message);
                    }
                } else {
                    responseCallBack.onResponseError(tag, "X", DATA_FORMAT_ERROR);
                }
            }
        };
        errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse != null && error.networkResponse.statusCode == 400) {
                    try {
                        HashMap<String, String> result = new Gson().fromJson(new String(error.networkResponse.data),
                                new TypeToken<Map<String, String>>() {
                                }.getType());
                        String code = result.get("code") != null ? result.get("code").toString() : "";
                        String message = result.get("message") != null ? result.get("message").toString() : "";
                        responseCallBack.onResponseError(tag, code, message);
                    } catch (Exception e) {
                        e.printStackTrace();
                        responseCallBack.onResponseError(tag, "X", DATA_FORMAT_ERROR);
                    }
                } else {
                    String code = error.networkResponse != null ? error.networkResponse.statusCode + "" : "N";
                    responseCallBack.onResponseError(tag, code, getErrorMessage(error));
                }
            }
        };
    }

    private String getErrorMessage(Object error) {
        if (error instanceof TimeoutError) {
            return GENERIC_SERVER_DOWN;
        } else if (isNetworkProblem(error)) {
            return NO_INTERNET;
        } else if (isServerProblem(error)) {
            VolleyError err = (VolleyError) error;
            if (err.networkResponse != null) {
                int state = err.networkResponse.statusCode;
                if (state > 499 && state < 600) {
                    return GENERIC_SERVER_ERROR;
                }
            }
        }
        return GENERIC_ERROR;
    }

    private boolean isNetworkProblem(Object error) {
        return (error instanceof NetworkError) || (error instanceof NoConnectionError);
    }

    private static boolean isServerProblem(Object error) {
        return (error instanceof ServerError) || (error instanceof AuthFailureError);
    }
}
