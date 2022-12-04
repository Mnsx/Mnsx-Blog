package top.mnsx.service;

import org.springframework.web.multipart.MultipartFile;
import top.mnsx.domain.ResponseResult;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
public interface UploadService {
    ResponseResult uploadImg(MultipartFile img);
}
