package org.hectormoraga.placestovisit.client.controller;

import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hectormoraga.placestovisit.client.entity.Country;
import org.hectormoraga.placestovisit.client.entity.TouristicAttraction;
import org.hectormoraga.placestovisit.client.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CountryController {
	private Logger logger = Logger.getLogger(getClass().getName());

	@Autowired
	private CountryService countryService;

	@GetMapping("/countries")
	public ModelAndView getAllCountries() throws URISyntaxException {
		ModelAndView theModel = new ModelAndView("index");
		
		List<Country> theCountries = countryService.getCountries();
		theModel.addObject("countries", theCountries);
		
		return theModel;
	}
	
	@GetMapping("/countries/{id}")
	public ModelAndView getCountryById(@PathVariable("id") Integer id) {
		ModelAndView theModel = new ModelAndView("index");
		
		Country country = countryService.getCountryById(id);
		country.setId(id);
				
		theModel.addObject("selectedCountry", country);
		
		return theModel;
	}

	@GetMapping("/countries/{id}/touristicAttractions")
	public ModelAndView getAllTouristicAttractionsById(@PathVariable Integer id) throws URISyntaxException {
		ModelAndView theModel = new ModelAndView("index");

		List<TouristicAttraction> touristicAttractions = countryService.getTouristicAttractionsByCountryId(id);
		logger.log(Level.INFO, "getAllTouristicAttractionsById({0}): {1}",
				new String[] {id.toString(), touristicAttractions.toString()});
		theModel.addObject("touristicAttractions", touristicAttractions);
		
		return theModel;
	}

}
