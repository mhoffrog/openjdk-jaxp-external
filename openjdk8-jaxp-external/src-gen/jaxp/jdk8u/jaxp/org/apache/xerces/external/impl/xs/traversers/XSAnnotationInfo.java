/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
/*
 * Copyright 2004 The Apache Software Foundation.
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
package jdk8u.jaxp.org.apache.xerces.external.impl.xs.traversers;

import org.w3c.dom.Element;
import jdk8u.jaxp.org.apache.xerces.external.impl.xs.opti.ElementImpl;

/**
 * Objects of this class contain the textual representation of
 * an XML schema annotation as well as information on the location
 * of the annotation in the document it originated from.
 *
 * @xerces.internal
 *
 * @author Michael Glavassevich, IBM
 */
final class XSAnnotationInfo {

    /** Textual representation of annotation. **/
    String fAnnotation;

    /** Line number of &lt;annotation&gt; element. **/
    int fLine;

    /** Column number of &lt;annotation&gt; element. **/
    int fColumn;

    /** Character offset of &lt;annotation&gt; element. **/
    int fCharOffset;

    /** Next annotation. **/
    XSAnnotationInfo next;

    XSAnnotationInfo(String annotation, int line, int column, int charOffset) {
        fAnnotation = annotation;
        fLine = line;
        fColumn = column;
        fCharOffset = charOffset;
    }

    XSAnnotationInfo(String annotation, Element annotationDecl) {
        fAnnotation = annotation;
        if (annotationDecl instanceof ElementImpl) {
            final ElementImpl annotationDeclImpl = (ElementImpl) annotationDecl;
            fLine = annotationDeclImpl.getLineNumber();
            fColumn = annotationDeclImpl.getColumnNumber();
            fCharOffset = annotationDeclImpl.getCharacterOffset();
        }
        else {
            fLine = -1;
            fColumn = -1;
            fCharOffset = -1;
        }
    }
} // XSAnnotationInfo
