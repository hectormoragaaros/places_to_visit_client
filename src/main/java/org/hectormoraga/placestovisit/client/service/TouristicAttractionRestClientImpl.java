package org.hectormoraga.placestovisit.client.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hectormoraga.placestovisit.client.entity.TouristicAttraction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
import org.springframework.hateoas.server.core.TypeReferences.CollectionModelType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TouristicAttractionRestClientImpl implements TouristicAttractionService {
	private RestTemplate restTemplate;
	private String crmRestUrl;
	private Logger logger = Logger.getLogger(getClass().getName());

	private final String uriIdStr; 
	
	@Autowired
	public TouristicAttractionRestClientImpl(RestTemplate theRestTemplate, @Value("${crm.rest.url}") String theUrl) {
		restTemplate = theRestTemplate;
		crmRestUrl = theUrl;

		uriIdStr = crmRestUrl + "/touristicAttractions/{id}";
		
		logger.log(Level.INFO, "Loaded property:  crm.rest.url={0}", crmRestUrl);
	}

	@Override
	public List<TouristicAttraction> getAllTouristicAttractions() throws URISyntaxException, NullPointerException {
		logger.log(Level.INFO, "in getAllTouristicAttractions(): Calling REST API {0}", crmRestUrl);
		
		Traverson client = new Traverson(new URI(crmRestUrl), MediaTypes.HAL_JSON);	
		CollectionModel<EntityModel<TouristicAttraction>> theTAs = null;
		
		theTAs = client
				.follow("touristicAttractions")
				.toObject(new CollectionModelType<EntityModel<TouristicAttraction>>() {});
		
		return (theTAs!=null)?theTAs.getContent().stream()
				.map(EntityModel::getContent)
				.toList():new ArrayList<>();		
	}

	@Override
	public TouristicAttraction getTouristicAttractionById(Integer id) {
		logger.log(Level.INFO, "in getTouristicAttractionById({0}): Calling REST API {1}",
				new Object[] {id, crmRestUrl});

		Map<String, Integer> vars = new HashMap<>();
		vars.put("id", id);
		
		ResponseEntity<TouristicAttraction> entityTA = restTemplate.getForEntity(uriIdStr, TouristicAttraction.class, vars);
		
		if (entityTA.hasBody()) {
			return entityTA.getBody();
		}
		
		return null;
	}
}
