package org.hectormoraga.placestovisit.client.service;

import java.net.URISyntaxException;
import java.util.List;

import org.hectormoraga.placestovisit.client.entity.TouristicAttraction;

public interface TouristicAttractionService {

	public List<TouristicAttraction> getAllTouristicAttractions() throws URISyntaxException;

	public TouristicAttraction getTouristicAttractionById(Integer id);
}
