/*
 * Copyright (c) 2012, Warner Onstine and Leo Przybylski
 * All rights reserved.
 * 
 * - Redistribution and use in source and binary forms, with or without modification, 
 * are permitted provided that the following conditions are met:
 * 
 * - Redistributions of source code must retain the above copyright notice, this list of 
 * conditions and the following disclaimer.
 * 
 * - Redistributions in binary form must reproduce the above copyright notice, this list 
 * of conditions and the following disclaimer in the documentation and/or other materials 
 * provided with the distribution.
 * 
 * - Neither the name of the <ORGANIZATION> nor the names of its contributors may be used
 * to endorse or promote products derived from this software without specific prior written 
 * permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS 
 * OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF 
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE 
 * COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE
 * GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED 
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED 
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.clearboxmedia.couchspring.couch;

import java.net.MalformedURLException;
import java.net.URL;

import org.jcouchdb.db.ServerImpl;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;

import java.util.logging.Logger;

/**
 * Overrides {@link org.jcouchdb.db.ServerImpl} to force AuthScope.ANY for credentials
 *
 *
 * @author Leo Przybylski (leo [at] clearboxmedia.com)
 */
public class CouchDbServerFactory {
    public static final Logger LOG = Logger.getLogger(CouchDbServerFactory.class.getSimpleName());

    public ServerImpl createCouchDbServerInstance(final String url) throws Exception {
        return this.createCouchDbServerInstance(url, null);
    }

    public ServerImpl createCouchDbServerInstance(final String url, final Credentials credentials) throws Exception {
        return this.createCouchDbServerInstance(new URL(url), null);
    }

    public ServerImpl createCouchDbServerInstance(final URL url, final Credentials credentials) {
        final ServerImpl retval = new ServerImpl(url.getHost(), url.getPort(), url.getProtocol().equals("https"));
        if (credentials != null) {
            retval.setCredentials(AuthScope.ANY, credentials);
        }

        LOG.warning("Creating Couch DB Server instance with port: " + url.getPort());

        return retval;
    }
}