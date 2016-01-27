package com.study;

import com.study.code.PrefixCode;
import com.study.service.IRedisService;
import org.junit.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by huichao on 2015/12/2.
 */
public class JedisTest extends BaseTest {
    @Autowired
    private IRedisService redisService;

    @org.junit.Test
    public void testKeys(){
        System.out.println(redisService.deleteAllKeys(PrefixCode.API_COOKIE_PRE));
    }
}
