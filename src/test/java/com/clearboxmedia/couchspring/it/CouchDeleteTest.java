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
package com.clearboxmedia.couchspring.it;

import java.util.Map;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.runner.RunWith;

import com.clearboxmedia.couchspring.domain.Event;

import org.jcouchdb.db.Database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.springframework.test.context.ContextConfiguration;

/**
 * Tests use cases for deleting documents to a couch database
 *
 * @author Leo Przybylski (leo [at] clearboxmedia.com)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/root-context.xml")
public class CouchDeleteTest {

    @Autowired
    protected Database database;

    @Test
    public void testEventDelete() {
        Event document = database.getDocument(Event.class, "3083848875");
        assertTrue(document != null);
        database.delete(document);
        
        try {
            document = database.getDocument(Event.class, "3083848875");
        }
        catch (Exception e) {
            assertTrue(e instanceof org.jcouchdb.exception.NotFoundException);
        }
    }


    @Test(expected = org.jcouchdb.exception.NotFoundException.class)
    public void testEventDelete_NotExists() {
        Event document = database.getDocument(Event.class, "-2");
        assertTrue(document != null);
        database.delete(document);
        document = database.getDocument(Event.class, "-2");
        assertTrue(document == null);
    }
}
