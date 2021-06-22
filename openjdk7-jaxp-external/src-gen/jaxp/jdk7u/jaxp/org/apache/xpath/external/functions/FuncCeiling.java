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
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/*
 * $Id: FuncCeiling.java,v 1.2.4.1 2005/09/14 19:53:44 jeffsuttor Exp $
 */
package jdk7u.jaxp.org.apache.xpath.external.functions;

import jdk7u.jaxp.org.apache.xpath.external.XPathContext;
import jdk7u.jaxp.org.apache.xpath.external.objects.XNumber;
import jdk7u.jaxp.org.apache.xpath.external.objects.XObject;

/**
 * Execute the Ceiling() function.
 * @xsl.usage advanced
 */
public class FuncCeiling extends FunctionOneArg
{
    static final long serialVersionUID = -1275988936390464739L;

  /**
   * Execute the function.  The function must return
   * a valid object.
   * @param xctxt The current execution context.
   * @return A valid XObject.
   *
   * @throws javax.xml.transform.TransformerException
   */
  public XObject execute(XPathContext xctxt) throws javax.xml.transform.TransformerException
  {
    return new XNumber(Math.ceil(m_arg0.execute(xctxt).num()));
  }
}
