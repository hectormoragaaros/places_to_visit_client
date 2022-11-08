package org.hectormoraga.placestovisit.client.controller;

import java.net.URISyntaxException;
import java.util.List;

import org.hectormoraga.placestovisit.client.entity.TouristicAttraction;
import org.hectormoraga.placestovisit.client.service.TouristicAttractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TouristicAttractionController {

	@Autowired
	TouristicAttractionService touristicAttractionService;

	@GetMapping("/touristicAttractions")
	public ModelAndView getAllTouristicAttractions() throws URISyntaxException {
		ModelAndView theModel = new ModelAndView("index");
		
		List<TouristicAttraction> theTA = touristicAttractionService.getAllTouristicAttractions();
		theModel.addObject("touristicAttractions", theTA);
		
		return theModel;
	}
	
	@GetMapping("/touristicAttractions/{id}")
	public ModelAndView getTouristicAttractionById(@PathVariable Integer id) {
		ModelAndView theModel = new ModelAndView("index");
		
		TouristicAttraction theTouristicAttraction = touristicAttractionService.getTouristicAttractionById(id);
		
		theModel.addObject("selectedTouristicAttraction", theTouristicAttraction);
		
		return theModel;
	}
}
