package top.mnsx.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.mnsx.domain.entity.Menu;

import java.util.List;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouterVo {
    private List<MenuVo> menus;
}
