package org.hectormoraga.placestovisit.client.entity;

import static org.locationtech.jts.geom.Geometry.TYPENAME_POINT;

import java.util.Objects;

import javax.annotation.Resource;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.impl.CoordinateArraySequence;
import org.n52.jackson.datatype.jts.GeometryDeserializer;
import org.n52.jackson.datatype.jts.GeometrySerializer;
import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Resource
public class TouristicAttraction extends RepresentationModel<TouristicAttraction>{
	private Integer id;
	private String nombre;
	@JsonSerialize(using = GeometrySerializer.class)
	@JsonDeserialize(using = GeometryDeserializer.class, as = Point.class)
	private Geometry ubicacion;
	private static final GeometryFactory geomFactory = new GeometryFactory();
	
	public TouristicAttraction(Integer id, String nombre, Geometry ubicacion) {
		this.id = id;
		this.nombre = nombre;
		this.ubicacion= ubicacion;
	}
	
	public TouristicAttraction() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Geometry getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(Geometry ubicacion) {
		this.ubicacion = ubicacion;
	}

	public Point getPoint() {
		if (ubicacion.getGeometryType().equals(TYPENAME_POINT)) {
			return new Point(new CoordinateArraySequence(ubicacion.getCoordinates()) , ubicacion.getFactory());
		}
		
		return new Point(null, geomFactory);
	}
	
	public String getCoordinates() {
		Point p = getPoint();
		
		return "(" + p.getX() + ", " + p.getY() + ")";
	}
	
	@Override
	public String toString() {
		return "TouristicAttraction [id=" + id + ", nombre=" + nombre + ", ubicacion=" + ubicacion + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(id, nombre, ubicacion);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof TouristicAttraction)) {
			return false;
		}
		TouristicAttraction other = (TouristicAttraction) obj;
		return Objects.equals(id, other.id) && Objects.equals(nombre, other.nombre)
				&& Objects.equals(ubicacion, other.ubicacion);
	}
	
}
