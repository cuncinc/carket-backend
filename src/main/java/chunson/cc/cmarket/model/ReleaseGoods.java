package chunson.cc.cmarket.model;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

public class ReleaseGoods extends Goods implements Serializable
{
    MultipartFile file; //文件信息

    public MultipartFile getFile()
    {
        return file;
    }

    public void setFile(MultipartFile file)
    {
        this.file = file;
    }
}
