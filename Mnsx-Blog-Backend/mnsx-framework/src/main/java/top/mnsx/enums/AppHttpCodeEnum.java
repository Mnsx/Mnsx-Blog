package top.mnsx.enums;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
public enum AppHttpCodeEnum {
    // 成功
    SUCCESS(200,"操作成功"),
    // 登录
    NEED_LOGIN(401,"需要登录后操作"),
    NO_OPERATOR_AUTH(403,"无权限操作"),
    SYSTEM_ERROR(500,"出现错误"),
    USERNAME_EXIST(501,"用户名已存在"),
    PHONENUMBER_EXIST(502,"手机号已存在"), EMAIL_EXIST(503, "邮箱已存在"),
    REQUIRE_USERNAME(504, "必需填写用户名"),
    LOGIN_ERROR(505,"用户名或密码错误"),
    CONTENT_NOT_NULL(506, "评论内容不能为空"),
    FILE_TYPE_ERROR(507, "文件类型错误，只能上传png或jpg文件"),
    FILE_CAN_NOT_EMPTY(508, "文件不能为空"),
    USERNAME_NOT_EXIST(509, "用户名不能为空"),
    NICKNAME_NOT_EXIST(510, "昵称不能为空"),
    PASSWORD_NOT_EXIST(511, "密码不能为空"),
    EMAIL_NOT_EXIST(512, "邮箱不能为空"),
    TAG_HAS_EXIST(513, "标签已存在"),
    TAG_NOT_EXIST(514, "标签不存在"),
    ARTICLE_NOT_EXIST(515, "文章不存在"),
    MENU_NOT_EXIST(516, "菜单不存在");
    int code;
    String msg;

    AppHttpCodeEnum(int code, String errorMessage){
        this.code = code;
        this.msg = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
