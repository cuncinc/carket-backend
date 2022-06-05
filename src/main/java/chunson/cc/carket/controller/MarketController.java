package chunson.cc.carket.controller;

import chunson.cc.carket.Exception.CreatorNotCorrespondException;
import chunson.cc.carket.Exception.StateNotCorrespondException;
import chunson.cc.carket.model.Asset;
import chunson.cc.carket.model.Result;
import chunson.cc.carket.service.MarketService;
import chunson.cc.carket.utils.TokenUtils;
import com.mysql.cj.util.StringUtils;
import com.sun.istack.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/market")
public class MarketController
{
    private final MarketService service;

    public MarketController(MarketService service)
    {
        this.service = service;
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
}
