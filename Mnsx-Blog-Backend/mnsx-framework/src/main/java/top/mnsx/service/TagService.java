package top.mnsx.service;

import top.mnsx.domain.ResponseResult;
import top.mnsx.domain.dto.TagDto;
import top.mnsx.domain.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;
import top.mnsx.domain.vo.PageVo;

/**
* @author Mnsx_x
* @description 针对表【blog_tag(标签)】的数据库操作Service
* @createDate 2022-11-30 17:05:09
*/
public interface TagService extends IService<Tag> {

    ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagDto tagDto);

    ResponseResult addTag(TagDto tagDto);

    ResponseResult removeTag(Long id);

    ResponseResult getTagInfo(Long id);

    ResponseResult modifyTag(Tag tag);

    ResponseResult listAllTag();
}
