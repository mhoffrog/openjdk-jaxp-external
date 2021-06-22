/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
/*
 * Copyright 2003,2004 The Apache Software Foundation.
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

package jdk7u.jaxp.org.apache.xerces.external.xs;

/**
 *  Represents a PSVI item for one element information item.
 */
public interface ElementPSVI extends ItemPSVI {
    /**
     * [element declaration]: an item isomorphic to the element declaration
     * used to validate this element.
     */
    public XSElementDeclaration getElementDeclaration();

    /**
     *  [notation]: the notation declaration.
     */
    public XSNotationDeclaration getNotation();

    /**
     * [nil]: true if clause 3.2 of Element Locally Valid (Element) (3.3.4) is
     * satisfied, otherwise false.
     */
    public boolean getNil();

    /**
     * schema information: the schema information property if it is the
     * validation root, <code>null</code> otherwise.
     */
    public XSModel getSchemaInformation();

}
