package com.heny.demo.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.jts.io.ByteOrderValues;
import org.locationtech.jts.io.WKBReader;
import org.locationtech.jts.io.WKBWriter;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AbstractGeometryTypeHandler<T extends Geometry> extends BaseTypeHandler<Geometry> {
    private static GeometryFactory geometryFactory;

    static {
        // 经度保留6为小数
        PrecisionModel precisionModel = new PrecisionModel(1000000);
        geometryFactory = new GeometryFactory(precisionModel, 4326);
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Geometry parameter, JdbcType jdbcType) throws SQLException {
        ps.setBytes(i, convertToBytes(parameter));
    }

    @Override
    public Geometry getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return convertToGeo(rs.getBytes(columnName));
    }

    @Override
    public Geometry getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return convertToGeo(rs.getBytes(columnIndex));
    }

    @Override
    public Geometry getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return convertToGeo(cs.getBytes(columnIndex));
    }

    /**
     * bytes转Geo对象
     * @param bytes
     * @return
     */
    private Geometry convertToGeo(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        try {
            byte[] geomBytes = ByteBuffer.allocate(bytes.length - 4).order(ByteOrder.LITTLE_ENDIAN)
                    .put(bytes, 4, bytes.length - 4).array();
            Geometry geometry = new WKBReader(geometryFactory).read(geomBytes);
            return geometry;
        } catch (Exception e) {
            throw new  RuntimeException("解析Geometry数据失败", e);
        }
    }

    /**
     * geo转bytes
     * @param geometry
     * @return
     * @throws IOException
     */
    private byte[] convertToBytes(Geometry geometry) {
        WKBWriter wkbWriter = new WKBWriter(2, ByteOrderValues.LITTLE_ENDIAN, false);
        byte[] geometryBytes = wkbWriter.write(geometry);
        byte[] wkb = new byte[geometryBytes.length + 4];
        //设置SRID为4326
        ByteOrderValues.putInt(4326, wkb, ByteOrderValues.LITTLE_ENDIAN);
        System.arraycopy(geometryBytes, 0, wkb, 4, geometryBytes.length);
        return wkb;
    }
}
