package chunson.cc.carket.controller;

import chunson.cc.carket.model.Result;
import chunson.cc.carket.service.FavoriteService;
import chunson.cc.carket.utils.TokenUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/favorites")
public class FavoriteController
{
    private final FavoriteService service;

    public FavoriteController(FavoriteService service)
    {
        this.service = service;
    }

    @PostMapping("/{aid}")
    public Result<?> addFavorite(@CookieValue("token") String token, @PathVariable long aid)
    {
        String me = TokenUtils.getAddress(token);
        if (null == me)
        {
            return new Result<>(HttpStatus.UNAUTHORIZED);
        }

        if (service.addFavorite(me, aid))
        {
            Map<String, Object> map = new HashMap<>();
            map.put("aid", aid);
            map.put("count", service.getFavoriteCount(aid));
            return new Result<>(map, HttpStatus.CREATED);
        }
        else
        {
            return new Result<>(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{aid}")
    public Result<?> deleteFavorite(@CookieValue("token") String token, @PathVariable long aid)
    {
        String me = TokenUtils.getAddress(token);
        if (null == me)
        {
            return new Result<>(HttpStatus.UNAUTHORIZED);
        }

        service.unFavorite(me, aid);

        Map<String, Object> map = new HashMap<>();
        map.put("aid", aid);
        map.put("count", service.getFavoriteCount(aid));
        return new Result<>(map);
    }

    @GetMapping("/{aid}/{address}")
    public Result<?> isMeFavorite(@PathVariable long aid, @PathVariable String address)
    {

        Map<String, Object> map = new HashMap<>();
        map.put("aid", aid);
        map.put("address", address);

        if (service.getFavorite(address, aid))
        {
            map.put("favorite", true);
        }
        else
        {
            map.put("favorite", false);
        }

        return new Result<>(map);
    }

    @GetMapping("/{aid}/count")
    public Result<?> getFavoriteCount(@PathVariable long aid)
    {
        Map<String, Object> map = new HashMap<>();
        map.put("aid", aid);
        map.put("count", service.getFavoriteCount(aid));

        return new Result<>(map);
    }
}
