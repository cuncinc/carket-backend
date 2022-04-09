//package chunson.cc.carket.model;
//
//import chunson.cc.carket.utils.config.CosConfig;
//import com.mysql.cj.util.StringUtils;
//
//import java.util.Date;
//
//public class Goods
//{
//    enum GoodState
//    {
//        un_sold,
//        on_selling,
//        be_sold,
//        be_deleted;
//    }
//
//    long goodsId;
//    long sellerId;
//    String goodsDesc;
////    private List<String> picKeyList;
////    private List<String> picUrlList;
//    String picKey;
//    String picUrl;
//    double price;
//    String category;
//    GoodState state;
//    Date releaseTime;
//
//    static String PIC_URL_PREFIX;
//
//
////    static
////    {
////        StringBuilder builder = new StringBuilder();
////        builder.append("https://")
////                .append(CosConfig.getBucketName())
////                .append(".cos.")
////                .append(CosConfig.getRegion())
////                .append(".myqcloud.com/goodsPic/");
////        System.out.println("bucketName: " + CosConfig.getBucketName() );
////
////        PIC_URL_PREFIX = builder.toString();
////    }
//
//    static void updatePrefix()
//    {
//        StringBuilder builder = new StringBuilder();
//        builder.append("https://")
//                .append(CosConfig.getBucketName())
//                .append(".cos.")
//                .append(CosConfig.getRegion())
//                .append(".myqcloud.com/goodsPic/");
//        PIC_URL_PREFIX = builder.toString();
//    }
//
//    public Goods() {}
//
//    public long getGoodsId()
//    {
//        return goodsId;
//    }
//
//    public void setGoodsId(long goodsId)
//    {
//        this.goodsId = goodsId;
//    }
//
//    public long getSellerId()
//    {
//        return sellerId;
//    }
//
//    public void setSellerId(long sellerId)
//    {
//        this.sellerId = sellerId;
//    }
//
//    public String getGoodsDesc()
//    {
//        return goodsDesc;
//    }
//
//    public void setGoodsDesc(String goodsDesc)
//    {
//        this.goodsDesc = goodsDesc;
//    }
//
////    public List<String> getPicKeyList()
////    {
////        return picKeyList;
////    }
////
////    public void setPicKeyList(List<String> picKeyList)
////    {
////        this.picKeyList = picKeyList;
////    }
////
////    public List<String> getPicUrlList()
////    {
////        updatePicUrlList();
////        return picUrlList;
////    }
////
////    public void updatePicUrlList()
////    {
////        if (null == picUrlList)
////        {
////            picUrlList = new ArrayList<>();
////        }
////        picUrlList.clear();
////        for (String key : picKeyList)
////        {
////            String ulr = PIC_URL_PREFIX + key;
////            picUrlList.add(ulr);
////        }
////    }
////
////    public void setPicUrlList(List<String> picUrlList)
////    {
////        this.picUrlList = picUrlList;
////    }
//
//
//    public String getPicKey()
//    {
//        return picKey;
//    }
//
//    public void setPicKey(String picKey)
//    {
//        this.picKey = picKey;
//    }
//
//    public String getPicUrl()
//    {
//        updatePicUrl();
//        return picUrl;
//    }
//
//    public void updatePicUrl()
//    {
//        if (!StringUtils.isNullOrEmpty(picKey))
//        {
//            updatePrefix();
//            picUrl = PIC_URL_PREFIX + picKey;
//        }
//    }
//
//    public void setPicUrl(String picUrl) {}
//
//    public double getPrice()
//    {
//        return price;
//    }
//
//    public void setPrice(double price)
//    {
//        this.price = price;
//    }
//
//    public String getCategory()
//    {
//        return category;
//    }
//
//    public void setCategory(String category)
//    {
//        this.category = category;
//    }
//
//    public GoodState getState()
//    {
//        return state;
//    }
//
//    public void setState(GoodState state)
//    {
//        this.state = state;
//    }
//
//    public Date getReleaseTime()
//    {
//        return releaseTime;
//    }
//
//    public void setReleaseTime(Date releaseTime)
//    {
//        this.releaseTime = releaseTime;
//    }
//
//    @Override
//    public String toString()
//    {
//        return "Goods{" +
//                "goodsId=" + goodsId +
//                ", sellerId=" + sellerId +
//                ", goodsDesc='" + goodsDesc + '\'' +
//                ", picKey='" + picKey + '\'' +
//                ", picUrl='" + picUrl + '\'' +
//                ", price=" + price +
//                ", category='" + category + '\'' +
//                ", state=" + state +
//                ", releaseTime=" + releaseTime +
//                '}';
//    }
//}