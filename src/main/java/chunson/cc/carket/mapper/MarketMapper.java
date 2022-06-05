package chunson.cc.carket.mapper;

import chunson.cc.carket.model.ShowAsset;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface MarketMapper
{
    @Select("SELECT * FROM `Asset` WHERE State = \"在流通\" LIMIT #{start}, #{num};;")
    public List<ShowAsset> selectAllAsset(int start, int num);
}
