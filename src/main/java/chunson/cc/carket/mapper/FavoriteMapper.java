package chunson.cc.carket.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface FavoriteMapper
{
    @Insert("INSERT INTO Favorite(Address, AId) VALUES(#{address}, #{aid});")
    public boolean insertFavorite(String address, long aid);

    @Delete("DELETE FROM Favorite WHERE Address=#{address} AND AId = #{aid};")
    public void deleteFavorite(String address, long aid);

    @Select("SELECT ifnull((SELECT 1 FROM Favorite WHERE Address = #{address} AND Aid = #{aid} LIMIT 1 ), 0) AS R;")
    public boolean selectFavorite(String address, long aid);

    @Select("SELECT COUNT(*) FROM Favorite WHERE Aid = #{aid};")
    public int selectFavoriteCount(long aid);

    @Select("SELECT Address FROM Favorite WHERE Aid = #{aid};")
    public List<String> selectFavoriteAddress(long aid);

    @Select("SELECT Aid FROM Favorite WHERE Address = #{address};")
    public List<Integer> selectFavoriteAid(String address);
}
