package top.mnsx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.mnsx.domain.ResponseResult;
import top.mnsx.domain.entity.Role;
import top.mnsx.domain.vo.PageVo;
import top.mnsx.domain.vo.RoleVo;
import top.mnsx.service.RoleService;
import top.mnsx.mapper.RoleMapper;
import org.springframework.stereotype.Service;
import top.mnsx.utils.BeanCopyUtil;
import top.mnsx.utils.SecurityUtil;

import java.util.ArrayList;
import java.util.List;

/**
* @author Mnsx_x
* @description 针对表【sys_role(角色信息表)】的数据库操作Service实现
* @createDate 2022-12-01 11:54:42
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{

    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        // 判断是否为管理员，如果是，集合中只需要有admin
        if (SecurityUtil.isAdmin()) {
            List<String> roleKeys = new ArrayList<>();
            roleKeys.add("admin");
            return roleKeys;
        }
        // 否则查询当前用户具有的角色
        return getBaseMapper().selectRoleKeyByUserId(id);
    }

    @Override
    public ResponseResult pageAll(Integer pageNum, Integer pageSize, String roleName, String status) {
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(roleName != null, Role::getRoleName, roleName);
        wrapper.eq(status != null, Role::getStatus, status);
        Page<Role> page = new Page<>(pageNum, pageSize);
        Page<Role> page1 = page(page, wrapper);
        List<Role> records = page1.getRecords();
        List<RoleVo> roleVos = BeanCopyUtil.copyBeanList(records, RoleVo.class);
        PageVo pageVo = new PageVo(roleVos, page1.getTotal());
        return ResponseResult.okResult(pageVo);
    }
}




