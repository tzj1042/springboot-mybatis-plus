package com.shuma.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shuma.service.HelloService;
import com.shuma.dao.HelloMapper;
import com.shuma.model.Hello;
import org.springframework.stereotype.Service;

/**
 * @author: KingKong
 * @create: 2018-10-03 3:17
 **/

@Service
public class HelloServiceImpl extends ServiceImpl<HelloMapper,Hello> implements HelloService {
}
