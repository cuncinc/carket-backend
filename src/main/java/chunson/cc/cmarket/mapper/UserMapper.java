package chunson.cc.cmarket.mapper;

import chunson.cc.cmarket.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMapper
{
    @Select("SELECT * FROM `user` WHERE UserId = #{userId}")
    User getUserById(Long userId);


    @Select("SELECT * FROM `user` WHERE PhoneNo = #{phone}")
    User getUserByPhone(String phone);

    @Update("UPDATE `user` SET AvatarKey = #{key} WHERE UserId = #{userId}")
    boolean updateAvatarKey(long userId, String key);
}
