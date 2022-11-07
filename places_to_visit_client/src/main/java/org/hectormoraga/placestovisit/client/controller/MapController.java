package org.hectormoraga.placestovisit.client.controller;

import java.net.URISyntaxException;
import java.util.List;

import org.hectormoraga.placestovisit.client.entity.Country;
import org.hectormoraga.placestovisit.client.entity.TouristicAttraction;
import org.hectormoraga.placestovisit.client.service.CountryService;
import org.hectormoraga.placestovisit.client.service.TouristicAttractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/map")
public class MapController {
	@Autowired
	private CountryService theCountryService;
	@Autowired
	private TouristicAttractionService theTAService;
	
	@GetMapping("/")
	public String load(Model theModel) throws URISyntaxException {
		List<Country> countries = theCountryService.getCountries();
		theModel.addAttribute("countries", countries);
		theModel.addAttribute("country", new Country());
		return "index";
	}
	
}
