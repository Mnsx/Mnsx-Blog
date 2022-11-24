package top.mnsx.utils;

import org.springframework.beans.BeanUtils;
import top.mnsx.domain.entity.Article;
import top.mnsx.domain.vo.HotArticleVo;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
public class BeanCopyUtils {

    private BeanCopyUtils() {

    }

    public static <T> T copyBean(Object source, Class<T> clazz) {
        // 创建目标对象
        T result = null;
        try {
            result = clazz.newInstance();
            // 实现属性copy
            BeanUtils.copyProperties(source, result);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        // 返回结果
        return result;
    }

    public static <T, V> List<T> copyBeanList(List<V> source, Class<T> clazz) {
        return source.stream()
                .map(s -> copyBean(s, clazz))
                .collect(Collectors.toList());
    }
}
