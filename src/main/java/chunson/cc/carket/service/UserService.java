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
    public enum ImgType{
        Avatar,
        Cover,
    }

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
        if(req.containsKey("email")) user.setEmail(req.get("email"));
        if(req.containsKey("bio")) user.setBio(req.get("bio"));
        return mapper.updateUser(user);
    }

    public String updateImgResource(String address, MultipartFile imgFile, ImgType type)
    {
        String route;
        User user = mapper.getUserByAddress(address);
        switch (type)
        {
            case Avatar:
                route = FileUtils.storeAvatar(imgFile);
                user.setAvatarRoute(route);
                if (mapper.updateUser(user))
                    return user.getAvatarLink();
                break;
            case Cover:
                route = FileUtils.storeCover(imgFile);
                user.setCoverRoute(route);
                if (mapper.updateUser(user))
                    return user.getCoverLink();
                break;
        }

        return null;
    }
}