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
 * $Id: GetOptsException.java,v 1.2.4.1 2005/08/31 11:47:06 pvedula Exp $
 */

package jdk8u.jaxp.org.apache.xalan.external.xsltc.cmdline.getopt;

/**
 * @author G Todd Miller
 */
public class GetOptsException extends Exception{
    static final long serialVersionUID = 8736874967183039804L;
    public GetOptsException(String msg){
        super(msg);
    }
}
