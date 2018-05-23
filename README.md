EShow开源框架中的Android框架
===========
www.eshow.org.cn 旗下 Android开源框架

一、项目编译环境
-----
Android Studio 3.0<br>
>Gradle配置：<br>
* build.gradle依赖版本（gradle:3.0.0）<br>
* gradle-wrapper.properties下载版本gradle-4.1-all.zip<br>
* compileSdkVersion 26 （Android 8.0）<br>

二、项目Model包说明
-----
* bangqulib<br>
项目基础框架，包含网络请求Volley的封装、基类、常用的自定义控件、弹出框样式等
* photoslib<br>
支持单张图片选择和多图选择的图库选择库
* bluetoothlib<br>
蓝牙模块，针对BLE模块的搜索连接和命令写入
* downloadlib<br>
文件下载服务，包含百分比进度条ProgressBar
* qrcodelib<br>
扫一扫，扫描二维码和二维码图片解析

三、项目目录结构
-----
>activity 活动类<br> 
>fragment 界面碎片<br>
>adapter 数据适配器<br>
>base 基类<br>
>comm 应用相关方法<br>
>listener 监听回调接口<br>
>model 数据结构<br>
>util 通用基础方法<br>
>widget 自定义控件<br>

四、项目代码说明
-----
###1、网络请求说明
首先定义一个请求响应接收类，返回参数为JsonObject
```Java
private ResponseCallBack responseCallBack = new ResponseCallBack() {
        @Override
        public void onResponseSuccess(String tag, JsonObject response, String code, String msg) {
            //请求成功
        }

        @Override
        public void onResponseNoData(String tag, String code, String msg) {
            //无数据
        }

        @Override
        public void onResponseError(String tag, String code, String msg) {
            //请求失败
        }

        @Override
        public void onResponseOverDue(String tag, String code, String msg) {
            //用户信息过期
        }
    };
```
然后创建params参数，指定tag(用于响应接收类判断回调)进行网络请求,getData和postData分别对应HTTP GET和POST请求
```java
HashMap<String, String> params = new HashMap<>();
params.put(key, value);
getData(url, params, new VolleyCallBack(tag, responseCallBack));
```
VolleyCallBack中封装了网络请求成功和失败的判断，可根据实际接口返回规则进行修改