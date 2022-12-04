package top.mnsx.mapper;

import org.apache.ibatis.annotations.Param;
import top.mnsx.domain.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author Mnsx_x
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Mapper
* @createDate 2022-12-01 11:51:31
* @Entity top.mnsx.domain.entity.Menu
*/
public interface MenuMapper extends BaseMapper<Menu> {

    List<String> selectPermsByUserId(@Param("id") Long id);

    List<Menu> selectAllRouterMenu();

    List<Menu> selectRouterMenuTreeByUserId(Long userId);
}




