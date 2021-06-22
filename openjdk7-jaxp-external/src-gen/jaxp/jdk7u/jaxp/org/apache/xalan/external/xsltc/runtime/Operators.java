/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
/*
 * Copyright 2001-2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/*
 * $Id: Operators.java,v 1.2.4.1 2005/09/12 12:02:15 pvedula Exp $
 */

package jdk7u.jaxp.org.apache.xalan.external.xsltc.runtime;

/**
 * @author Jacek Ambroziak
 * @author Santiago Pericas-Geertsen
 */
public final class Operators {
    public static final int EQ = 0;
    public static final int NE = 1;
    public static final int GT = 2;
    public static final int LT = 3;
    public static final int GE = 4;
    public static final int LE = 5;

    private static final String[] names = {
    "=", "!=", ">", "<", ">=", "<="
    };

    public static final String getOpNames(int operator) {
          return names[operator];
    }

//  Swap operator array
    private static final int[] swapOpArray = {
        EQ,     // EQ
        NE,     // NE
        LT,     // GT
        GT,     // LT
        LE,     // GE
        GE      // LE
    };

    public static final int swapOp(int operator) {
          return swapOpArray[operator];
    }

}
