package chunson.cc.carket.model;

public class TxResult
{
    private String txHash;
    private Object data;

    public TxResult()
    {
    }

    public TxResult(String txHash, Object data)
    {
        this.txHash = txHash;
        this.data = data;
    }

    public String getTxHash()
    {
        return txHash;
    }

    public void setTxHash(String txHash)
    {
        this.txHash = txHash;
    }

    public Object getData()
    {
        return data;
    }

    public void setData(Object data)
    {
        this.data = data;
    }
}
