package com.bangqu.lib.volley;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by renruigang on 2015/8/25.
 */
public class JsonRequest extends Request<JsonObject> {

    private final String TYPE_UTF8_CHARSET = "charset=UTF-8";
    private Gson mGson;
    private Map<String, String> params;
    private Map<String, String> header;
    private VolleyCallBack volleyCallBack;

    public JsonRequest(String url, Map<String, String> params, VolleyCallBack volleyCallBack) {
        super(Method.POST, url, volleyCallBack.errorListener);
        mGson = new Gson();
        this.params = removeNull(params);
        this.volleyCallBack = volleyCallBack;
    }

    public JsonRequest(String url, VolleyCallBack volleyCallBack) {
        super(Method.GET, url, volleyCallBack.errorListener);
        mGson = new Gson();
        this.volleyCallBack = volleyCallBack;
    }

    public void addHttpHeaders(Map<String, String> header) {
        this.header = header;
    }

    @Override
    public String getCacheKey() {
        if (params != null) {
            return getUrl() + params.toString();
        }
        return getUrl();
    }

    private Map<String, String> removeNull(Map<String, String> map) {
        if (map.containsValue(null)) {
            Set<Map.Entry<String, String>> mapEntries = map.entrySet();
            Iterator it = mapEntries.iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                if (entry.getValue() == null) {
                    it.remove();
                    continue;
                }
            }
        }
        return map;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return this.params;
    }

    @Override
    protected Response parseNetworkResponse(NetworkResponse response) {
        try {
            String type = response.headers.get("Content-Type");
            if (type == null) {
                type = TYPE_UTF8_CHARSET;
                response.headers.put("Content-Type", type);
            } else if (!type.contains("UTF-8")) {
                type += ";" + TYPE_UTF8_CHARSET;
                response.headers.put("Content-Type", type);
            }
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(mGson.fromJson(jsonString, JsonObject.class), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(JsonObject response) {
        volleyCallBack.listener.onResponse(response);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        if (header != null) {
            return header;
        }
        return super.getHeaders();
    }
}