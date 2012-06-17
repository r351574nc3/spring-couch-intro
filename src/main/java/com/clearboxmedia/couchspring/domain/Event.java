package com.clearboxmedia.couchspring.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

import org.svenson.JSONProperty;

/**
 * Syncrswim event!
 * 
 * @author Leo "r351574nc3" Przybylski
 */
public class Event extends AppDocument {
    private String id;
    private String title;
    private String description;
    private String startTime;
    private String stopTime;
    private Place venue;
    private String venueId;
    private Map<String,String> links;
    private String lastUpdated;
    private String externalUpdatedDate;
    private List<String> tags;
    
    public Event() {
    }
    
    public Event(Map data, String provider) {
        this.setExternalId(data.get("id").toString());
        this.setProvider(provider);
        this.title               = (String) data.get("title");
        this.startTime           = (String) data.get("start_date");
        this.stopTime            = (String) data.get("stop_date");
        this.lastUpdated         = (String) data.get("modified");
        this.externalUpdatedDate = lastUpdated;
        this.links               = new LinkedHashMap();
        links.put("main", "/syncrswim/events/event/" + id);
        links.put("image", "");
        
        this.tags = new ArrayList<String>();
        if (data.get("tags") != null) {
            tags.addAll(Arrays.asList(data.get("tags").toString().split(",")));
        }

        // Handle the description now
        final Description description = new Description((String) data.get("description"));
        this.description = description.toString();
        

        final Iterator<String> imageUrls_it = description.findImages().iterator();
        if (imageUrls_it.hasNext()) {
            links.put("image", imageUrls_it.next());
        }
    }

    public String getId() {
        return this.id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }


    public String getLastUpdated() {
        return this.lastUpdated;
    }

    public void setLastUpdated(final String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getExternalUpdatedDate() {
        return this.externalUpdatedDate;
    }

    public void setExternalUpdatedDate(final String externalUpdatedDate) {
        this.externalUpdatedDate = externalUpdatedDate;
    }

    @JSONProperty(ignore = true)
    public Place getVenue() {
        return this.venue;
    }

    public String getVenueId() {
        return this.venueId;
    }

    public void setVenueId(final String venueId) {
        this.venueId = venueId;
    }
    public String getDescription() {
        return this.description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public void setStartTime(final String startTime) {
        this.startTime = startTime;
    }


    public String getStopTime() {
        return this.stopTime;
    }

    public void setStopTime(final String stopTime) {
        this.stopTime = stopTime;
    }

    public List<String> getTags() {
        return this.tags;
    }

    public void setTags(final List<String> tags) {
        this.tags = tags;
    }

    public Map<String,String> getLinks() {
        return this.links;
    }

    public void setLinks(final Map<String,String> links) {
        this.links = links;
    }
}