package chunson.cc.carket.mapper;

import chunson.cc.carket.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserMapper
{
    @Select("SELECT * FROM `User` WHERE Address = #{address};")
    User getUserByAddress(String address);

    @Update("UPDATE `User` SET Username = #{username}, AvatarRoute = #{avatarRoute}, CoverRoute = #{coverRoute}, Email = #{email}, bio = #{bio} WHERE Address = #{address};")
    boolean updateUser(User user);

//    @Update("UPDATE Account SET PswHash = #{pswHash} WHERE Address = #{address};")
//    boolean updatePswHash(String address, String pswHash);
}
