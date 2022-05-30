package chunson.cc.carket.mapper;

import chunson.cc.carket.model.Report;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ReportMapper
{
    @Insert("INSERT INTO Report(`From`, TokenId, Why) VALUES(#{from}, #{tokenId}, #{why});")
    public boolean insertReport(String from, long tokenId, String why);

    @Update("UPDATE Report SET State=\"已处理\" WHERE Rid = #{rid};")
    public boolean updateReport(long rid);

    @Select("SELECT * FROM Report WHERE State = \"待处理\";")
    public List<Report> selectTodoReports();

    @Select("SELECT * FROM Report WHERE Rid = #{rid};")
    public Report selectReport(long rid);
}
