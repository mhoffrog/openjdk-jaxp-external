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
 * $Id: FuncUnparsedEntityURI.java,v 1.2.4.1 2005/09/14 20:18:46 jeffsuttor Exp $
 */
package jdk7u.jaxp.org.apache.xpath.external.functions;

import jdk7u.jaxp.org.apache.xml.external.dtm.DTM;
import jdk7u.jaxp.org.apache.xpath.external.XPathContext;
import jdk7u.jaxp.org.apache.xpath.external.objects.XObject;
import jdk7u.jaxp.org.apache.xpath.external.objects.XString;

/**
 * @xsl.usage advanced
 */
public class FuncUnparsedEntityURI extends FunctionOneArg
{
    static final long serialVersionUID = 845309759097448178L;

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

    String name = m_arg0.execute(xctxt).str();
    int context = xctxt.getCurrentNode();
    DTM dtm = xctxt.getDTM(context);
    int doc = dtm.getDocument();

    String uri = dtm.getUnparsedEntityURI(name);

    return new XString(uri);
  }
}