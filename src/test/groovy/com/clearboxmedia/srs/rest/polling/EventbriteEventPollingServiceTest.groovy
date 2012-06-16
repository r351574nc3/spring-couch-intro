package com.clearboxmedia.srs.rest.polling

import spock.lang.*
import groovyx.net.http.*
import com.sun.jersey.api.JResponse
// import com.clearboxmedia.srs.rest.config.EventbriteConfig
import com.clearboxmedia.srs.providers.eventbrite.config.EventbriteConfig
//import static groovyx.net.http.ContentType.*
//import static groovyx.net.http.Method.*

class EventbriteEventPollingServiceTest extends Specification {


    def "no arguments to polling service"() {
        def pollingService = new EventbriteEventPollingService()
        pollingService.eventbriteConfig = new EventbriteConfig()
        pollingService.eventbriteConfig.appKey="I4TP26KOIL27JGBHO2"
        pollingService.eventbriteConfig.apiUrl="https://www.eventbrite.com/json/event_search?app_key={appKey}{query}"
        
        when:
        def response = pollingService.poll()
        println response.entity
        // expect: response.status == 200
        
        then:
        thrown(NullPointerException)
    }

}  