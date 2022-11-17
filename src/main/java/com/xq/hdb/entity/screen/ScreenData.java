package com.xq.hdb.entity.screen;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.models.auth.In;
import lombok.Data;
import java.util.Date;

@Data
@TableName("screen_data")
public class ScreenData {

    @TableField("id")
    private String id;

    @TableField("product_good_amount")
    private Integer productGoodAmount;

    @TableField("query_time")
    private String queryTime;

    @TableField("create_time")
    private Date createTime;

}
