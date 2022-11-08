package org.hectormoraga.placestovisit.client.entity;

import javax.annotation.Resource;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;
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
	
	public TouristicAttraction(Integer id, String nombre, Geometry ubicacion) {
		this.id = id;
		this.nombre = nombre;
		this.ubicacion = ubicacion;
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

	public Coordinate[] getCoodinates() {
		return ubicacion.getCoordinates();
	}
	
	@Override
	public String toString() {
		return "TouristicAttraction [id=" + id + ", nombre=" + nombre + ", ubicacion=" + ubicacion + "]";
	}
}
