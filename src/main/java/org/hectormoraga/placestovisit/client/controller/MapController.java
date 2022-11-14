package org.hectormoraga.placestovisit.client.controller;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.hectormoraga.placestovisit.client.entity.Country;
import org.hectormoraga.placestovisit.client.entity.TouristicAttraction;
import org.hectormoraga.placestovisit.client.service.CountryService;
import org.hectormoraga.placestovisit.client.utils.GeometryUtils;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/map")
public class MapController {
	@Autowired
	private CountryService theCountryService;
	private Logger logger = Logger.getLogger(getClass().getName());
	private List<Country> countries;
	private Country country;
	
	@PostConstruct
	protected void init() throws URISyntaxException {
		countries = theCountryService.getCountries();
		country = new Country();
	}
	
	@GetMapping("/")
	public String load(Model theModel) {
		Map<String, Object> attrs = new HashMap<>();		
		attrs.put("countries", countries);
		attrs.put("country", country);

		theModel.addAllAttributes(attrs);
		
		return "index";
	}
	
	@PostMapping("/searchTAs")
	public ModelAndView searchTAs(@ModelAttribute Country country) throws URISyntaxException {
		ModelAndView theModel = new ModelAndView("index");
		
		country = theCountryService.getCountryById(country.getId());
		List<TouristicAttraction> touristicAttractions = theCountryService.getTouristicAttractionsByCountryId(country.getId());
		List<Point> coordinates = touristicAttractions.stream()
			.map(TouristicAttraction::getPoint)
			.collect(Collectors.toList());			
		GeometryUtils geomUtils = new GeometryUtils(coordinates);
		
		logger.log(Level.INFO, "searchTAs: {0}", touristicAttractions);

		Map<String, Object> attrs = new HashMap<>();		
		attrs.put("countries", countries);
		attrs.put("touristicAttractions", touristicAttractions);
		attrs.put("mapCentroid", geomUtils.getCentroid());
		attrs.put("mapBox", geomUtils.getBox());
		attrs.put("mapZoom", geomUtils.getZoom());
		attrs.put("country", country);		
		
		theModel.addAllObjects(attrs);
		
		return theModel;
	}
}
