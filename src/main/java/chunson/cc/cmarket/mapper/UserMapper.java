package chunson.cc.cmarket.mapper;

import chunson.cc.cmarket.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper
{


    @Select("select * from user where UserId = #{userId}")
    User getUserById(String userId);




}
