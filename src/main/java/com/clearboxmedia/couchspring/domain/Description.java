package com.clearboxmedia.couchspring.domain;

import java.util.Collection;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description wrapper. Helps strip html tags and parse out useful metadata from descriptions. Probably should eventually
 * be made into a service specifically for parsing useful information from text (maybe even spidering information!)
 *
 * @author Leo Przybylski
 */
public class Description {
    private String desc;

    public Description(final String desc) {
        this.desc = desc;
    }
    
    /**
     * Get a {@link Collection} of image url strings
     *
     * @returns a {@link Collection} of image urls
     */
    public Collection<String> findImages() {
        final LinkedList<String> retval = new LinkedList();
        
        String imgregex = "<img|[^>]*href\\=\\\"([^>]*)\\\"[^>]*>";
        Pattern p1 = Pattern.compile(imgregex,Pattern.CASE_INSENSITIVE);
        Matcher m1 = p1.matcher(desc);
        
        while (m1.find()) {
            final String imageUrl = m1.group(1);
            if (imageUrl != null) {
                retval.add(imageUrl);
            }
        }

        return retval;
    }

    public String toString() {
        String retval = "" + desc;
        // Wipe out tags that usually don't have any relative information in their text portions.
        final String scriptregex = "<(script|style)[^>]*>[^<]*</(script|style)>";
        final Pattern p1 = Pattern.compile(scriptregex, Pattern.CASE_INSENSITIVE);
        Matcher m1 = p1.matcher(retval);

        retval = m1.replaceAll("");
        
        // Match any HTML/XML tag
        String tagregex = "<[^>]*>";
        Pattern p2 = Pattern.compile(tagregex);
        Matcher m2 = p2.matcher(retval);

        retval = m2.replaceAll("");
        
        // Use backreferencing to remove one or two newlines
        String multiplenewlines = "(\\n{1,2})(\\s*\\n)+"; 
        retval = retval.replaceAll(multiplenewlines,"$1");
        
        return retval;
    }
}