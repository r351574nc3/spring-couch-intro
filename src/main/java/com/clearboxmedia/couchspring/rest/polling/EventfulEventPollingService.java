package com.clearboxmedia.srs.rest.polling;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

import org.springframework.web.client.RestTemplate;

import com.sun.jersey.api.JResponse;
import com.clearboxmedia.srs.providers.eventful.config.EventfulConfig;
import com.clearboxmedia.srs.couch.PersistanceService;
import com.clearboxmedia.srs.domain.EventWrapper;
import com.clearboxmedia.srs.providers.eventful.domain.EventfulSearchQuery;
import com.clearboxmedia.srs.providers.eventful.domain.EventfulSearchResponse;
import com.clearboxmedia.srs.search.EventSearchCriteria;


import java.util.*;

/**
 * Service for polling eventful data
 *
 * @author leo
 */
@Component
@Path("/eventful")
@Produces("application/json")
public class EventfulEventPollingService {
    @Autowired
    private PersistanceService persistanceService;
    
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EventfulConfig eventfulConfig;

    public RestTemplate getRestTemplate() {
        return this.restTemplate;
    }

    protected void setRestTemplate(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public EventfulConfig getEventfulConfig() {
        return this.eventfulConfig;
    }

    public void setEventfulConfig(EventfulConfig eventfulConfig) {
        this.eventfulConfig = eventfulConfig;        
    }

    @GET
    @Path("/poll")
    @Produces(MediaType.APPLICATION_JSON)
    public JResponse poll(final String city, final String region) {
        final ObjectMapper mapper = new ObjectMapper();
        final JsonFactory factory = mapper.getJsonFactory();

        final Map<String, String> variables = new HashMap<String, String>();
        variables.put("appKey", eventfulConfig.getAppKey());

        final EventfulSearchQuery query = new EventfulSearchQuery(new EventSearchCriteria() {{
            setLimit(25);
            setCity(city);
            setRegion(region);
        }});

        final EventfulSearchResponse results = restTemplate.getForObject(eventfulConfig.getApiUrl() + query, EventfulSearchResponse.class, variables);
        List<EventWrapper> entity = new ArrayList<EventWrapper>();

        entity.addAll(results.getEvents());

        return JResponse.ok(entity).build();
    }

    
    public void setPersistanceService(final PersistanceService persistanceService) {
        this.persistanceService = persistanceService;
    }

    public PersistanceService getPersistanceService() {
        return this.persistanceService;
    }
}
