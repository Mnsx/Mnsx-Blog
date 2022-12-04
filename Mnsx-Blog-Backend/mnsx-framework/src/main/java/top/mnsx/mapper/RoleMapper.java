package top.mnsx.mapper;

import org.apache.ibatis.annotations.Param;
import top.mnsx.domain.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author Mnsx_x
* @description 针对表【sys_role(角色信息表)】的数据库操作Mapper
* @createDate 2022-12-01 11:54:42
* @Entity top.mnsx.domain.entity.Role
*/
public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectRoleKeyByUserId(@Param("id") Long id);
}




