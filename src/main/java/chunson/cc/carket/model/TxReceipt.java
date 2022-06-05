package chunson.cc.carket.model;

import java.util.List;

public class TxReceipt
{
    private String blockHash;
    private Long blockNumber;
    private String contractAddress;
    private Long cumulativeGasUsed;
    private String from;
    private Long gasUsed;
    private List<Object> logs;
    private String logsBloom;
    private String status;
    private String to;
    private String transactionHash;
    private Integer transactionIndex;

    public TxReceipt()
    {
    }

    public TxReceipt(String blockHash, Long blockNumber, String contractAddress, Long cumulativeGasUsed, String from, Long gasUsed, List<Object> logs, String logsBloom, String status, String to, String transactionHash, Integer transactionIndex)
    {
        this.blockHash = blockHash;
        this.blockNumber = blockNumber;
        this.contractAddress = contractAddress;
        this.cumulativeGasUsed = cumulativeGasUsed;
        this.from = from;
        this.gasUsed = gasUsed;
        this.logs = logs;
        this.logsBloom = logsBloom;
        this.status = status;
        this.to = to;
        this.transactionHash = transactionHash;
        this.transactionIndex = transactionIndex;
    }

    public String getBlockHash()
    {
        return blockHash;
    }

    public void setBlockHash(String blockHash)
    {
        this.blockHash = blockHash;
    }

    public Long getBlockNumber()
    {
        return blockNumber;
    }

    public void setBlockNumber(Long blockNumber)
    {
        this.blockNumber = blockNumber;
    }

    public String getContractAddress()
    {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress)
    {
        this.contractAddress = contractAddress;
    }

    public Long getCumulativeGasUsed()
    {
        return cumulativeGasUsed;
    }

    public void setCumulativeGasUsed(Long cumulativeGasUsed)
    {
        this.cumulativeGasUsed = cumulativeGasUsed;
    }

    public String getFrom()
    {
        return from;
    }

    public void setFrom(String from)
    {
        this.from = from;
    }

    public Long getGasUsed()
    {
        return gasUsed;
    }

    public void setGasUsed(Long gasUsed)
    {
        this.gasUsed = gasUsed;
    }

    public List<Object> getLogs()
    {
        return logs;
    }

    public void setLogs(List<Object> logs)
    {
        this.logs = logs;
    }

    public String getLogsBloom()
    {
        return logsBloom;
    }

    public void setLogsBloom(String logsBloom)
    {
        this.logsBloom = logsBloom;
    }


    public String getTo()
    {
        return to;
    }

    public void setTo(String to)
    {
        this.to = to;
    }

    public String getTransactionHash()
    {
        return transactionHash;
    }

    public void setTransactionHash(String transactionHash)
    {
        this.transactionHash = transactionHash;
    }

    public Integer getTransactionIndex()
    {
        return transactionIndex;
    }

    public void setTransactionIndex(Integer transactionIndex)
    {
        this.transactionIndex = transactionIndex;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }
}
