package top.mnsx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.util.StringUtils;
import top.mnsx.domain.ResponseResult;
import top.mnsx.domain.dto.TagDto;
import top.mnsx.domain.entity.Tag;
import top.mnsx.domain.vo.PageVo;
import top.mnsx.domain.vo.TagVo;
import top.mnsx.enums.AppHttpCodeEnum;
import top.mnsx.exception.SystemException;
import top.mnsx.service.TagService;
import top.mnsx.mapper.TagMapper;
import org.springframework.stereotype.Service;
import top.mnsx.utils.BeanCopyUtil;

import java.util.List;

/**
* @author Mnsx_x
* @description 针对表【blog_tag(标签)】的数据库操作Service实现
* @createDate 2022-11-30 17:05:09
*/
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
    implements TagService{

    @Override
    public ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagDto tagDto) {
        // 分页查询
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasText(tagDto.getName()), Tag::getName, tagDto.getName());
        wrapper.eq(StringUtils.hasText(tagDto.getRemark()), Tag::getRemark, tagDto.getRemark());
        Page<Tag> page = new Page<>(pageNum, pageSize);
        page(page, wrapper);
        // 封装数据返回
        PageVo pageVo = new PageVo(page.getRecords(), page.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addTag(TagDto tagDto) {
        // 判断标签名称不能重复
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Tag::getName, tagDto.getName());
        Tag tag = getOne(wrapper);
        if (tag != null) {
            throw new SystemException(AppHttpCodeEnum.TAG_HAS_EXIST);
        }
        // 保存
        tag = BeanCopyUtil.copyBean(tagDto, Tag.class);
        save(tag);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult removeTag(Long id) {
        // 判断标签是否存在
        if (!ifTagExist(id)) {
            throw new SystemException(AppHttpCodeEnum.TAG_NOT_EXIST);
        }

        // 删除对应标签
        removeById(id);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getTagInfo(Long id) {
        // 判断标签是否存在
        if (!ifTagExist(id)) {
            throw new SystemException(AppHttpCodeEnum.TAG_NOT_EXIST);
        }

        // 获取标签信息
        Tag tag = getById(id);

        return ResponseResult.okResult(tag);
    }

    @Override
    public ResponseResult modifyTag(Tag tag) {
        // 判断标签是否存在
        if (!ifTagExist(tag.getId())) {
            throw new SystemException(AppHttpCodeEnum.TAG_NOT_EXIST);
        }

        // 修改标签
        updateById(tag);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listAllTag() {
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Tag::getId, Tag::getName);
        List<Tag> list = list(wrapper);
        List<TagVo> tagVos = BeanCopyUtil.copyBeanList(list, TagVo.class);
        return ResponseResult.okResult(tagVos);
    }

    private boolean ifTagExist(Long id) {
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Tag::getId, id);
        Tag tag = getOne(wrapper);
        if (tag == null) {
            return false;
        }
        return true;
    }
}