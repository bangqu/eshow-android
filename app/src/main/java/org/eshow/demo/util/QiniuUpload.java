package org.eshow.demo.util;

import android.os.Handler;

import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.KeyGenerator;
import com.qiniu.android.storage.Recorder;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.persistent.FileRecorder;

import java.io.File;
import java.io.IOException;

/**
 */
public class QiniuUpload {

    private static QiniuUpload qiniuUpload;
    private UploadManager uploadManager;

    public static QiniuUpload getInstance() {
        if (qiniuUpload == null) {
            qiniuUpload = new QiniuUpload();
        }
        return qiniuUpload;
    }

    public QiniuUpload() {
//        Configuration config = new Configuration.Builder()
//                .chunkSize(512 * 1024)        // 分片上传时，每片的大小。 默认256K
//                .putThreshhold(1024 * 1024)   // 启用分片上传阀值。默认512K
//                .connectTimeout(10)           // 链接超时。默认10秒
//                .useHttps(true)               // 是否使用https上传域名
//                .responseTimeout(60)          // 服务器响应超时。默认60秒
//                .build();
        uploadManager = new UploadManager();
    }

    public void UploadImageDelay(final String path, final String key, final String token, final UpCompletionHandler handler) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                uploadManager.put(path, key, token, handler, null);
            }
        }, 3000);
    }

    public void UploadImage(final String path, final String key, final String token, final UpCompletionHandler handler) {
        uploadManager.put(path, key, token, handler, null);
    }

    public void UploadImage(File path, String key, String token, UpCompletionHandler handler) {
        uploadManager.put(path, key, token, handler, null);
    }

    public void UploadFile(String path, String key, String token, UpCompletionHandler handler) {
        try {
            Recorder recorder = new FileRecorder(path);
            //默认使用key的url_safe_base64编码字符串作为断点记录文件的文件名
            //避免记录文件冲突（特别是key指定为null时），也可自定义文件名(下方为默认实现)：
            KeyGenerator keyGen = new KeyGenerator() {
                public String gen(String key, File file) {
                    // 不必使用url_safe_base64转换，uploadManager内部会处理
                    // 该返回值可替换为基于key、文件内容、上下文的其它信息生成的文件名
                    return key + "_._" + new StringBuffer(file.getAbsolutePath()).reverse();
                }
            };
            // 重用uploadManager。一般地，只需要创建一个uploadManager对象
            //UploadManager uploadManager = new UploadManager(recorder);  // 1
            // UploadManager uploadManager = new UploadManager(recorder, keyGen); // 2
            // 或在初始化时指定：
            Configuration config = new Configuration.Builder()
                    // recorder分片上传时，已上传片记录器
                    // keyGen分片上传时，生成标识符，用于片记录器区分是哪个文件的上传记录
                    .recorder(recorder, keyGen)
                    .build();
            uploadManager = new UploadManager(config);
            uploadManager.put(path, key, token,
                    handler, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
