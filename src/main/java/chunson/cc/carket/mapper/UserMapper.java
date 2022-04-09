//package chunson.cc.carket.mapper;
//
//import chunson.cc.carket.model.User;
//import org.apache.ibatis.annotations.Insert;
//import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Select;
//import org.apache.ibatis.annotations.Update;
//
//@Mapper
//public interface UserMapper
//{
//    @Select("SELECT * FROM `user` WHERE UserId = #{userId}")
//    User getUserById(Long userId);
//
//    @Select("SELECT * FROM `user` WHERE PhoneNo = #{phone}")
//    User getUserByPhone(String phone);
//
//    @Update("UPDATE `user` SET AvatarKey = #{key} WHERE UserId = #{userId}")
//    boolean updateAvatarKey(long userId, String key);
//
//    @Insert("INSERT INTO `user`(UserName, `Password`, PhoneNo) VALUES(#{username}, #{password}, #{phone})")
//    boolean insertUser(String username, String password, String phone);
//
//    @Update("UPDATE `user` SET UserName = #{username}, QQNo = #{qq}, WechatId = #{wechatId} WHERE UserId = #{userId}")
//    boolean updateInfo(long userId, String username, String qq, String wechatId);
//}
