package com.clearboxmedia.couchspring.domain;

import org.jcouchdb.document.BaseDocument;
import org.svenson.JSONProperty;

/**
 *
 */
public class AppDocument extends BaseDocument {

    private String externalId;
    private String provider;
    
    /**
     * Returns the simple name of the class as doc type.
     * 
     * The annotation makes it a read-only property and also shortens the JSON name a little.
     *
     * @return document type name
     */
    @JSONProperty(value = "docType", readOnly = true)
    public String getDocumentType()
    {
        return this.getClass().getSimpleName();
    }
    
    public String getExternalId() {
        return this.externalId;
    }

    public void setExternalId(final String externalId) {
        this.externalId = externalId;
    }

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}
    
}