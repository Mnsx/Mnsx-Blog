package top.mnsx.service;

import top.mnsx.domain.ResponseResult;
import top.mnsx.domain.entity.Link;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Mnsx_x
* @description 针对表【blog_link(友链)】的数据库操作Service
* @createDate 2022-11-23 17:10:50
*/
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();
}
