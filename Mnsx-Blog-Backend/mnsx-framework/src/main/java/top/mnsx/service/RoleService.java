package top.mnsx.service;

import top.mnsx.domain.ResponseResult;
import top.mnsx.domain.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Mnsx_x
* @description 针对表【sys_role(角色信息表)】的数据库操作Service
* @createDate 2022-12-01 11:54:42
*/
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long id);

    ResponseResult pageAll(Integer pageNum, Integer pageSize, String roleName, String status);
}
