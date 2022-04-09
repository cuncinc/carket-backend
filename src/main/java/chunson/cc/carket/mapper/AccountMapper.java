package chunson.cc.carket.mapper;

import chunson.cc.carket.model.Account;
import org.apache.ibatis.annotations.*;

@Mapper
public interface AccountMapper
{
    @Insert("INSERT INTO Account(Address, UserName, PswHash, Salt) VALUES(#{address}, #{username}, #{pswHash}, #{salt});")
    boolean insertAccount(Account account);

    @Select("SELECT ifnull((SELECT 1 FROM Account WHERE Username=#{username} LIMIT 1 ), 0) AS R;")
    boolean existsAccount(String username);

    @Select("SELECT * FROM Account WHERE Username = #{username};")
    Account getAccountByName(String username);

    @Select("SELECT * FROM Account WHERE Address = #{address};")
    Account getAccountByAddress(String address);

    @Update("UPDATE Account SET PswHash = #{pswHash} WHERE Address = #{address};")
    boolean updatePswHash(String address, String pswHash);

    @Delete("DELETE FROM Account WHERE Address = #{address};")
    boolean deleteAccount(String address);
}
