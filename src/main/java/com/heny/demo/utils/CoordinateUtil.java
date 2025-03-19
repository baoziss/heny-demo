package com.heny.demo.utils;

import lombok.extern.log4j.Log4j2;
import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;

@Log4j2
public class CoordinateUtil {
    /**
     * 二维坐标转换
     *
     * @param x          源X坐标
     * @param y          源Y坐标
     * @param srcEPSG    源坐标系EPSG码
     * @param targetEPSG 目标坐标系EPSG码
     * @return 转换后坐标对象
     */
    public static Coordinate convert2D(double x, double y, int srcEPSG, int targetEPSG) {
        try {
            CoordinateReferenceSystem sourceCRS = CRS.decode("EPSG:" + srcEPSG, true);
            CoordinateReferenceSystem targetCRS = CRS.decode("EPSG:" + targetEPSG, true);
            MathTransform transform = CRS.findMathTransform(sourceCRS, targetCRS, true);
            return JTS.transform(new Coordinate(x, y), new Coordinate(), transform);
        } catch (FactoryException | TransformException e) {
            log.error("坐标转换失败: {}", e.getMessage());
            throw new RuntimeException("Coordinate transform error", e);
        }
    }

    public static void main(String[] args) {
        Coordinate src = new Coordinate(116.3975, 39.9087);

        // 执行转换
        Coordinate result = CoordinateUtil.convert2D(
                src.x, src.y,
                4326,  // WGS84
                3857   // Web墨卡托
        );
    }
}
