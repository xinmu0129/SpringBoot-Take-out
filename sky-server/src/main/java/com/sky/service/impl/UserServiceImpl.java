package com.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    public static final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";

    @Autowired
    private WeChatProperties wechatProperties;

    @Autowired
    private UserMapper userMapper;
    /**
     * 微信登录
     * @param userLoginDTO
     * @return
     */
    public User wxLogin(UserLoginDTO userLoginDTO){
        String openid = getOpenId(userLoginDTO.getCode());
        //判断openid是否为空 若为空 则表示登录失败 抛出业务异常

        if(openid == null){
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }
        //不为空 则合法微信用户 判断是否是新用户 即openid是否在用户表
        User user = userMapper.getByOpenid(openid);

        //若是新用户 自动完成注册
        if(user == null){
            user = User.builder()
                    .openid(openid)
                    .createTime(LocalDateTime.now())
                    .build();
            userMapper.insert(user);
        }
        //返回用户对象
        return user;
    }

    /**
     * 获取微信用户openid
     * @param code
     * @return
     */
    private String getOpenId(String code){
        //调用微信服务器的接口(找HttpClientUtil) 来获得当前用户的openid
        //doget里面的参数 一个是地址 一个是map 即？后面的参数
        Map<String, String>map = new HashMap<>();
        //要获取appid的值 通过配置属性类 就是说用wechatproperties 而这个能找到yml里的信息
        map.put("appid" , wechatProperties.getAppid());
        map.put("secret" , wechatProperties.getSecret());
        map.put("js_code" , code);
        map.put("grant_type" , "authorization_code");
        //他会返回一个json数据包
        String json = HttpClientUtil.doGet(WX_LOGIN , map);

        JSONObject jsonObject = JSON.parseObject(json);
        String openid = jsonObject.getString("openid");
        return openid;
    }
}
