package chunson.cc.carket.mapper;

import chunson.cc.carket.model.Event;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface EventMapper
{
    @Insert("INSERT INTO `Event`(`From`, `To`, TokenId, Amount, Type, TxHash) VALUES(#{from}, #{to}, #{tokenId}, #{amount}, #{type}, #{txHash});")
    public boolean insertEventWithTxHash(Event event);  //不能重名，否则启动失败

    @Insert("INSERT INTO `Event`(`From`, `To`, TokenId, Amount, Type) VALUES(#{from}, #{to}, #{tokenId}, #{amount}, #{type});")
    public void insertEvent(String type, String from, String to, long tokenId, Double amount);

    @Select("SELECT * FROM `Event` WHERE TokenId=#{tokenId} ORDER BY `Timestamp` DESC;")
    public List<Event> selectEventsOfToken(long tokenId);
}
