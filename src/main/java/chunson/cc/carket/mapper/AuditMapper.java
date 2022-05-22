package chunson.cc.carket.mapper;

import chunson.cc.carket.model.Admin;
import chunson.cc.carket.model.AuditRecord;
import chunson.cc.carket.model.AuditAsset;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface AuditMapper
{
    @Select("SELECT ifnull((SELECT 1 FROM Admin WHERE AdminName=#{name} LIMIT 1 ), 0) AS R;")
    boolean existsAdmin(String name);

    @Insert("INSERT INTO AuditRecord(AdminName, AId, State, Reason) VALUES(#{adminName}, #{aid}, #{state}, #{reason});")
    void insertRecord(AuditRecord record);

    @Update("UPDATE Asset SET State=#{state} WHERE State=\"待审核\" AND AId = #{aid};")
    void updateAssetState(AuditRecord record);

    @Select("SELECT * FROM Admin WHERE AdminName = #{adminName};")
    Admin getAdminByName(String adminName);

    @Update("UPDATE Admin SET PswHash = #{pswHash} WHERE AdminName = #{adminName}")
    boolean updatePswHash(String adminName, String pswHash);

    @Select("SELECT Asset.*, `User`.AvatarRoute, `User`.Username AS CreatorName FROM `User`, Asset WHERE `User`.Address = Asset.Creator AND State = \"待审核\" LIMIT #{start}, #{num};")
    List<AuditAsset> selectAuditing(int start, int num);

    @Select("SELECT * FROM AuditRecord WHERE AdminName = #{name} LIMIT #{start}, #{num};")
    List<AuditRecord> selectRecord(String name, int start, int num);
}
