package chunson.cc.carket.service;

import chunson.cc.carket.mapper.UserMapper;
import chunson.cc.carket.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService
{
    @Autowired
    private UserMapper mapper;

    public User getUserByAddress(String address)
    {
        return mapper.getUserByAddress(address);
    }
}