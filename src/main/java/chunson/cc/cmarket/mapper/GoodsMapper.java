package chunson.cc.cmarket.mapper;

import chunson.cc.cmarket.model.Goods;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GoodsMapper
{
//    enum Order
//    {
//        ASC,
//        DESC;
//    }
//
//    @Select("SELECT * FROM goods ORDER BY #{orderBy} #{order}")
//    List<Goods> getAllGoods(String orderBy, Order order);

    @Select("SELECT * FROM goods")
    List<Goods> getAllGoods();

    @Select("SELECT * FROM goods WHERE SellerId = #{sellerId}")
    List<Goods> getGoodsBySeller(long sellerId);

    @Select("SELECT * FROM goods WHERE GoodsId = #{goodsId}")
    Goods getGoodsById(long goodsId);

    @Select("SELECT * FROM goods WHERE Category = #{category}")
    List<Goods> getGoodsByCategory(String category);

    @Insert("INSERT INTO goods ( SellerId, GoodsDesc, PicKey, Price, Category, ReleaseTime ) " +
            "VALUES (#{sellerId}, #{goodsDesc}, #{picKey}, #{price}, #{category}, NOW())")
    @Options(useGeneratedKeys=true, keyProperty="goodsId")
    boolean insertGoods(Goods goods);

    @Update("UPDATE goods SET PicKey = #{picKey} WHERE GoodsId = #{goodsId}")
    boolean updatePicKey(Goods goods);

    @Update("UPDATE goods SET GoodsDesc = #{goodsDesc}, Price=#{price}, Category=#{category} WHERE GoodsId = #{goodsId}")
    boolean updateInfo(Goods goods);
}
