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
public class UserInfoVo {
    private String avatar;
    private String email;
    private Long id;
    private String nickName;
    private String sex;
}
