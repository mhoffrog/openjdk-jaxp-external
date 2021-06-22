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
 * $Id: FuncSum.java,v 1.2.4.1 2005/09/14 20:18:46 jeffsuttor Exp $
 */
package jdk8u.jaxp.org.apache.xpath.external.functions;

import jdk8u.jaxp.org.apache.xml.external.dtm.DTM;
import jdk8u.jaxp.org.apache.xml.external.dtm.DTMIterator;
import jdk8u.jaxp.org.apache.xml.external.utils.XMLString;
import jdk8u.jaxp.org.apache.xpath.external.XPathContext;
import jdk8u.jaxp.org.apache.xpath.external.objects.XNumber;
import jdk8u.jaxp.org.apache.xpath.external.objects.XObject;

/**
 * Execute the Sum() function.
 * @xsl.usage advanced
 */
public class FuncSum extends FunctionOneArg
{
    static final long serialVersionUID = -2719049259574677519L;

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

    DTMIterator nodes = m_arg0.asIterator(xctxt, xctxt.getCurrentNode());
    double sum = 0.0;
    int pos;

    while (DTM.NULL != (pos = nodes.nextNode()))
    {
      DTM dtm = nodes.getDTM(pos);
      XMLString s = dtm.getStringValue(pos);

      if (null != s)
        sum += s.toDouble();
    }
    nodes.detach();

    return new XNumber(sum);
  }
}
