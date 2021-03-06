package chunson.cc.carket.mapper;

import chunson.cc.carket.model.Account;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface AccountMapper
{
//    @Insert("INSERT INTO Account(Address, UserName, PswHash, Salt, WalletPsw, WalletName) VALUES(#{address}, #{username}, #{pswHash}, #{salt}, #{walletPsw}, #{walletName});")

    //    boolean insertAccount(Account account);
    @Insert("INSERT INTO Account(Address, UserName, PswHash, Salt, PrivateKey) VALUES(#{address}, #{username}, #{pswHash}, #{salt}, #{privateKey});")
    boolean insertAccount(Account account);

    @Delete("DELETE FROM Account WHERE Address = #{address};")
    boolean deleteAccount(String address);

    @Update("UPDATE Account SET PswHash = #{pswHash} WHERE Address = #{address};")
    boolean updatePswHash(String address, String pswHash);

    @Update("UPDATE Account SET Balance = Balance + #{price} WHERE Address = #{address};")
    boolean addBalance(String address, double price);

    @Update("UPDATE Account SET Balance = {price} WHERE Address = #{address};")
    boolean updateBalance(String address, double price);

    @Select("SELECT ifnull((SELECT 1 FROM Account WHERE Username=#{username} LIMIT 1 ), 0) AS R;")
    boolean existsAccount(String username);

    @Select("SELECT * FROM Account WHERE Username = #{username};")
    Account getAccountByName(String username);

    @Select("SELECT * FROM Account WHERE Address = #{address};")
    Account getAccountByAddress(String address);

    @Select("SELECT Balance FROM Account WHERE Address = #{address};")
    double getBalance(String address);
}
