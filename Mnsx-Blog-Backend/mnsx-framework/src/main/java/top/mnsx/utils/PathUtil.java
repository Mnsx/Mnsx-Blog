package top.mnsx.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
public class PathUtil {
    public static String generatePath(String fileName) {
        // 日期
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd/");
        String date = simpleDateFormat.format(new Date());
        // uuid
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        // 后缀名
        int index = fileName.lastIndexOf(".");
        String fileType = fileName.substring(index);

        return new StringBuilder().append(date).append(uuid).append(fileType).toString();
    }
}
