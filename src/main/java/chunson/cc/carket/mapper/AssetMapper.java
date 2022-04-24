package chunson.cc.carket.mapper;

import chunson.cc.carket.model.Asset;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface AssetMapper
{
    @Select("SELECT * FROM `Asset` WHERE AId = #{aId};")
    Asset getAssetById(String aId);

    @Insert("INSERT INTO Asset ( `Name`, Type, `Desc`, Creator, Route, `Hash`, Label ) VALUES ( #{name}, #{type}, #{desc}, #{creator}, #{route}, #{hash}, #{label} );")
    boolean insertAsset (Asset asset);

//    List<Asset> getAssetList();

//    @Update("UPDATE Account SET PswHash = #{pswHash} WHERE Address = #{address};")
//    boolean updatePswHash(String address, String pswHash);
}
