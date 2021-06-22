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
 * $Id: FuncNot.java,v 1.2.4.1 2005/09/14 20:18:44 jeffsuttor Exp $
 */
package jdk7u.jaxp.org.apache.xpath.external.functions;

import jdk7u.jaxp.org.apache.xpath.external.XPathContext;
import jdk7u.jaxp.org.apache.xpath.external.objects.XBoolean;
import jdk7u.jaxp.org.apache.xpath.external.objects.XObject;

/**
 * Execute the Not() function.
 * @xsl.usage advanced
 */
public class FuncNot extends FunctionOneArg
{
    static final long serialVersionUID = 7299699961076329790L;

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
    return m_arg0.execute(xctxt).bool() ? XBoolean.S_FALSE : XBoolean.S_TRUE;
  }
}
