package com.shuma.web.controller

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.king.kong.tzj.util.ApiDataUtil
import com.shuma.service.HelloService
import com.shuma.model.Hello
import com.shuma.web.config.ApiLog
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * 测试
 *
 * @author: KingKong
 * @create: 2018-10-03 1:42
 */

@Slf4j
@RestController
@Api(tags= arrayOf("测试接口"),description = "用于框架测试")
class HelloController {

    @Autowired
    lateinit var helloService: HelloService

    @GetMapping("/")
    fun hello(): String {
        return "hello king"
    }

    @ApiLog("列表条件查询")
    @GetMapping("/list")
     fun list(): List<Hello> {
        val queryWrapper = QueryWrapper<Hello>()
        queryWrapper.eq("msg", "你发的")
        return helloService.list(queryWrapper)
    }

    @ApiLog("新增")
    @ApiOperation(value = "新增测试",notes="新增测试")
    @ApiImplicitParams(
        ApiImplicitParam(name="msg",value="msg",required = true,dataType ="String",paramType = "query")
    )
    @GetMapping("/insert")
    fun insert(@RequestParam("msg") msg :String): ApiDataUtil {
        val hello = Hello()
        hello.msg=msg
        return if (hello.insert()){
            ApiDataUtil.successResult("添加成功！")
        }else{
            ApiDataUtil.successResult("添加成失败！")
        }
    }
}
