package com.mealz.server.global.util;

import lombok.experimental.UtilityClass;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Point;
import org.geolatte.geom.crs.CoordinateReferenceSystems;

@UtilityClass
public class PostGisUtil {

  public Point<G2D> makePoint(double lat, double lon) {
    return new Point<>(new G2D(lat, lon), CoordinateReferenceSystems.WGS84);
  }

}
