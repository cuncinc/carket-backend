package chunson.cc.carket.utils;

import com.github.lianjiatech.retrofit.spring.boot.core.RetrofitClient;
import retrofit2.http.*;

@RetrofitClient(baseUrl = "${vnt.url}")
public interface VNTUtils
{
//////////// 业务逻辑
    @FormUrlEncoded
    @POST("/charge")
    String charge(@Field("to") String to, @Field("amount") long amount);

    @FormUrlEncoded
    @POST("/setPrice")
    String setPrice(@Field("to") String to, @Field("tokenId") long tokenId, @Field("amount") long amount);

    @FormUrlEncoded
    @POST("/mint")
    int mint(@Field("to") String to, @Field("cid") String cid);

    @FormUrlEncoded
    @POST("/transferToken")
    String transferToken(@Field("from") String from, @Field("to") String to,  @Field("tokenId") long tokenId);

    @FormUrlEncoded
    @POST("/transferVNT")
    String transferVNT(@Field("to") String to, @Field("from") String from, @Field("amount") long amount);

    @GET("/cid")
    String getCid(@Query("tokenId") long tokenId);

    @GET("/price")
    long getPrice(@Query("tokenId") long tokenId);

    @GET("/owner")
    String getOwner(@Query("tokenId") long tokenId);

    @GET("/info")
    String getInfo(@Query("tokenId") long tokenId);

    @GET("/balance")
    String getBalance(@Query("address") String address);


//////////// 下面是溯源相关
    @GET("/blockNumber")
    long getBlockNumber();

    @GET("/block")
    String block(@Query("blockHash") String blockHash);

    @GET("/block")
    String block(@Query("blockHash") long num);

    @GET("/tx")
    String getTx(@Query("txHash") String txHash);

    @GET("/txReceipt")
    String getReceipt(@Query("txHash") String txHash);
}