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
import javax.net.ssl.SSLSocketFactory;

import java.lang.reflect.Field;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerPNames;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import java.util.logging.Logger;

/**
 * Overrides {@link org.jcouchdb.db.ServerImpl} to add https support to the SchemeRegistry
 *
 *
 * @author Leo Przybylski (leo [at] clearboxmedia.com)
 */
public class ServerImpl extends org.jcouchdb.db.ServerImpl {
    
    public ServerImpl(String hostname, int port) {
        super(hostname, port);
    }

    public ServerImpl(String hostname) {
        super(hostname);
    }
    
    public ServerImpl(String hostname, int port, boolean https) {
        super(hostname, port, https);
    }

    protected DefaultHttpClient getHttpClient() {

        if (getObj("httpClient") == null) {
            synchronized(this) {
                if (getObj("httpClient") == null) {
                    final SchemeRegistry supportedSchemes = new SchemeRegistry();
                    final SocketFactory sf = PlainSocketFactory.getSocketFactory();
                    supportedSchemes.register(new Scheme("http", sf, 80));
                    supportedSchemes.register(new Scheme("https", sf, 443));
            
                    final HttpParams params = new BasicHttpParams();
                    HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
                    HttpProtocolParams.setUseExpectContinue(params, false);
                    HttpClientParams.setRedirecting(params, false);
                    params.setParameter(ConnManagerPNames.MAX_CONNECTIONS_PER_ROUTE, new ConnPerRouteBean((Integer) getObj("maxConnectionsPerRoute")));
            
                    params.setParameter(ConnManagerPNames.MAX_TOTAL_CONNECTIONS, (Integer) getObj("maxTotalConnections"));
            
                    set("context", new BasicHttpContext());
                    set("clientConnectionManager", new ThreadSafeClientConnManager(params, supportedSchemes));
                    set("httpClient", new DefaultHttpClient((ThreadSafeClientConnManager) getObj("clientConnectionManager"), params));
                    if (getObj("authScope") != null) {
                        ((DefaultHttpClient) getObj("httpClient")).getCredentialsProvider().setCredentials((AuthScope) getObj("authScope"), (Credentials) getObj("credentials"));
                    }
                }
            }
        }
        return (DefaultHttpClient) getObj("httpClient");
    }

    protected Object getObj(final String fieldName) {
        try {
            Field field = super.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(this);
        }
        catch (Exception e) {
            // convert to runtime exception
            throw new RuntimeException(e);
        }
    }
    protected void set(final String fieldName, final Object value) {
        try {
            Field field = super.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(this, value);
        }
        catch (Exception e) {
            // convert to runtime exception
            throw new RuntimeException(e);
        }
    }
}

