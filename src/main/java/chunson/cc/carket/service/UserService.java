package chunson.cc.carket.service;

import chunson.cc.carket.mapper.UserMapper;
import chunson.cc.carket.model.User;
import chunson.cc.carket.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class UserService
{
    @Autowired
    private UserMapper mapper;

    public User getUserByAddress(String address)
    {
        return mapper.getUserByAddress(address);
    }

    public boolean updateUser(String address, Map<String ,String> req)
    {
        User user = getUserByAddress(address);
        if(req.containsKey("username")) user.setUsername(req.get("username"));
        if(req.containsKey("email")) user.setUsername(req.get("email"));
        if(req.containsKey("bio")) user.setUsername(req.get("bio"));
        return mapper.updateUser(user);
    }

    public String updateAvatar(String address, MultipartFile avatar)
    {
        String route = FileUtils.storeAvatar(avatar);
        User user = mapper.getUserByAddress(address);
        user.setAvatarRoute(route);
        if (mapper.updateUser(user))
            return user.getAvatarLink();

        return null;
    }
}