package org.hectormoraga.placestovisit.client.utils;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

public class GeometryUtils {
	private Logger logger = Logger.getLogger(GeometryUtils.class.getName());
	private Point centroid;
	private Point[] box;
	private int zoom;
	private static final GeometryFactory geometryFactory = new GeometryFactory();
	
	public GeometryUtils(List<Point> listPoints) {
		centroid = getCentroid(listPoints);
		box = getBox(listPoints);
		zoom = calculateOptimalZoom(box, 1.2);
	}

	public Point getCentroid() {
		return centroid;
	}
	
	public Point[] getBox() {
		return box;
	}
	
	public int getZoom() {
		return zoom;
	}
	
	private Point getCentroid(List<Point> listPoints) {
		try {
			double cx = 0.0;
			double cy = 0.0;
			int num = listPoints.size();

			for (Point point : listPoints) {
				cx += point.getCoordinates()[0].x;
				cy += point.getCoordinates()[0].y;
			}

			Coordinate coords = new Coordinate(cx / num, cy / num);

			return geometryFactory.createPoint(coords);
		} catch (Exception ex) {
			logger.log(Level.WARNING, ex.toString());
			return geometryFactory.createPoint(new Coordinate(0, 0));
		}
	}

	private Point[] getBox(List<Point> listPoints) {
		double minX = 0.0;
		double maxX = 0.0;
		double minY = 0.0;
		double maxY = 0.0;
		Point pmin = geometryFactory.createPoint();
		Point pmax = geometryFactory.createPoint();

		try {
			minX = listPoints.get(0).getX();
			minY = listPoints.get(0).getY();
			maxX = listPoints.get(0).getX();
			maxY = listPoints.get(0).getY();

			for (int i = 0; i < listPoints.size(); i++) {
				Point p = listPoints.get(i);

				if (p.getX() - minX < 0) {
					minX = p.getX();
				}
				if (p.getY() - minY < 0) {
					minY = p.getY();
				}
				if (p.getX() - maxX > 0) {
					maxX = p.getX();
				}
				if (p.getY() - maxY > 0) {
					maxY = p.getY();
				}
			}

			pmin = geometryFactory.createPoint(new Coordinate(minX, minY));
			pmax = geometryFactory.createPoint(new Coordinate(maxX, maxY));

			return new Point[] { pmin, pmax };
		} catch (Exception ex) {
			logger.log(Level.WARNING, ex.toString());
			return new Point[] { pmin, pmax };
		}
	}
	
	private int calculateOptimalZoom (Point[] mapBox, double paddingFactor)
    {
		logger.log(Level.INFO, "mapBox=({0},{1})-({2},{3})",
				new String[] {String.valueOf(mapBox[0].getX()),
						String.valueOf(mapBox[0].getY()),
						String.valueOf(mapBox[1].getX()),
						String.valueOf(mapBox[1].getY())
				});
		// if mapbox size is 0, default zoom is 2
		if (Math.abs(mapBox[0].getX()-mapBox[1].getX())<=1E-7) {
			return 15;
		}
		
		double ry1 = Math.log(Math.abs((Math.sin(Math.toRadians(mapBox[0].getY()))+1)
				/Math.cos(Math.toRadians(mapBox[0].getY()))));
		logger.log(Level.INFO, "ry1={0}", String.valueOf(ry1));
		double ry2 = Math.log(Math.abs((Math.sin(Math.toRadians(mapBox[1].getY()))+1)/
				Math.cos(Math.toRadians(mapBox[1].getY()))));
		logger.log(Level.INFO, "ry2={0}", String.valueOf(ry2));
		double ryc = (ry1 + ry2) / 2;
		logger.log(Level.INFO, "ryc={0}", String.valueOf(ryc));
		double centerY = Math.toDegrees(Math.atan(Math.sinh(ryc)));
		logger.log(Level.INFO, "centerY={0}", String.valueOf(centerY));
		double resolutionHorizontal = (mapBox[1].getX()-mapBox[0].getX())/800;
		logger.log(Level.INFO, "resolutionHorizontal={0}", String.valueOf(resolutionHorizontal));
		double vy0 = Math.log(Math.abs(Math.tan(Math.PI*(0.25+centerY/360))));
		logger.log(Level.INFO, "vy0={0}", String.valueOf(vy0));
		double vy1 = Math.log(Math.abs(Math.tan(Math.PI*(0.25 + mapBox[1].getY()/360))));
		logger.log(Level.INFO, "vy1={0}", String.valueOf(vy1));
        double viewHeightHalf = 300.0;
        double zoomFactorPowered = viewHeightHalf/(40.7436654315252*(vy1 - vy0));
		logger.log(Level.INFO, "zoomFactorPowered={0}", String.valueOf(zoomFactorPowered));
        double resolutionVertical = 360.0 / (zoomFactorPowered*256);
		logger.log(Level.INFO, "resolutionVertical={0}", String.valueOf(resolutionVertical));        
        double resolution = Math.max(resolutionHorizontal, resolutionVertical)*paddingFactor;
		logger.log(Level.INFO, "resolution={0}", String.valueOf(resolution));
        int zoomCalculated = (int)Math.floor(Math.log(360/(resolution*256))/Math.log(2.0));
		logger.log(Level.INFO, "zoom={0}", String.valueOf(zoomCalculated));
        
		return (zoomCalculated>0)?zoomCalculated-1:0;
    }

}
