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

package jdk7u.jaxp.org.apache.xerces.external.impl.dv.xs;

import jdk7u.jaxp.org.apache.xerces.external.impl.dv.InvalidDatatypeValueException;
import jdk7u.jaxp.org.apache.xerces.external.impl.dv.ValidationContext;

/**
 * Represent the schema type "anyAtomicType"
 *
 * @xerces.experimental
 *
 * @author Ankit Pasricha, IBM
 *
 */
class AnyAtomicDV extends TypeValidator {

    public short getAllowedFacets() {
        return 0;
    }

    public Object getActualValue(String content, ValidationContext context) throws InvalidDatatypeValueException {
        return content;
    }

} // class AnyAtomicDV
