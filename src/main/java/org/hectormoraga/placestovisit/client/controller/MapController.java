package org.hectormoraga.placestovisit.client.controller;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import org.hectormoraga.placestovisit.client.entity.Country;
import org.hectormoraga.placestovisit.client.entity.TouristicAttraction;
import org.hectormoraga.placestovisit.client.service.CountryService;
//import org.hectormoraga.placestovisit.client.service.TouristicAttractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/map")
public class MapController {
	@Autowired
	private CountryService theCountryService;
	//@Autowired
	//private TouristicAttractionService theTAService;
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
	public String searchTAs(Model theModel, @ModelAttribute Country country) throws URISyntaxException {
		country = theCountryService.getCountryById(country.getId());
		List<TouristicAttraction> touristicAttractions = theCountryService.getTouristicAttractionsByCountryId(country.getId());
		logger.log(Level.INFO, "searchTAs: {0}", touristicAttractions.toString());

		Map<String, Object> attrs = new HashMap<>();		
		attrs.put("touristicAttractions", touristicAttractions);
		attrs.put("country", country);
		
		theModel.addAllAttributes(attrs);
		
		return "index";
	}	
}
