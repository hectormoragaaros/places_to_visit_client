package org.hectormoraga.placestovisit.client.utils;

import java.util.List;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

public class GeometryUtils {
	private static final GeometryFactory geometryFactory = new GeometryFactory();

	public static Point getCentroid(List<Point> listPoints) {
		try {
			double cx = 0.0, cy = 0.0;
			int num = listPoints.size();

			for (Point point : listPoints) {
				cx += point.getCoordinates()[0].x;
				cy += point.getCoordinates()[0].y;
			}

			Coordinate coords = new Coordinate(cx / num, cy / num);

			Point centroid = geometryFactory.createPoint(coords);
			
			return centroid;
		} catch (Exception ex) {
			return geometryFactory.createPoint(new Coordinate(0, 0));
		}
	}
	
}
