package chunson.cc.cmarket.controller;

import chunson.cc.cmarket.mapper.UserMapper;
import chunson.cc.cmarket.model.Result;
import chunson.cc.cmarket.model.User;
import chunson.cc.cmarket.utils.COSUtils;
import chunson.cc.cmarket.utils.PasswordUtils;
import chunson.cc.cmarket.utils.SmsUtils;
import chunson.cc.cmarket.utils.TokenUtils;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController
{
    private UserMapper userMapper;
    private TokenUtils tokenUtils;

    @Autowired
    public UserController(UserMapper usermapper, TokenUtils tokenUtils)
    {
        this.userMapper = usermapper;
        this.tokenUtils = tokenUtils;
    }

    @GetMapping("/getUserById")
    public Result<User> getUserById(Long userId)
    {
        return Result.success(userMapper.getUserById(userId), null);
    }

    @GetMapping("/sendSmsCode")
    public Result sendSmsCode(String phone)
    {
        if (SmsUtils.sendSms(phone))
        {
            return Result.success("验证码已发送");
        }

        return Result.failure("验证码请求失败");
    }

    @PostMapping("/loginByPassword")
    public Result loginByPassword(@RequestBody Map<String, String> req)
    {
        String phone = req.get("phone");
        String password = req.get("password");

//        System.out.println(password);
//        System.out.println(phone);

        User user = userMapper.getUserByPhone(phone);
        if (null == user)
        {
            return Result.failure("用户未注册");
        }

        if (!PasswordUtils.md5Password(password).equals(user.getPassword()))
        {
            return Result.failure("密码错误");
        }

        return Result.success(tokenUtils.generateToken(user), null);
    }

    @PostMapping("/loginByCode")
    public Result loginByCode(@RequestBody Map<String,String> req)
    {
        String phone = req.get("phone");
        String code = req.get("code");
        if (StringUtils.isNullOrEmpty(phone))
        {
            System.out.println("phone is null");
            return Result.failure("手机不能为空");
        }
        else if (StringUtils.isNullOrEmpty(code))
        {
            System.out.println("code is null");
            return Result.failure("验证码不能为空");
        }

        User user = userMapper.getUserByPhone(phone);
        if (null == user)
        {
            return Result.failure("用户未注册");
        }

        if (!SmsUtils.validateSmsCode(phone, code))
        {
            return Result.failure("验证码错误或已失效");
        }

        return Result.success(tokenUtils.generateToken(user), null);
    }

    @GetMapping("/getUserByPhone")
    public Result<User> getUserByPhone(String phone)
    {
        User user = userMapper.getUserByPhone(phone);
//        if (null == user)
//        {
//            return Result.failure("无此用户");
//        }
//        else
//        {
//            return Result.success(user.toString());
//        }
        return Result.success(user, null);
    }

    @PostMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam MultipartFile file, HttpServletRequest request) throws IOException
    {
        String token = request.getHeader("token");
        Long userId = tokenUtils.getUserIdFromToken(token);
        if (null == userId)
        {
            return Result.failure("用户未登录，非法操作，请重新登录");
        }

        String key = userId + ".jpg";
        if (COSUtils.uploadAvatar(file.getInputStream(), key))
        {
            if (userMapper.updateAvatarKey(userId, key))
                return Result.success("修改头像成功");
        }

        return Result.failure("修改头像失败");
    }

    @PostMapping("/logonAndLoginByCode")
    public Result logonAndLoginByCode(@RequestBody Map<String,String> req)
    {
        String phone = req.get("phone");
        String code = req.get("code");
        if (StringUtils.isNullOrEmpty(phone))
        {
            System.out.println("phone is null");
            return Result.failure("手机不能为空");
        }
        else if (StringUtils.isNullOrEmpty(code))
        {
            System.out.println("code is null");
            return Result.failure("验证码不能为空");
        }

        if (!SmsUtils.validateSmsCode(phone, code))
        {
            return Result.failure("验证码错误或已失效");
        }

        User user = userMapper.getUserByPhone(phone);
        if (null == user)
        {
            userMapper.insertUser(phone, null, phone);
            user = userMapper.getUserByPhone(phone);
        }

        return Result.success(tokenUtils.generateToken(user), null);
    }

    @GetMapping("/getMeInfo")
    public Result getMeInfo(HttpServletRequest request)
    {
        String token = request.getHeader("token");
        Long userId = tokenUtils.getUserIdFromToken(token);
        if (null == userId)
        {
            return Result.failure("用户未登录，非法操作，请重新登录");
        }
        return Result.success(userMapper.getUserById(userId), null);
    }


    @PostMapping("/logon")
    public Result logon(@RequestBody Map<String, String> req)
    {
        String phone = req.get("phone");
        String password = req.get("password");
        String code = req.get("code");
        String md5Password = PasswordUtils.md5Password(password);

        if (null != userMapper.getUserByPhone(phone))
        {
            return Result.failure("用户已存在");
        } else if (!SmsUtils.validateSmsCode(phone, code))
        {
            return Result.failure("验证码错误或已过期");
        } else if (!userMapper.insertUser(phone, md5Password, phone))
        {
            return Result.failure("注册失败");
        }

        User user = userMapper.getUserByPhone(phone);
        return Result.success(user.getUserId(), null);
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

        String username = req.get("username");
        String qq = req.get("qq");
        String wechatId = req.get("wechatId");

        User user = userMapper.getUserById(userId);

        // 当为null时，说明不更改；不为null但为空字符串时，说明删除
        if (username != null)
        {
            if (username.isEmpty())
                return Result.failure("用户名不能为空");
            else
                user.setUserName(username);
        }

        if (null != qq)
        {
            if (qq.isEmpty())
                user.setQqNo(null);
            else
                user.setQqNo(qq);
        }

        if (null != wechatId)
        {
            if (wechatId.isEmpty())
                user.setWechatId(null);
            else
                user.setWechatId(wechatId);
        }

        username = user.getUserName();
        qq = user.getQqNo();
        wechatId = user.getWechatId();

        if (!userMapper.updateInfo(userId, username, qq, wechatId))
        {
            return Result.failure("修改个人信息失败");
        }

        return Result.success("修改个人信息成功");
    }

    @GetMapping("/refreshToken")
    public Result refreshToken(HttpServletRequest request)
    {
        String token = request.getHeader("token");
        return Result.success(tokenUtils.refreshToken(token), null);
    }
}