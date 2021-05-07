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
    public User getUserById(Long userId)
    {
        return userMapper.getUserById(userId);
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
    public Result loginByPassword(@RequestParam Map<String, String> req)
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

        return Result.success(tokenUtils.generateToken(user));
    }

    @PostMapping("/loginByCode")
    public Result loginByCode(@RequestParam Map<String, String> req)
    {
        String phone = req.get("phone");
        String code = req.get("code");

        User user = userMapper.getUserByPhone(phone);
        if (null == user)
        {
            return Result.failure("用户未注册");
        }

        if (!SmsUtils.validateSmsCode(phone, code))
        {
            return Result.failure("验证码错误或已失效");
        }

        return Result.success(tokenUtils.generateToken(user));
    }

    @GetMapping("/getUserByPhone")
    public User getUserByPhone(String phone)
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
        return user;
    }

    @PostMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam MultipartFile file, HttpServletRequest request) throws IOException
    {
        String token = request.getHeader("token");
        long userId = tokenUtils.getUserIdFromToken(token);
        String key = userId + ".jpg";

        if (COSUtils.uploadAvatar(file.getInputStream(), key))
        {
            if (userMapper.updateAvatarKey(userId, key))
                return Result.success(null, "修改头像成功");
        }

        return Result.failure("修改头像失败");
    }

    @PostMapping("/logon")
    public Result logon(@RequestParam Map<String, String> req)
    {
        String phone = req.get("phone");
        String password = req.get("password");
        String code = req.get("code");

        return null;
    }
}