package chunson.cc.carket.controller;

import chunson.cc.carket.Exception.*;
import chunson.cc.carket.model.Result;
import chunson.cc.carket.service.AccountService;
import chunson.cc.carket.service.MarketService;
import chunson.cc.carket.utils.TokenUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/market")
public class MarketController
{
    private final MarketService service;
    private final AccountService accountService;

    public MarketController(MarketService service, AccountService accountService)
    {
        this.service = service;
        this.accountService = accountService;
    }

    @GetMapping("/assets")
    public Result<?> getAssets(@RequestParam Map<String, String> req)
    {
        if (!req.containsKey("page") || !req.containsKey("num"))
            return new Result<>(HttpStatus.BAD_REQUEST);

        int page, num;

        try
        {
            page = Integer.parseInt(req.get("page"));
            num = Integer.parseInt(req.get("num"));
        }
        catch (NumberFormatException e)
        {
            return new Result<>(HttpStatus.BAD_REQUEST);
        }

        List<Map<String, Object>> result = service.getAll(page, num);
        return new Result<>(result);
    }

    @PostMapping("/assets/{aid}/buy")
    public Result<?> buy(@CookieValue("token") String token, @PathVariable long aid, @RequestBody Map<String, String> req)
    {
        String me = TokenUtils.getAddress(token);
        String password = req.get("password");
        if (null == me)
        {
            Map<String, String> map = new HashMap<>();
            map.put("message", "没有token");
            return new Result<>(map, HttpStatus.UNAUTHORIZED);
        }
        else if (password == null)
        {
            Map<String, String> map = new HashMap<>();
            map.put("message", "没有密码");
            return new Result<>(map, HttpStatus.BAD_REQUEST);
        }
        else if (!accountService.checkAccount(me, password))
        {
            Map<String, String> map = new HashMap<>();
            map.put("message", "密码错误");
            return new Result<>(map, HttpStatus.UNAUTHORIZED);
        }

        try
        {
            service.buy(me, aid);
        }
        catch (AssetNotFoundException e)
        {
            return new Result<>(HttpStatus.NOT_FOUND);
        }
        catch (NotEnoughMoneyException e)
        {
            Map<String, String> map = new HashMap<>();
            map.put("message", "余额不足以购买这个艺术品");
            return new Result<>(map, HttpStatus.FORBIDDEN);
        }
        catch (TransferVNTException e)
        {
            Map<String, String> map = new HashMap<>();
            map.put("message", "转账失败");
            return new Result<>(map, HttpStatus.FORBIDDEN);
        }

        return new Result<>(HttpStatus.CREATED);
    }
}
