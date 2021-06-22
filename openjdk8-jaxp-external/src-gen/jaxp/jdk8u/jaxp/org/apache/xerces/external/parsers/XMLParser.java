/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
/*
 * Copyright 1999-2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jdk8u.jaxp.org.apache.xerces.external.parsers;

import java.io.IOException;

import jdk8u.jaxp.org.apache.xerces.external.impl.Constants;
import jdk8u.jaxp.org.apache.xerces.external.utils.XMLSecurityManager;
import jdk8u.jaxp.org.apache.xerces.external.utils.XMLSecurityPropertyManager;
import jdk8u.jaxp.org.apache.xerces.external.xni.XNIException;
import jdk8u.jaxp.org.apache.xerces.external.xni.parser.XMLInputSource;
import jdk8u.jaxp.org.apache.xerces.external.xni.parser.XMLParserConfiguration;

import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.SAXNotRecognizedException;

/**
 * Base class of all XML-related parsers.
 * <p>
 * In addition to the features and properties recognized by the parser
 * configuration, this parser recognizes these additional features and
 * properties:
 * <ul>
 * <li>Properties
 *  <ul>
 *   <li>http://apache.org/xml/properties/external/error-handler</li>
 *   <li>http://apache.org/xml/properties/external/entity-resolver</li>
 *  </ul>
 * </ul>
 *
 * @author Arnaud  Le Hors, IBM
 * @author Andy Clark, IBM
 *
 * @version $Id: XMLParser.java,v 1.5 2007/07/20 14:11:21 spericas Exp $
 */
public abstract class XMLParser {

    //
    // Constants
    //

    // properties

    /** Property identifier: entity resolver. */
    protected static final String ENTITY_RESOLVER =
        Constants.XERCES_PROPERTY_PREFIX + Constants.ENTITY_RESOLVER_PROPERTY;

    /** Property identifier: error handler. */
    protected static final String ERROR_HANDLER =
        Constants.XERCES_PROPERTY_PREFIX + Constants.ERROR_HANDLER_PROPERTY;

    /** Recognized properties. */
    private static final String[] RECOGNIZED_PROPERTIES = {
        ENTITY_RESOLVER,
        ERROR_HANDLER,
    };

    //
    // Data
    //

    /** The parser configuration. */
    protected XMLParserConfiguration fConfiguration;

    /** The XML Security Manager. */
    XMLSecurityManager securityManager;

    /** The XML Security Property Manager. */
    XMLSecurityPropertyManager securityPropertyManager;


    //
    // Constructors
    //

    /**
     * Query the state of a feature.
     */
    public boolean getFeature(String featureId)
            throws SAXNotSupportedException, SAXNotRecognizedException {
        return fConfiguration.getFeature(featureId);

    }

    /**
     * Default Constructor.
     */
    protected XMLParser(XMLParserConfiguration config) {

        // save configuration
        fConfiguration = config;

        // add default recognized properties
        fConfiguration.addRecognizedProperties(RECOGNIZED_PROPERTIES);

    } // <init>(XMLParserConfiguration)

    //
    // Public methods
    //

    /**
     * parse
     *
     * @param inputSource
     *
     * @exception XNIException
     * @exception java.io.IOException
     */
    public void parse(XMLInputSource inputSource)
        throws XNIException, IOException {
        // null indicates that the parser is called directly, initialize them
        if (securityManager == null) {
            securityManager = new XMLSecurityManager(true);
            fConfiguration.setProperty(Constants.SECURITY_MANAGER, securityManager);
        }
        if (securityPropertyManager == null) {
            securityPropertyManager = new XMLSecurityPropertyManager();
            fConfiguration.setProperty(Constants.XML_SECURITY_PROPERTY_MANAGER, securityPropertyManager);
        }

        reset();
        fConfiguration.parse(inputSource);

    } // parse(XMLInputSource)

    //
    // Protected methods
    //

    /**
     * reset all components before parsing
     */
    protected void reset() throws XNIException {
    } // reset()

} // class XMLParser