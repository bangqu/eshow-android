package org.eshow.demo.comm;

/**
 * 创建者     rrg
 * 描述	      ${用于保存app端与服务器交互的url等常量}
 */
public class HttpConfig {

    private static final String PRO_URL = "http://api.chemo.daoqun.com/";
    private static final String API_URL = "https://api.easyapi.com/";
    public static final String QINIU_URL = "http://oyeu81n16.bkt.clouddn.com/";
    public static final String MSG_SYS = "https://notification-api.easyapi.com/api/notificationRecord";
    //**********************************************************
    //获取验证码  类型（signup注册验证，login登录验证，找回密码password，identity身份验证）
    public static final String GET_CAPTCHA = PRO_URL + "captcha/send.json";
    //用户注册
    public static final String GET_SIGNUP = PRO_URL + "user/signup.json";
    //用户验证码登录
    public static final String GET_CAPTCHA_LOGIN = PRO_URL + "captcha/login.json";
    //用户登录
    public static final String GET_LOGIN = PRO_URL + "user/login.json";
    //第三方登录
    public static final String AUTH_LOGIN = PRO_URL + "third-party/login.json";
    //添加第三方账号
    public static final String ADD_AUTH_THIRD = PRO_URL + "third-party/save.json";
    //获取第三方账号列表
    public static final String THIRD_PARTY_MINE = PRO_URL + "third-party/mine.json";
    //添加第三方账号
    public static final String THIRD_PARTY_SAVE = PRO_URL + "third-party/save.json";
    //删除第三方账号
    public static final String THIRD_PARTY_DEL = PRO_URL + "third-party/delete.json";
    //忘记密码
    public static final String GET_LOSEPASS = PRO_URL + "forget-password/reset.json";
    //修改密码
    public static final String UPDATE_PASSWORD = PRO_URL + "user/updatePassword.json";
}
