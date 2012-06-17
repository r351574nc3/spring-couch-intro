package com.clearboxmedia.couchspring.domain;

import java.util.LinkedHashMap;
import java.util.List;

public class Place extends AppDocument {
    private String id;
    private String name;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String postalCode;
    private String lastUpdated;
    private Boolean active;
    private Location location;
    private String venueType;
    private List<String> tags;

    public Place() {
    }

    public Place(final LinkedHashMap data, String provider) {
        System.out.println("populating venue:" + data);
        
        this.setExternalId(data.get("id").toString());
        this.setProvider(provider);
        this.name               = (String) data.get("title");
        this.address1           = (String) data.get("address");
        this.address2            = (String) data.get("address2");
        this.city         = (String) data.get("city");
        this.state = (String) data.get("region");
        this.postalCode = (String)data.get("postal_code");
        this.location = new Location();
        this.location.setLat((Double)data.get("latitude"));
        this.location.setLong((Double)data.get("longitude"));     
    }

    public String getId() {
        return this.id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getAddress1() {
        return this.address1;
    }

    public void setAddress1(final String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return this.address2;
    }

    public void setAddress2(final String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public String getState() {
        return this.state;
    }

    public void setState(final String state) {
        this.state = state;
    }
    
    public Location getLocation() {
        return this.location;
    }
    
    public void setLocation(final Location location) {
        this.location = location;
    }

    public String getVenueType() {
        return this.venueType;
    }

    public void setVenueType(final String venueType) {
        this.venueType = venueType;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public void setPostalCode(final String postalCode) {
        this.postalCode = postalCode;
    }

    public String getLastUpdated() {
        return this.lastUpdated;
    }

    public void setLastUpdated(final String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Boolean getActive() {
        return this.active;
    }

    public void setActive(final Boolean active) {
        this.active = active;
    }

    public List<String> getTags() {
        return this.tags;
    }

    public void setTags(final List<String> tags) {
        this.tags = tags;
    }
}