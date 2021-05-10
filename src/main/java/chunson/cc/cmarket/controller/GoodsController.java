package chunson.cc.cmarket.controller;

import chunson.cc.cmarket.mapper.GoodsMapper;
import chunson.cc.cmarket.model.DisplayGoods;
import chunson.cc.cmarket.model.Goods;
import chunson.cc.cmarket.model.ReleaseGoods;
import chunson.cc.cmarket.model.Result;
import chunson.cc.cmarket.utils.COSUtils;
import chunson.cc.cmarket.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/goods")
public class GoodsController
{
    private GoodsMapper goodsMapper;
    private TokenUtils tokenUtils;

    @Autowired
    public GoodsController(GoodsMapper goodsMapper, TokenUtils tokenUtils)
    {
        this.goodsMapper = goodsMapper;
        this.tokenUtils = tokenUtils;
    }

    @GetMapping("/getAll")
    public Result<List<Goods>> getAll()
    {
        List<Goods> list = goodsMapper.getAllGoods();
        return Result.success(list, null);
    }

    @GetMapping("/getById")
    public Result<Goods> getById(long goodsId)
    {
        Goods goods = goodsMapper.getGoodsById(goodsId);
        return Result.success(goods, null);
    }

    @PostMapping("/updatePic")
    public Result updatePic(ReleaseGoods info, HttpServletRequest request) throws IOException
    {
        String token = request.getHeader("token");
        Long userId = tokenUtils.getUserIdFromToken(token);
        if (null == userId)
        {
            return Result.failure("用户未登录，非法操作，请重新登录");
        }
        long goodsId = info.getGoodsId();
        Goods goods = goodsMapper.getGoodsById(goodsId);
        if (goods.getSellerId() != userId)
        {
            return Result.failure("无权更改他人的商品");
        }

        String key = userId + "_" + System.currentTimeMillis() + ".jpg";

        if (!COSUtils.uploadGoodsPic(info.getFile().getInputStream(), key))
        {
            return Result.failure("图片上传失败");
        }
        goods.setPicKey(key);

        if (!goodsMapper.updatePicKey(goods))
        {
            return Result.failure("修改图片失败");
        }

        return Result.success(goods, null);
    }

    @PostMapping("/updateInfo")
    public Result updateInfo(@RequestBody Map<String, String> req, HttpServletRequest request)
    {
        String token = request.getHeader("token");
        Long userId = tokenUtils.getUserIdFromToken(token);
        if (null == userId)
        {
            return Result.failure("用户未登录，非法操作，请重新登录");
        }
        long goodsId = Long.parseLong(req.get("goodsId"));
        Goods goods = goodsMapper.getGoodsById(goodsId);
        if (goods.getSellerId() != userId)
        {
            return Result.failure("无权更改他人的商品");
        }

        String desc = req.get("desc");
        String category = req.get("category");
        String priceText = req.get("price");

        if (desc != null)
            goods.setGoodsDesc(desc);
        if (category != null)
            goods.setCategory(category);
        if (priceText != null)
        {
            double price = Double.parseDouble(priceText);
            goods.setPrice(price);
        }

        if (!goodsMapper.updateInfo(goods))
        {
            return Result.failure("修改商品失败");
        }

        return Result.success(goods, null);
    }

    @GetMapping("/getAllDisplayGoods")
    public Result getAllDisplayGoods()
    {
        List<DisplayGoods> list = goodsMapper.getAllDisplayGoods();
        return Result.success(list, null);
    }

    @PostMapping("/release")
    public Result release(ReleaseGoods info, HttpServletRequest request) throws IOException
    {
        String token = request.getHeader("token");
        Long userId = tokenUtils.getUserIdFromToken(token);
        if (null == userId)
        {
            return Result.failure("用户未登录，非法操作，请重新登录");
        }

        String desc = info.getGoodsDesc();
        double price = info.getPrice();
        String category = info.getCategory();
        String key = userId + "_" + System.currentTimeMillis() + ".jpg";

        Goods goods = new Goods();
        goods.setGoodsDesc(desc);
        goods.setPrice(price);
        goods.setPicKey(key);
        goods.setCategory(category);
        goods.setSellerId(userId);

        if (!COSUtils.uploadGoodsPic(info.getFile().getInputStream(), key))
        {
            return Result.failure("图片上传失败");
        }

        if (!goodsMapper.insertGoods(goods))
        {
            return Result.failure("发布商品失败");
        }

        return Result.success(goods, null);
    }
}
