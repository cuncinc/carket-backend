package chunson.cc.carket.service;

import chunson.cc.carket.mapper.FavoriteMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService
{
    private final FavoriteMapper mapper;

    public FavoriteService(FavoriteMapper mapper)
    {
        this.mapper = mapper;
    }

    public boolean addFavorite(String address, long aid)
    {
        return mapper.insertFavorite(address, aid);
    }

    public void unFavorite(String address, long aid)
    {
        mapper.deleteFavorite(address, aid);
    }

    public boolean getFavorite(String address, long aid)
    {
        return mapper.selectFavorite(address, aid);
    }

    public int getFavoriteCount(long aid)
    {
        return mapper.selectFavoriteCount(aid);
    }
}
