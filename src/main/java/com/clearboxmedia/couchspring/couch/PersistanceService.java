package com.clearboxmedia.couchspring.couch;

import com.clearboxmedia.couchspring.domain.AppDocument;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.jcouchdb.db.Database;

/**
 * Service responsible for persisting changes with couch
 *
 * @author r351574nc3
 */
@Component
public class PersistanceService {
    @Autowired
    private Database database;

    /**
     * Saves a document to couchdb
     */
    public void save(final AppDocument document) {
        database.createDocument(document);
    }
    
    public AppDocument getDocument(String id, Class clazz) {
    	return (AppDocument)database.getDocument(clazz, id);
    }

    public void setDatabase(final Database database) {
        this.database = database;
    }

    public Database getDatabase() {
        return this.database;
    }
}