package top.mnsx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.mnsx.constants.SystemConstants;
import top.mnsx.domain.ResponseResult;
import top.mnsx.domain.entity.Link;
import top.mnsx.domain.vo.LinkVo;
import top.mnsx.service.LinkService;
import top.mnsx.mapper.LinkMapper;
import org.springframework.stereotype.Service;
import top.mnsx.utils.BeanCopyUtils;

import java.util.List;

/**
* @author Mnsx_x
* @description 针对表【blog_link(友链)】的数据库操作Service实现
* @createDate 2022-11-23 17:10:50
*/
@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link>
    implements LinkService{

    @Override
    public ResponseResult getAllLink() {
        // 查询所有审核通过的
        LambdaQueryWrapper<Link> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Link::getStatus, SystemConstants.Link_STATUS_NORMAL);
        List<Link> links = list(wrapper);
        // vo
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(links, LinkVo.class);
        // 返回
        return ResponseResult.okResult(linkVos);
    }
}




