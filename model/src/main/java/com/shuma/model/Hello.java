package com.shuma.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 测试
 *
 * @author: KingKong
 * @create: 2018-10-03 1:30
 **/

@Data
@TableName("t_hello")
@ApiModel("测试的模型")
public class Hello extends Model<Hello> implements Serializable{
    @TableId(value = "id",type= IdType.AUTO)
    @ApiModelProperty("id")
    private Integer id;
    @ApiModelProperty("测试信息字段")
    private String msg;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
