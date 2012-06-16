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
import com.clearboxmedia.srs.providers.eventbrite.config.EventbriteConfig;
import com.clearboxmedia.srs.couch.PersistanceService;
import com.clearboxmedia.srs.domain.EventWrapper;
import com.clearboxmedia.srs.providers.eventbrite.domain.EventbriteSearchQuery;
import com.clearboxmedia.srs.providers.eventbrite.domain.EventbriteSearchResponse;
import com.clearboxmedia.srs.search.EventSearchCriteria;


import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: warnero
 * Date: Apr 6, 2012
 * Time: 8:34:06 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
@Path("/eventbrite")
@Produces("application/json")
public class EventbriteEventPollingService {
    @Autowired
    private PersistanceService persistanceService;
    
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

    public EventbriteConfig getEventbriteConfig() {
        return this.eventbriteConfig;
    }

    public void setEventbriteConfig(EventbriteConfig eventbriteConfig) {
        this.eventbriteConfig = eventbriteConfig;        
    }

    @GET
    @Path("/poll")
    @Produces(MediaType.APPLICATION_JSON)
    public JResponse poll(final String city, final String region) {
        final ObjectMapper mapper = new ObjectMapper();
        final JsonFactory factory = mapper.getJsonFactory();

        final Map<String, String> variables = new HashMap<String, String>();
        variables.put("appKey", eventbriteConfig.getAppKey());

        final EventbriteSearchQuery query = new EventbriteSearchQuery(
            new EventSearchCriteria() {{
                setLimit(25);
                setCity(city);
                setRegion(region);
            }}
            );

        final EventbriteSearchResponse results = restTemplate.getForObject(eventbriteConfig.getApiUrl() + query, EventbriteSearchResponse.class, variables);
        final Map<String,String> retval = new HashMap<String,String>();

        for (final EventWrapper wrapper : results.getEvents()) {
            getPersistanceService().save(wrapper.getEvent());
        }

        retval.put("Polled", "" + results.getEvents().size());

        return JResponse.ok(retval).build();
    }

    
    public void setPersistanceService(final PersistanceService persistanceService) {
        this.persistanceService = persistanceService;
    }

    public PersistanceService getPersistanceService() {
        return this.persistanceService;
    }
}
