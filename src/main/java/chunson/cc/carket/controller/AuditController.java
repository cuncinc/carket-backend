package chunson.cc.carket.controller;

import chunson.cc.carket.model.AuditRecord;
import chunson.cc.carket.model.Result;
import chunson.cc.carket.service.AuditService;
import chunson.cc.carket.utils.TokenUtils;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/audit")
public class AuditController
{
    @Autowired
    private AuditService service;

    @PostMapping("/session")
    public Result<?> login(@RequestBody Map<String, String> req)
    {
        @NotNull String name = req.get("adminName");
        @NotNull String password = req.get("password");

        String token = service.login(name, password);

        if (token != null)
        {
            Map<String, String> map = new HashMap<>();
            map.put("token", token);
            return new Result<>(map, HttpStatus.CREATED);
        }

        return new Result<>(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/session")
    public Result<?> updateToken(@NotNull @CookieValue("token") String token)
    {
        if (token.equals(""))
            return new Result<>(HttpStatus.BAD_REQUEST);

        String newToken = service.updateToken(token);
        if (newToken != null)
        {
            Map<String, String> map = new HashMap<>();
            map.put("newToken", newToken);
            return new Result<>(map);
        }
        return new Result(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/session")
    public Result<?> checkToken(@NotNull @CookieValue("token") String token)
    {
        if (TokenUtils.isTokenOK(token))
            return new Result<>();

        return new Result(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/todo")
    public Result<?> getTodoAuditing(@NotNull @CookieValue("token") String token, @RequestParam Map<String, String> req)
    {
        if (token.equals("") || !req.containsKey("page") || !req.containsKey("num"))
            return new Result<>(HttpStatus.BAD_REQUEST);
        else if (!TokenUtils.isTokenOK(token))
            return new Result<>(HttpStatus.UNAUTHORIZED);

        String adminName = TokenUtils.getAddress(token);
        if (!service.isAdminExists(adminName))
            return new Result<>(HttpStatus.UNAUTHORIZED);

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

        List<Map<String, String>> maps = service.getAuditingAssets(page, num);
        return new Result<>(maps);
    }

    @PostMapping("")
    public Result<?> addAudit(@RequestBody List<AuditRecord> req, @NotNull @CookieValue("token") String token)
    {
        if (token.equals(""))
            return new Result<>(HttpStatus.BAD_REQUEST);
        else if (!TokenUtils.isTokenOK(token))
            return new Result<>(HttpStatus.UNAUTHORIZED);

        String adminName = TokenUtils.getAddress(token);
        if (!service.isAdminExists(adminName))
            return new Result<>(HttpStatus.UNAUTHORIZED);

        for (AuditRecord record : req)
        {
            record.setAdminName(adminName);
        }

        service.addAudits(req);

        return new Result<>(HttpStatus.CREATED);
    }

    @GetMapping("/records")
    public Result<?> getRecords(@NotNull @CookieValue("token") String token, @RequestParam Map<String, String> req)
    {
        if (token.equals("") || !req.containsKey("page") || !req.containsKey("num"))
            return new Result<>(HttpStatus.BAD_REQUEST);
        else if (!TokenUtils.isTokenOK(token))
            return new Result<>(HttpStatus.UNAUTHORIZED);

        String adminName = TokenUtils.getAddress(token);
        if (!service.isAdminExists(adminName))
            return new Result<>(HttpStatus.UNAUTHORIZED);

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

        List<AuditRecord> records = service.getRecords(adminName, page, num);
        return new Result<>(records);
    }
}
