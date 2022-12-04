package top.mnsx.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleVo {
    private Long id;
    private String roleKey;
    private String roleName;
    private Integer roleSort;
    private String status;
}
