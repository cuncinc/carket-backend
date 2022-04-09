package chunson.cc.carket.mapper;

import chunson.cc.carket.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper
{
    @Select("SELECT * FROM `User` WHERE Address = #{address};")
    User getUserByAddress(String address);

//    @Update("UPDATE Account SET PswHash = #{pswHash} WHERE Address = #{address};")
//    boolean updatePswHash(String address, String pswHash);
}
