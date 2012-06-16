package com.clearboxmedia.srs.rest;

import com.clearboxmedia.srs.couch.PersistanceService;
import com.clearboxmedia.srs.domain.EventWrapper;
import com.clearboxmedia.srs.json.SearchService;
import com.clearboxmedia.srs.search.EventSearchCriteria;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import org.springframework.web.client.RestTemplate;

import org.elasticsearch.action.search.SearchResponse;
import org.jcouchdb.db.Database;

import com.sun.jersey.api.JResponse;
 
/**
 * ReST Web Service that searches the couch database for events and returns them in JSON
 * 
 * @author r351574nc3
 */
@Component
@Path("/events")
@Produces("application/json")
public class EventService {
    @Autowired
    private PersistanceService persistanceService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SearchService searchService;


    public RestTemplate getRestTemplate() {
        return this.restTemplate;
    }

    protected void setRestTemplate(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    /**
     * Search backed datastore for events. Queries are in the following format:
     * <p><code>
     * {
     *  "query": {
     *            "type":"event"//optional? could do it based on end point we're calling?
     *            "geometry": {"type": "Point", "coordinates": [102.0, 0.5]},
     *            "radius": "5000", //meters
     *            "limit" : 10
     *            "date-range": {
     *                            "from": "",
     *                            "to":""//date format
     *                          }
     *          }
     * }
     *</code></p>
     * <p>
     * <code>
     * {
     *  "query": {
     *            "type":"event"
     *            "geometry": {"type": "Point", "coordinates": [102.0, 0.5]}, 
     *            "name": "name or partial being searched on"
     *           }
     * }</code></p>
     *
     * <code><p>{
     * "query": {
     *           "type":"event"
     *           "geometry": {"type": "Point", "coordinates": [102.0, 0.5]}, 
     *           "tags": "tag1, tag2"
     *          }
     * }</code></p>
     *
     * <code><p>{
     *  "query": {
     *            "type":"event"
     *            "geometry": {"type": "Point", "coordinates": [102.0, 0.5]}, 
     *            "description": "description keywords"
     *           }
     * }</code></p>
     *
     * <p><code>{
     *  "query": {
     *            "type":"event"
     *            "geometry": {"type": "Point", "coordinates": [102.0, 0.5]}, 
     *            "full": "search tags, description, title, etc for what's in here
     *          (all text fields we store essentially)"
     *           }
     * }
     * 
     * </code></p>
     */
	@POST
	@Path("/jsonSearch")
    @Produces(MediaType.APPLICATION_JSON)
	public JResponse<SearchResponse> search(final String jsonCriteria) { 
        System.out.println("passed criteria is: " + jsonCriteria);
        final EventSearchCriteria query = searchService.parseCriteria(jsonCriteria);
        SearchResponse response = searchService.search(query);
        return JResponse.ok(response).build();
	}

    /**
     * Search backed datastore for events. Queries are in the following format:
     * <code>/events/search?city=&lt;city&gt;&amp;tags=&lt;tags&gt;&amp;&lt;radius&gt;</code>
     *
     * @param city
     * @param state
     * @param tags
     * @param radius
     * @param dateRange
     */
	@POST
	@Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
	public JResponse<List<EventWrapper>> search(final String city,
                                                final String state,
                                                final List<String> tags,
                                                final Float radius,
                                                final String dateRange) { 
        //System.out.println("json response is: " + JResponse.ok(entity).build().toString());
        //return JResponse.ok(entity).build();
        return null;
    }

	@GET
	@Path("/event/{eventid}")
	public Response event(@PathParam("eventid") String eventId) { 
        String result = "Hello world!";
		return Response.status(200).entity(result).build();
    }

    public void setPersistanceService(final PersistanceService persistanceService) {
        this.persistanceService = persistanceService;
    }

    public PersistanceService getPersistanceService() {
        return this.persistanceService;
    }
}