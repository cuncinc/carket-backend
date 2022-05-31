package chunson.cc.carket.model;

public class TxResult
{
    private String txHash;
    private Integer amount;

    public TxResult(String txHash, Integer amount)
    {
        this.txHash = txHash;
        this.amount = amount;
    }

    public TxResult()
    {
    }

    public String getTxHash()
    {
        return txHash;
    }

    public void setTxHash(String txHash)
    {
        this.txHash = txHash;
    }

    public Integer getAmount()
    {
        return amount;
    }

    public void setAmount(Integer amount)
    {
        this.amount = amount;
    }
}
