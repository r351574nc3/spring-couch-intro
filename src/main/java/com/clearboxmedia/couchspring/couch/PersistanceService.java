package com.clearboxmedia.srs.couch;

import com.clearboxmedia.srs.domain.SrsDocument;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.jcouchdb.db.Database;

import static com.clearboxmedia.srs.logging.BufferedLogger.*;

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
    public void save(final SrsDocument document) {
        info("Saving a document");
        database.createDocument(document);
    }
    
    public SrsDocument getDocument(String id, Class clazz) {
    	info("Retrieving document: " + id);
    	return (SrsDocument)database.getDocument(clazz, id);
    }

    public void setDatabase(final Database database) {
        this.database = database;
    }

    public Database getDatabase() {
        return this.database;
    }
}