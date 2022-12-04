package top.mnsx.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.mnsx.enums.AppHttpCodeEnum;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemException extends RuntimeException {
    private int code;
    private String msg;

    public SystemException(AppHttpCodeEnum httpCodeEnum) {
        super(httpCodeEnum.getMsg());
        this.code = httpCodeEnum.getCode();
        this.msg = httpCodeEnum.getMsg();
    }
}
