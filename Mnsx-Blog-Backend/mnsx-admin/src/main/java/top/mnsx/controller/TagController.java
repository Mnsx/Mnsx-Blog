package top.mnsx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.mnsx.domain.ResponseResult;
import top.mnsx.domain.dto.TagDto;
import top.mnsx.domain.entity.Tag;
import top.mnsx.domain.vo.PageVo;
import top.mnsx.service.TagService;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
@RequestMapping("/content/tag")
@RestController
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/list")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, TagDto tagDto) {
        return tagService.pageTagList(pageNum, pageSize, tagDto);
    }

    @GetMapping("/listAllTag")
    public ResponseResult listAllTag() {
        return tagService.listAllTag();
    }

    @PostMapping
    public ResponseResult addTag(@RequestBody TagDto tagDto) {
        return tagService.addTag(tagDto);
    }

    @DeleteMapping("/{id}")
    public ResponseResult removeTag(@PathVariable("id") Long id) {
        return tagService.removeTag(id);
    }

    @GetMapping("/{id}")
    public ResponseResult getTagInfo(@PathVariable("id") Long id) {
        return tagService.getTagInfo(id);
    }

    @PutMapping
    public ResponseResult modifyTag(@RequestBody Tag tag) {
        return tagService.modifyTag(tag);
    }
}
