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
package com.clearboxmedia.couchspring.json;

import java.util.Arrays;

import org.svenson.ClassNameBasedTypeMapper;
import org.svenson.JSON;
import org.svenson.JSONConfig;
import org.svenson.JSONParser;
import org.svenson.converter.DateConverter;
import org.svenson.converter.DefaultTypeConverterRepository;
import org.svenson.matcher.SubtypeMatcher;

import com.clearboxmedia.couchspring.domain.AppDocument;

/**
 * Need some JSON Config for jcouchdb
 * 
 * @author r351574nc3
 *
 */
public class JsonConfigFactory {

    /**
     * Factory method for creating a {@link JSONConfig}
     *
     * @return {@link JSONConfig} to create
     */
    JSONConfig createJsonConfig() {
        final DateConverter dateConverter = new DateConverter();
        
        final DefaultTypeConverterRepository typeConverterRepository = new DefaultTypeConverterRepository();
        typeConverterRepository.addTypeConverter(dateConverter);
        // typeConverterRepository.addTypeConverter(new LatLongConverter());

        // we use the new sub type matcher  
        final ClassNameBasedTypeMapper typeMapper = new ClassNameBasedTypeMapper();
        typeMapper.setBasePackage(AppDocument.class.getPackage().getName());
        // we only want to have AppDocument instances
        typeMapper.setEnforcedBaseType(AppDocument.class);
        // we use the docType property of the AppDocument 
        typeMapper.setDiscriminatorField("docType");        
        // we only want to do the expensive look ahead if we're being told to
        // deliver AppDocument instances.        
        typeMapper.setPathMatcher(new SubtypeMatcher(AppDocument.class));

        final JSON generator = new JSON();
        generator.setIgnoredProperties(Arrays.asList("metaClass"));
        generator.setTypeConverterRepository(typeConverterRepository);
        generator.registerTypeConversion(java.util.Date.class, dateConverter);
        generator.registerTypeConversion(java.sql.Date.class, dateConverter);
        generator.registerTypeConversion(java.sql.Timestamp.class, dateConverter);
    
        final JSONParser parser = new JSONParser();
        parser.setTypeMapper(typeMapper);
        parser.setTypeConverterRepository(typeConverterRepository);
        parser.registerTypeConversion(java.util.Date.class, dateConverter);
        parser.registerTypeConversion(java.sql.Date.class, dateConverter);
        parser.registerTypeConversion(java.sql.Timestamp.class, dateConverter);

        return new JSONConfig(generator, parser);
    }
}
