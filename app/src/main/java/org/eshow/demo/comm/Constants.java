package org.eshow.demo.comm;

import java.util.ArrayList;

/**
 * Created by 任瑞刚
 * <p>
 * 应用常量定义类
 */
public class Constants {

    public static final String APP = "Eshow";  //应用信息存储
    //配置数据
    public static final int SPLASH_TIME = 1000;//启动页停留时间
    public static final int PAGE_SIZE = 10;
    //存储数据
    public static final String LOGIN_MOBILE = "login_mobile";  //登录手机号
    public static final String USER_TOKEN = "user_token";  //token信息
    public static final String USER_INFO = "user_info";  //用户信息
    public static final String GETUI_CLIENTID = "gt_clientid";  //个推ClientId
    public static final String VERSIONCODE = "versioncode";  //引导页
    public static final String IGNORE_VERSION = "ignore_version";  //忽略版本更新
    public static final String BIND_DEVICE = "bind_device";  //绑定设备名称
    //页面跳转参数
    public static final String INTENT_OBJECT = "intent_object";  //页面跳转参数

    public static final ArrayList<String> constellations = new ArrayList<String>() {
        {
            add("白羊座"); //3月21日～4月20日
            add("金牛座"); //4月21日～5月21日
            add("双子座"); //5月22日～6月21日
            add("巨蟹座"); //6月22日～7月22日
            add("狮子座"); //7月23日～8月22日
            add("处女座"); //8月23日～9月23日
            add("天秤座"); //9月24日～10月23日
            add("天蝎座"); //10月24日～11月22日
            add("射手座"); //11月23日～12月21日
            add("摩羯座"); //12月22日～1月20日
            add("水瓶座"); //1月21日～2月19日
            add("双鱼座"); //2月20日～3月20日
        }
    };
}
