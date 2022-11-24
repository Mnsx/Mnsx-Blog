package top.mnsx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.mnsx.domain.entity.User;
import top.mnsx.service.UserService;
import top.mnsx.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author Mnsx_x
* @description 针对表【sys_user(用户表)】的数据库操作Service实现
* @createDate 2022-11-23 17:27:09
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




