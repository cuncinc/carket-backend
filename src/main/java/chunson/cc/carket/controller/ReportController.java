package chunson.cc.carket.controller;

import chunson.cc.carket.model.Report;
import chunson.cc.carket.model.Result;
import chunson.cc.carket.service.ReportService;
import chunson.cc.carket.utils.TokenUtils;
import com.mysql.cj.util.StringUtils;
import org.apache.ibatis.annotations.Update;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reports")
public class ReportController
{
    private final ReportService service;

    public ReportController(ReportService service)
    {
        this.service = service;
    }

    @PostMapping("")
    public Result<?> addFavorite(@CookieValue("token") String token, @RequestBody Map<String, Object> req)
    {
        String why = req.get("why").toString();
        Long tokenId = Long.parseLong(req.get("tokenId").toString());

        if (StringUtils.isNullOrEmpty(why))
        {
            return new Result<>(HttpStatus.BAD_REQUEST);
        }

        String me = TokenUtils.getAddress(token);
        if (null == me)
        {
            return new Result<>(HttpStatus.UNAUTHORIZED);
        }


        if (service.addReport(me, tokenId, why))
        {
            return new Result<>(HttpStatus.CREATED);
        }
        else
        {
            return new Result<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("")
    public Result<?> getTodoReports(@CookieValue("adminToken") String adminToken)
    {
        String admin = TokenUtils.getAddress(adminToken);
        if (null == admin)
        {
            return new Result<>(HttpStatus.UNAUTHORIZED);
        }

        List<Report> reports = service.getTodoReports();
        return new Result<>(reports);
    }

    @PutMapping("/{rid}")
    public Result<?> dealReport(@CookieValue("adminToken") String adminToken, @PathVariable long rid, @RequestBody Map<String, Object> req)
    {
//        req.get("hello");

        String admin = TokenUtils.getAddress(adminToken);
        if (null == admin)
        {
            return new Result<>(HttpStatus.UNAUTHORIZED);
        }

        //todo

        service.updateReport(rid);

        return new Result<>();
    }
}
