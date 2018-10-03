package com.shuma.web.config;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.tags.form.LabelTag;

import java.util.List;

/**
 * 初始化数据
 *
 * @author KingKong
 * @create 2018-09-07 17:18
 * @Order(value=1)//有多个CommandLineRunner接口时可以指定执行顺序（小的先执行）
 **/

@Component
public class DataInitConfig implements CommandLineRunner {
    //CommandLineRunner表示在所有的bean以及applicationCOntenxt完成后的操作


    @Override
    public void run(String... args) throws Exception {
        System.err.println("执行数据初始化操作......");
    }
}
