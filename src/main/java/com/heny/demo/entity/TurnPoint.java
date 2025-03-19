package com.heny.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.heny.demo.handler.PointTypeHandler;
import lombok.Data;
import org.locationtech.jts.geom.Point;

/**
 * @author :Yozuru
 * @since :2024/9/14 17:19
 */
@Data
@TableName("turn_point")
public class TurnPoint {
    @TableId("id")
    private Integer id;
    @TableField(typeHandler = PointTypeHandler.class)
    private Point coordinate;
    private String index;
}
