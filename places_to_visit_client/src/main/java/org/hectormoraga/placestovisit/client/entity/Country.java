package org.hectormoraga.placestovisit.client.entity;

import javax.annotation.Resource;

import org.springframework.hateoas.RepresentationModel;

@Resource
public class Country extends RepresentationModel<Country> {
	private Integer id;
	private String nombre;
	private String alpha2Code;
	private String alpha3Code;
	
	public Country(Integer id, String nombre, String alpha2Code, String alpha3Code) {
		this.id = id;
		this.nombre = nombre;
		this.alpha2Code = alpha2Code;
		this.alpha3Code = alpha3Code;
	}
	
	public Country() {}

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

	public String getAlpha2Code() {
		return alpha2Code;
	}

	public void setAlpha2Code(String alpha2Code) {
		this.alpha2Code = alpha2Code;
	}

	public String getAlpha3Code() {
		return alpha3Code;
	}

	public void setAlpha3Code(String alpha3Code) {
		this.alpha3Code = alpha3Code;
	}

	@Override
	public String toString() {
		return "Country [id=" + id.toString() + ", nombre=" + nombre + ", alpha2Code=" + alpha2Code + ", alpha3Code=" + alpha3Code
				+ "]";
	}
}
