package chunson.cc.carket.mapper;

import chunson.cc.carket.model.Asset;
import chunson.cc.carket.model.ShowAsset;
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
    @Select("SELECT ifnull((SELECT 1 FROM Asset WHERE AId=#{aid} AND Creator = #{creator} LIMIT 1 ), 0) AS R;")
    boolean checkAidCreator(int aid, String creator);

    @Select("SELECT ifnull((SELECT 1 FROM Asset WHERE TokenId=#{tokenId} AND `Owner` = #{owner} LIMIT 1 ), 0) AS R;")
    boolean checkTokenIdOwner(int tokenId, String owner);

    @Select("SELECT * FROM `Asset` WHERE AId = #{aid};")
    Asset getAssetByAid(int aid);

    @Insert("INSERT INTO Asset ( `Name`, Type, `Desc`, Creator, Route, `Hash`, Label ) VALUES ( #{name}, #{type}, #{desc}, #{creator}, #{route}, #{hash}, #{label} );")
    boolean insertAsset (Asset asset);

    @Update("UPDATE Asset SET TokenId=#{tokenId},JsonCId=#{jsonCid},`Owner`=#{owner},Rate=#{rate},State=\"在链上\" WHERE AId=#{aid};")
    boolean mintAsset(Asset asset);

    @Update("UPDATE Asset SET State=#{state} WHERE AId=#{aid};")
    boolean updateState(int aid, String state);

    @Update("UPDATE Asset SET `Owner`=#{owner} WHERE AId=#{aid};")
    boolean updateOwner(int aid, String owner);

    @Update("UPDATE Asset SET Price=#{price} WHERE AId=#{aid};")
    boolean updatePrice(int aid, int price);

    @Select("SELECT Asset.*, `User`.AvatarRoute, `User`.Username AS OwnerName FROM `User`, Asset WHERE `User`.Address = Asset.Owner AND State = \"在流通\" LIMIT #{start}, #{num};")
    List<ShowAsset> selectAssets(int start, int num);

    @Select("SELECT Asset.*, `User`.AvatarRoute, `User`.Username AS OwnerName FROM `User`, Asset WHERE (`User`.Address = Asset.Owner OR `User`.Address = Asset.Creator) AND (Creator=#{me} OR Owner=#{me});")
    List<ShowAsset> myAssets(String me);


//    List<Asset> getAssetList();

//    @Update("UPDATE Account SET PswHash = #{pswHash} WHERE Address = #{address};")
//    boolean updatePswHash(String address, String pswHash);
}
