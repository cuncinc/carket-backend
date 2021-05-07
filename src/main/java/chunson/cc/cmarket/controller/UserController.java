package chunson.cc.cmarket.controller;

import chunson.cc.cmarket.mapper.UserMapper;
import chunson.cc.cmarket.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController
{
    private UserMapper daoMapper;

    @Autowired
    public UserController(UserMapper daoMapper)
    {
        this.daoMapper = daoMapper;
    }

    @GetMapping("/login")
    public String login()
    {
        return "hello";
    }

    @GetMapping("/test")
    public User test(String userid)
    {
        return daoMapper.getUserById(userid);
    }
}