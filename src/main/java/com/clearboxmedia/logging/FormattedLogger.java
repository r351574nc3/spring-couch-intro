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
package com.clearboxmedia.logging;

import java.io.PrintWriter;
import java.io.StringWriter;

import java.util.logging.Logger;
import static java.util.logging.Level.*;

/**
 * Class with static methods wrapping {@link Log} methods. Automatically sets up Log for you. It's called the <code>FormattedLogger</code> because
 * it handles everything in ansi-C standard printf format. For example, <code>printf("The epoch time is now %d", new Date().getTime())</code>.<br/>
 * <br/>
 *  
 * To use these just do
 * <code>
 * import com.clearboxmedia.logging.FormattedLogger.*
 * </code>
 * 
 */
public class FormattedLogger {
    
    /**
     * Applies a pattern with parameters to create a {@link String} used as a logging message
     *  
     * 
     * @param pattern to format against
     * @param objs an array of objects used as parameters to the <code>pattern</code>
     * @return Logging Message
     */
    private static final String getMessage(String pattern, Object ... objs) {
        StringWriter retval = new StringWriter();
        PrintWriter writer = new PrintWriter(retval);
        
        writer.printf(pattern, objs);
        
        return retval.toString();
    }
    
    /**
     * Uses {@link StackTraceElement[]} from {@link Throwable} to determine the calling class. Then, the {@link Logger} is retrieved for it by
     * convention
     * 
     * 
     * @return Logger for the calling class
     */
    private static final Logger getLogger() {
        try {
            return Logger.getLogger(new Throwable().getStackTrace()[2].getClassName());
        }
        catch (Exception e) {
            // This will never happen unless Java is broken
            return Logger.getLogger(FormattedLogger.class.getSimpleName());
        }
    }

    /**
     * Uses {@link StackTraceElement[]} from {@link Throwable} to determine the calling class. Then, the {@link Logger} is retrieved for it by
     * convention. Just like {@link #getLogger()} except this is intended to be called directly from classes.
     * 
     * 
     * @return Logger for the calling class
     */
    public static final Logger logger() {
        try {
            return Logger.getLogger(new Throwable().getStackTrace()[1].getClassName());
        }
        catch (Exception e) {
            // This will never happen unless Java is broken
            return Logger.getLogger(FormattedLogger.class.getSimpleName());
        }
    }

    /**
     * Wraps {@link Log#fine(String)}
     * 
     * @param pattern to format against
     * @param objs an array of objects used as parameters to the <code>pattern</code>
     */
    public static final void fine(String pattern, Object ... objs) {
        Logger log = getLogger();
        if (log.isLoggable(FINE)) {
            log.fine(getMessage(pattern, objs));
        }
    }

    /**
     * Wraps {@link Log#finer(String)}
     * 
     * @param pattern to format against
     * @param objs an array of objects used as parameters to the <code>pattern</code>
     */
    public static final void finer(String pattern, Object ... objs) {
        Logger log = getLogger();
        if (log.isLoggable(FINER)) {
            log.finer(getMessage(pattern, objs));
        }
    }

    /**
     * Wraps {@link Log#finest(String)}
     * 
     * @param pattern to format against
     * @param objs an array of objects used as parameters to the <code>pattern</code>
     */
    public static final void finest(String pattern, Object ... objs) {
        Logger log = getLogger();
        if (log.isLoggable(FINEST)) {
            log.finest(getMessage(pattern, objs));
        }
    }

    /**
     * Wraps {@link Log#config(String)}
     * 
     * @param pattern to format against
     * @param objs an array of objects used as parameters to the <code>pattern</code>
     */
    public static final void config(String pattern, Object ... objs) {
        Logger log = getLogger();
        if (log.isLoggable(CONFIG)) {
            log.config(getMessage(pattern, objs));
        }
    }

    /**
     * Wraps {@link Log#info(String)}
     * 
     * @param pattern to format against
     * @param objs an array of objects used as parameters to the <code>pattern</code>
     */
    public static final void info(String pattern, Object ... objs) {
        Logger log = getLogger();
        if (log.isLoggable(INFO)) {
            log.info(getMessage(pattern, objs));
        }
    }

    /**
     * Wraps {@link Log#warning(String)}
     * 
     * @param pattern to format against
     * @param objs an array of objects used as parameters to the <code>pattern</code>
     */
    public static final void warn(String pattern, Object ... objs) {
        Logger log = getLogger();
        if (log.isLoggable(WARNING)) {
            log.warning(getMessage(pattern, objs));
        }
    }

    /**
     * Wraps {@link Log#severe(String)}
     * 
     * @param pattern to format against
     * @param objs an array of objects used as parameters to the <code>pattern</code>
     */
    public static final void severe(String pattern, Object ... objs) {
        Logger log = getLogger();
        if (log.isLoggable(SEVERE)) {
            getLogger().severe(getMessage(pattern, objs));
        }
    }
}