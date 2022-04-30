package chunson.cc.carket.utils;

import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class IpfsUtils
{
    private static String url;
    private static IPFS ipfs;

    public IpfsUtils(@Value("${ipfs.url}") String url)
    {
        IpfsUtils.url = url;
    }

    private static IPFS getInstance()
    {
        if (ipfs == null)
        {
            ipfs = new IPFS(url);
        }
        return ipfs;
    }

    public static String upload(File file) throws IOException
    {
        NamedStreamable.FileWrapper savefile = new NamedStreamable.FileWrapper(file);
        MerkleNode result = getInstance().add(savefile).get(0);
        return result.hash.toString();
    }

    public static String upload(byte[] data) throws IOException
    {
        NamedStreamable.ByteArrayWrapper file = new NamedStreamable.ByteArrayWrapper(data);
        MerkleNode addResult = getInstance().add(file).get(0);
        return addResult.hash.toString();
    }
}
