package top.mnsx.service.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.internal.signer.OSSSignerParams;
import com.aliyun.oss.model.OSSObject;
import com.sun.javafx.scene.shape.PathUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.mnsx.domain.ResponseResult;
import top.mnsx.enums.AppHttpCodeEnum;
import top.mnsx.exception.SystemException;
import top.mnsx.properties.OssProperties;
import top.mnsx.service.UploadService;
import top.mnsx.utils.PathUtil;

import java.io.*;
import java.time.Month;
import java.util.Calendar;
import java.util.Objects;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
@Service
public class UploadServiceImpl implements UploadService {
    private static final String  OSS_URL_PREFIX = "https://mnsx-blog.oss-cn-hangzhou.aliyuncs.com/";
    @Autowired
    private OssProperties ossProperties;

    @Override
    public ResponseResult uploadImg(MultipartFile img) {
        // 判断文件类、文件大小
        String originalFilename = img.getOriginalFilename();
        if (originalFilename == null) {
            throw new SystemException(AppHttpCodeEnum.FILE_CAN_NOT_EMPTY);
        }
        if (!originalFilename.endsWith(".png") && !originalFilename.endsWith(".jpg")) {
            throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERROR);
        }

        // 上传文件到oss
        String url = upload(img, originalFilename);
        return ResponseResult.okResult(url);
    }

    public String upload(MultipartFile img, String filePath) {
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = ossProperties.getEndpoint();
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = ossProperties.getAccessKeyId();
        String accessKeySecret = ossProperties.getAccessKeySecret();
        // 填写Bucket名称，例如examplebucket。
        String bucketName = ossProperties.getBucketName();
        // 填写Object完整路径，例如exampledir/exampleobject.txt。Object完整路径中不能包含Bucket名称。
        String objectName = PathUtil.generatePath(filePath);

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            ossClient.putObject(bucketName, objectName, img.getInputStream());
            return OSS_URL_PREFIX + objectName;
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return "";
    }
}
