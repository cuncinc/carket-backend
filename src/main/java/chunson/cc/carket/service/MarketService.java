package chunson.cc.carket.service;

import chunson.cc.carket.mapper.EventMapper;
import chunson.cc.carket.mapper.MarketMapper;
import chunson.cc.carket.mapper.UserMapper;
import chunson.cc.carket.model.*;
import chunson.cc.carket.utils.VNTUtils;
import com.sun.istack.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MarketService
{
    private final EventMapper eventMapper;
    private final UserMapper userMapper;
    private final VNTUtils vntUtils;

    private final MarketMapper mapper;

    public MarketService(EventMapper eventMapper, UserMapper userMapper, VNTUtils vntUtils, MarketMapper mapper)
    {
        this.eventMapper = eventMapper;
        this.userMapper = userMapper;
        this.vntUtils = vntUtils;
        this.mapper = mapper;
    }

    public List<Map<String, Object>> getAll(@NotNull int page, int num)
    {
        List<ShowAsset> assets = mapper.selectAllAsset((page - 1) * num, num);
        List<Map<String, Object>> maps = new ArrayList<>();
        if (assets != null)
        {
            for (ShowAsset asset : assets)
            {
                if (asset.getCreator() != null)
                {
                    User creator = userMapper.getUserByAddress(asset.getCreator());
                    asset.setCreatorName(creator.getUsername());
                    asset.setCreatorAvatarRoute(creator.getAvatarRoute());
                }

                if (asset.getOwner() != null)
                {
                    User owner = userMapper.getUserByAddress(asset.getOwner());
                    asset.setOwnerName(owner.getUsername());
                    asset.setOwnerAvatarRoute(owner.getAvatarRoute());
                }
            }
            for (ShowAsset obj : assets)
            {
                maps.add(obj.marketing());
            }
        }
        return maps;
    }
}