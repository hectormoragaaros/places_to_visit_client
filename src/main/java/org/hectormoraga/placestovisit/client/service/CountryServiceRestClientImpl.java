package org.hectormoraga.placestovisit.client.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.hectormoraga.placestovisit.client.entity.Country;
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
public class CountryServiceRestClientImpl implements CountryService {
	private RestTemplate restTemplate;
	private String crmRestUrl;
	private Logger logger = Logger.getLogger(getClass().getName());

	private final String uriStr;
	private final String uriIdStr;

	@Autowired
	public CountryServiceRestClientImpl(RestTemplate theRestTemplate, @Value("${crm.rest.url}") String theUrl) {
		restTemplate = theRestTemplate;

		crmRestUrl = theUrl;
		uriStr = theUrl + "/countries";
		uriIdStr = theUrl + "/countries/{id}";

		logger.log(Level.INFO, "Loaded property:  crm.rest.url={0}", crmRestUrl);
	}

	@Override
	public List<Country> getCountries() throws URISyntaxException, NullPointerException {
		logger.log(Level.INFO, "in getCountries(): Calling REST API {0}", crmRestUrl);

		Traverson client = new Traverson(new URI(crmRestUrl), MediaTypes.HAL_JSON);
		CollectionModel<EntityModel<Country>> countries = client
				.follow("countries")
				.toObject(new CollectionModelType<EntityModel<Country>>() {});

		return (countries!=null)?
				countries.getContent()
						 .stream()
						 .map(EntityModel::getContent)
						 .collect(Collectors.toList()):
			new ArrayList<>();
	}

	@Override
	public Country getCountryById(Integer id) {
		logger.log(Level.INFO, "in getCountryById({0}): Calling REST API {1}",
				new Object[] { id, crmRestUrl});

		Map<String, Integer> vars = new HashMap<>();
		vars.put("id", id);

		ResponseEntity<Country> entityCountry = restTemplate.getForEntity(uriIdStr, Country.class, vars);

		if (entityCountry.hasBody()) {
			return entityCountry.getBody();
		}

		return null;
	}

	@Override
	public List<TouristicAttraction> getTouristicAttractionsByCountryId(Integer id)
			throws URISyntaxException, NullPointerException {
		logger.log(Level.INFO, "in getTouristicAttractionsByCountryId({0}): Calling REST API {1}",
				new Object[]{id, crmRestUrl});

		String uriTA = uriStr + "/" + id;

		Traverson client = new Traverson(new URI(uriTA), MediaTypes.HAL_JSON);
		CollectionModel<EntityModel<TouristicAttraction>> touristicAttractions = null;

		touristicAttractions = client.follow("touristicAttractions")
				.toObject(new CollectionModelType<EntityModel<TouristicAttraction>>() {
				});

		if (touristicAttractions == null) {
			return new ArrayList<>();
		}

		return touristicAttractions.getContent().
				stream().
				map(EntityModel::getContent)
				.collect(Collectors.toList());
	}
}
