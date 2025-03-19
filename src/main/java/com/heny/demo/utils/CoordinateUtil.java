package com.heny.demo.utils;

import lombok.extern.log4j.Log4j2;
import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.locationtech.jts.geom.Coordinate;
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

    public static Coordinate convert3D(Coordinate coordinate, int srcEPSG, int targetEPSG) {
        try {
            // 强制指定坐标轴顺序（兼容三维坐标系）
            CoordinateReferenceSystem sourceCRS = CRS.decode("EPSG:" + srcEPSG, true);
            CoordinateReferenceSystem targetCRS = CRS.decode("EPSG:" + targetEPSG, true);

            // 创建带高程的坐标点（x,y,z）
            Coordinate sourceCoord = new Coordinate(coordinate.x, coordinate.y, coordinate.z);

            // 构建三维坐标转换管道
            MathTransform transform = CRS.findMathTransform(sourceCRS, targetCRS, true);
            Coordinate targetCoord = new Coordinate();

            // 执行坐标转换（自动处理Z轴值）
            JTS.transform(sourceCoord, targetCoord, transform);

            // 高程值处理逻辑
            if (Double.isNaN(targetCoord.z)) {
                // 目标坐标系无高程维度时保留原始Z值
                targetCoord.z = coordinate.z;
            }

            return targetCoord;
        } catch (FactoryException | TransformException e) {
            log.error("三维坐标转换异常：SRC={}, TAR={} | {}",
                    srcEPSG, targetEPSG, e.getMessage());
            throw new RuntimeException("三维坐标转换失败", e);
        }
    }

    public static void main(String[] args) {
        Coordinate src = new Coordinate(3510423.176, 627439.133, 1976.3);

        // 执行转换
//        Coordinate result = CoordinateUtil.convert2D(
//                src.x, src.y,
//                4490,  // WGS84
//                4547   // Web墨卡托
//        );
        Coordinate result = CoordinateUtil.convert3D(
                src,
                4490,
                4547
        );
    }
}
