package com.heny.demo.handler;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.locationtech.jts.geom.Point;

@MappedJdbcTypes(JdbcType.OTHER)
@MappedTypes(value = Point.class)
public class PointTypeHandler extends AbstractGeometryTypeHandler{
}
