package chunson.cc.carket.service;

import chunson.cc.carket.mapper.AuditMapper;
import chunson.cc.carket.model.Admin;
import chunson.cc.carket.model.Asset;
import chunson.cc.carket.model.AuditRecord;
import chunson.cc.carket.model.ShowAsset;
import chunson.cc.carket.utils.PswUtils;
import chunson.cc.carket.utils.TokenUtils;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class AuditService
{
    @Autowired
    private AuditMapper mapper;

    private boolean checkAdmin(@NotNull String adminName, String password)
    {
        Admin admin = mapper.getAdminByName(adminName);
        if (admin != null && admin.getPswHash() != null)
            return PswUtils.checkPassword(password, admin.getPswHash());

        return false;
    }

    public String login(@NotNull String name, @NotNull String password)
    {
        if (isAdminExists(name) && checkAdmin(name, password))
        {
            return TokenUtils.generateToken(name);
        }

        return null;
    }

    public String updateToken(@NotNull String token)
    {
        if (TokenUtils.isTokenOK(token))
        {
            return TokenUtils.refreshToken(token);
        }

        return null;
    }

    public List<Map<String, String>> getAuditingAssets(@NotNull int page, int num)
    {
        List<ShowAsset> assets = mapper.selectAuditing((page-1)*num, num);
        List<Map<String, String>> maps = new ArrayList<>();
        for (ShowAsset obj : assets)
        {
            maps.add(obj.auditing());
        }
        return maps;
    }

    public List<AuditRecord> getRecords(@NotNull String name, int page, int num)
    {
        return mapper.selectRecord(name, (page-1)*num, num);
    }

    public void addAudits(List<AuditRecord> records)
    {
        for (AuditRecord record : records)
        {
            mapper.updateAssetState(record);
            mapper.insertRecord(record);
        }
    }

    public boolean isAdminExists(String name)
    {
        return mapper.existsAdmin(name);
    }
}
