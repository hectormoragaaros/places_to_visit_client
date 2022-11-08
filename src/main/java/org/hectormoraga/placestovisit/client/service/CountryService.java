package org.hectormoraga.placestovisit.client.service;

import java.net.URISyntaxException;
import java.util.List;

import org.hectormoraga.placestovisit.client.entity.Country;
import org.hectormoraga.placestovisit.client.entity.TouristicAttraction;

public interface CountryService {

	public List<Country> getCountries() throws URISyntaxException;

	public Country getCountryById(Integer id);
	
	public List<TouristicAttraction> getTouristicAttractionsByCountryId(Integer id) throws URISyntaxException;

}
