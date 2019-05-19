package com.ubatis.circleserver.api.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.ubatis.circleserver.config.SysConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Date;

/**
 * 阿里oss API
 */
@Component
public class OssAPI {

    private final static Logger logger = LoggerFactory.getLogger(OssAPI.class);

    /** oss url过期时间 ，50年*/
    private Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 50);

    @Autowired
    private SysConfig mSysConfig;

    private OSSClient ossClient;

    private static BASE64Decoder mDecoder = null;

    private OSSClient getOssClient() {
        if(ossClient == null) ossClient = new OSSClient(mSysConfig.getOss_endpoint(),
                mSysConfig.getOss_accesskey_id(), mSysConfig.getOss_accesskey_secret());
        return ossClient;
    }

    private static BASE64Decoder getBase64Decoder(){
        if(mDecoder == null){
            mDecoder = new BASE64Decoder();
        }
        return mDecoder;
    }

    public String putFile(String fileKey, MultipartFile file){
        PutObjectResult putObjectResult = null;
        ObjectMetadata objectMeta = new ObjectMetadata();
        objectMeta.setContentLength(file.getSize());
        objectMeta.setContentType(file.getContentType());
        try {
            putObjectResult = getOssClient().putObject(mSysConfig.getOss_bucket_name(), fileKey, file.getInputStream(), objectMeta);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        URL url = getOssClient().generatePresignedUrl(mSysConfig.getOss_bucket_name(), fileKey, expiration);
        if(url != null) {
            return url.toString();
        }
        return null;
    }

    public String putBase64(String fileKey, String base64Str){
        PutObjectResult putObjectResult = null;
        try {

            putObjectResult = getOssClient().putObject(mSysConfig.getOss_bucket_name(), fileKey
                    , new ByteArrayInputStream(getBase64Decoder().decodeBuffer(base64Str)));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        URL url = getOssClient().generatePresignedUrl(mSysConfig.getOss_bucket_name(), fileKey, expiration);
        if(url != null) {
            return url.toString();
        }
        return null;
    }

    public void delFile(String fileKey){
        getOssClient().deleteObject(mSysConfig.getOss_bucket_name(), fileKey);
    }

}
