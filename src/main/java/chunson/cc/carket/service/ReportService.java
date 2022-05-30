package chunson.cc.carket.service;

import chunson.cc.carket.mapper.AssetMapper;
import chunson.cc.carket.mapper.ReportMapper;
import chunson.cc.carket.model.Report;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService
{
    private final ReportMapper reportMapper;

    private final AssetMapper assetMapper;

    public ReportService(AssetMapper assetMapper, ReportMapper reportMapper)
    {
        this.assetMapper = assetMapper;
        this.reportMapper = reportMapper;
    }

    public List<Report> getTodoReports()
    {
        return reportMapper.selectTodoReports();
    }

    public boolean addReport(String from, long tokenId, String why)
    {
        return reportMapper.insertReport(from, tokenId, why);
    }

    public boolean updateReport(long rid)
    {
        //todo
        Report report = reportMapper.selectReport(rid);
        return reportMapper.updateReport(rid);
    }
}
