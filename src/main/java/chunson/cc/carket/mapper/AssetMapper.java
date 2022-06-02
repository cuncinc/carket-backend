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
    @Insert("INSERT INTO Asset ( `Name`, Type, `Desc`, Creator, IpfsCid, `Hash`, Label ) VALUES ( #{name}, #{type}, #{desc}, #{creator}, #{ipfsCid}, #{hash}, #{label} );")
    boolean insertAsset(Asset asset);

    @Update("UPDATE Asset SET TokenId=#{tokenId},JsonCId=#{jsonCid},`Owner`=#{owner},Rate=#{rate},State=\"未流通\" WHERE AId=#{aid};")
    boolean mintAsset(Asset asset);

    @Update("UPDATE Asset SET State=#{state} WHERE AId=#{aid};")
    boolean updateState(Long aid, String state);

    @Update("UPDATE Asset SET `Owner`=#{owner} WHERE AId=#{aid};")
    boolean updateOwner(Long aid, String owner);

    @Update("UPDATE Asset SET `Name`=#{name} WHERE AId=#{aid};")
    boolean updateName(Long aid, String name);

    @Update("UPDATE Asset SET `Desc`=#{desc} WHERE AId=#{aid};")
    boolean updateDesc(Long aid, String desc);

    @Update("UPDATE Asset SET Price=#{price} WHERE AId=#{aid};")
    boolean updatePrice(Long aid, int price);

    @Select("SELECT Asset.*, `User`.AvatarRoute, `User`.Username AS OwnerName FROM `User`, Asset WHERE `User`.Address = Asset.Owner AND State = \"在流通\" LIMIT #{start}, #{num};")
    List<ShowAsset> selectAssets(int start, int num);

    @Select("SELECT Asset.*, `User`.AvatarRoute AS `OwnerAvatarRoute`, `User`.Username AS OwnerName FROM `User`, Asset WHERE `User`.Address = Asset.`Owner` AND Asset.`Owner`=#{address};")
    List<ShowAsset> selectOwnedAssets(String address);

    @Select("SELECT Asset.*, `User`.AvatarRoute AS `OwnerAvatarRoute`, `User`.Username AS OwnerName FROM `User`, Asset WHERE `User`.Address = Asset.`Owner` AND Asset.`Owner`=#{address} AND State=\"在流通\";")
    List<ShowAsset> selectOnSaleAssets(String address);

    @Select("SELECT Asset.*, `User`.AvatarRoute AS `CreatorAvatarRoute`, `User`.Username AS CreatorName FROM `User`, Asset WHERE `User`.Address = Asset.`Creator` AND Asset.`Creator`=#{address} AND TokenId IS NOT NULL;")
    List<ShowAsset> selectCreatedAssets(String address);

    @Select("SELECT Asset.*, `User`.AvatarRoute AS `CreatorAvatarRoute`, `User`.Username AS CreatorName FROM `User`, Asset WHERE `User`.Address = Asset.`Creator` AND Asset.`Creator`=#{address} AND TokenId IS NULL;")
    List<ShowAsset> selectAuditingAssets(String address);

    @Select("SELECT Asset.* FROM Asset, Favorite WHERE Favorite.Address = #{address} AND Asset.AId = Favorite.Aid;")
    List<ShowAsset> selectFavoriteAssets(String address);

    @Select("SELECT * From Asset WHERE AId = #{aid};")
    Asset selectOneAssetByAid(long aid);

    @Select("SELECT * From Asset WHERE TokenId = #{tokenId};")
    Asset selectOneAssetByTokenId(long tokenId);

    @Select("SELECT ifnull((SELECT 1 FROM Asset WHERE AId=#{aid} AND Creator = #{creator} LIMIT 1 ), 0) AS R;")
    boolean checkAidCreator(Long aid, String creator);

    @Select("SELECT ifnull((SELECT 1 FROM Asset WHERE TokenId=#{tokenId} AND `Owner` = #{owner} LIMIT 1 ), 0) AS R;")
    boolean checkTokenIdOwner(Long tokenId, String owner);

    @Select("SELECT * FROM `Asset` WHERE AId = #{aid};")
    Asset getAssetByAid(Long aid);


//    List<Asset> getAssetList();

//    @Update("UPDATE Account SET PswHash = #{pswHash} WHERE Address = #{address};")
//    boolean updatePswHash(String address, String pswHash);
}
