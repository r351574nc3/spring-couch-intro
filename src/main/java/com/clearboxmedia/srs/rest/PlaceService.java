package com.clearboxmedia.srs.rest;

import com.clearboxmedia.srs.domain.Place;
import com.clearboxmedia.srs.domain.PlaceWrapper;
import com.clearboxmedia.srs.providers.eventbrite.config.EventbriteConfig;
import com.clearboxmedia.srs.providers.eventbrite.domain.Summary;
import com.clearboxmedia.srs.providers.eventbrite.domain.EventbriteSearchResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.springframework.web.client.RestTemplate;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import com.sun.jersey.api.JResponse;

// import com.sun.jersey.api.json.JSONWithPadding;
 
/**
 * Need some JSON Config for jcouchdb
 * 
 * @author r351574nc3
 */
@Component
@Path("/places")
@Produces("application/json")
public class PlaceService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EventbriteConfig eventbriteConfig;

    public RestTemplate getRestTemplate() {
        return this.restTemplate;
    }

    protected void setRestTemplate(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
	@GET
	@Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
	public JResponse<List<PlaceWrapper>> search(@QueryParam("criteria") final String criteria) { 
        // public List<Place> search(@QueryParam("criteria") final String criteria) { 
        System.out.println("Got criteria " + criteria);
        final ObjectMapper mapper = new ObjectMapper();
        final JsonFactory factory = mapper.getJsonFactory();

        try {
            if (criteria != null) {
                final JsonParser jp = factory.createJsonParser(criteria);
                final JsonNode actualObj = mapper.readTree(jp);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        List<PlaceWrapper> entity = new ArrayList<PlaceWrapper>();
        for (final String[] placesarr : eventbriteConfig.getLocationCriteria()) {
            final String city = placesarr[0];
            final String region = placesarr[1];

            final Map<String,String> variables = new HashMap<String, String>();
            variables.put("city", city);
            variables.put("region", region);
            variables.put("appKey", eventbriteConfig.getAppKey());
            final EventbriteSearchResponse results = restTemplate.getForObject(eventbriteConfig.getApiUrl(), 
                                                                               EventbriteSearchResponse.class, variables);
            entity.addAll(results.getPlaces());
        }
        // return entity;
        return JResponse.ok(entity).build();
	}

	@GET
	@Path("/place/{placeid}")
	public Response place(@PathParam("placeid") String placeId) { 
        String result = "Hello world!";
		return Response.status(200).entity(result).build();
    }

	@GET
	@Path("/sync")
	public Response sync() { 
        final String eventbriteUrl = eventbriteConfig.getApiUrl();
        final String key = eventbriteConfig.getAppKey();


        for (final String[] placearr : eventbriteConfig.getLocationCriteria()) {
            final String city = placearr[0].trim();
            final String region = placearr[1].trim();

            final Map<String, String> variables = new HashMap<String, String>();
            variables.put("appKey", key);
            variables.put("city", city);
            variables.put("region", region);

            final Map events = getRestTemplate().getForObject(eventbriteUrl, Map.class, variables);
        }

        String result = "Sync'd events";
		return Response.status(200).entity(result).build();
    } 
}