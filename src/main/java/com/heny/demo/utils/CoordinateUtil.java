package com.heny.demo.utils;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.log4j.Log4j2;
import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.WKTReader;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CRSAuthorityFactory;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;

@Log4j2
public class CoordinateUtil {
    public static Geometry coordinateTransform(Geometry sourceGeometry, int targetSrid){
        if (sourceGeometry == null || sourceGeometry.getSRID() == 0 || targetSrid == 0){
            return null;
        }
        try {
            CRSAuthorityFactory factory = CRS.getAuthorityFactory(true);
            CoordinateReferenceSystem source = factory.createCoordinateReferenceSystem("EPSG:" + sourceGeometry.getSRID());
            CoordinateReferenceSystem target = factory.createCoordinateReferenceSystem("EPSG:" + targetSrid);
            MathTransform transform = CRS.findMathTransform(source, target,true);
            Geometry res = JTS.transform(sourceGeometry, transform);
            if (res != null){
                res.setSRID(targetSrid);
            }
            return res;
        }catch (FactoryException | TransformException e){
            e.printStackTrace();
        }
        return null;
    }

    public static Geometry createGeometry(String wkt,int srid){
        if (StringUtils.isEmpty(wkt)){
            return null;
        }
        try {
            WKTReader reader = new WKTReader();
            Geometry geometry = reader.read(wkt);
            geometry.setSRID(srid);
            return geometry;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String wkt = "POINT (106.61 25.32)";
        Geometry source = createGeometry(wkt,4490);
        Geometry res = coordinateTransform(source, 4524);
        int x = 1;
    }
}
