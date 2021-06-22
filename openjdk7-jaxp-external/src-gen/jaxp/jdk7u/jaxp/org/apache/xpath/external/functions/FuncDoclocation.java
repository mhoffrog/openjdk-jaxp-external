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
 * $Id: FuncDoclocation.java,v 1.2.4.1 2005/09/14 19:53:44 jeffsuttor Exp $
 */
package jdk7u.jaxp.org.apache.xpath.external.functions;

import jdk7u.jaxp.org.apache.xml.external.dtm.DTM;
import jdk7u.jaxp.org.apache.xpath.external.XPathContext;
import jdk7u.jaxp.org.apache.xpath.external.objects.XObject;
import jdk7u.jaxp.org.apache.xpath.external.objects.XString;

/**
 * Execute the proprietary document-location() function, which returns
 * a node set of documents.
 * @xsl.usage advanced
 */
public class FuncDoclocation extends FunctionDef1Arg
{
    static final long serialVersionUID = 7469213946343568769L;

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

    int whereNode = getArg0AsNode(xctxt);
    String fileLocation = null;

    if (DTM.NULL != whereNode)
    {
      DTM dtm = xctxt.getDTM(whereNode);

      // %REVIEW%
      if (DTM.DOCUMENT_FRAGMENT_NODE ==  dtm.getNodeType(whereNode))
      {
        whereNode = dtm.getFirstChild(whereNode);
      }

      if (DTM.NULL != whereNode)
      {
        fileLocation = dtm.getDocumentBaseURI();
//        int owner = dtm.getDocument();
//        fileLocation = xctxt.getSourceTreeManager().findURIFromDoc(owner);
      }
    }

    return new XString((null != fileLocation) ? fileLocation : "");
  }
}
