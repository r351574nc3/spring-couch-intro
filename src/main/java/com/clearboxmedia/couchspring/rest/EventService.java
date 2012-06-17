package com.clearboxmedia.srs.rest;

import com.clearboxmedia.srs.couch.PersistanceService;
import com.clearboxmedia.srs.domain.EventWrapper;
import com.clearboxmedia.srs.json.SearchService;
import com.clearboxmedia.srs.search.EventSearchCriteria;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.jcouchdb.db.Database;
 
/**
 * ReST Web Service that searches the couch database for events and returns them in JSON
 * 
 * @author r351574nc3
 */
@Component
public class EventService {
    @Autowired
    private PersistanceService persistanceService;


    @Autowired
    private SearchService searchService;


    public void setPersistanceService(final PersistanceService persistanceService) {
        this.persistanceService = persistanceService;
    }

    public PersistanceService getPersistanceService() {
        return this.persistanceService;
    }
}