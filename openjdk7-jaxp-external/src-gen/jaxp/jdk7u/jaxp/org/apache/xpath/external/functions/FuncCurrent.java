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
 * $Id: FuncCurrent.java,v 1.2.4.1 2005/09/14 19:53:44 jeffsuttor Exp $
 */
package jdk7u.jaxp.org.apache.xpath.external.functions;

import jdk7u.jaxp.org.apache.xml.external.dtm.DTM;
import jdk7u.jaxp.org.apache.xml.external.dtm.DTMIterator;
import jdk7u.jaxp.org.apache.xpath.external.XPathContext;
import jdk7u.jaxp.org.apache.xpath.external.axes.LocPathIterator;
import jdk7u.jaxp.org.apache.xpath.external.axes.PredicatedNodeTest;
import jdk7u.jaxp.org.apache.xpath.external.objects.XNodeSet;
import jdk7u.jaxp.org.apache.xpath.external.objects.XObject;
import jdk7u.jaxp.org.apache.xpath.external.axes.SubContextList;
import jdk7u.jaxp.org.apache.xpath.external.patterns.StepPattern;
import jdk7u.jaxp.org.apache.xalan.external.res.XSLMessages;
import jdk7u.jaxp.org.apache.xalan.external.res.XSLTErrorResources;


/**
 * Execute the current() function.
 * @xsl.usage advanced
 */
public class FuncCurrent extends Function
{
    static final long serialVersionUID = 5715316804877715008L;

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

    SubContextList subContextList = xctxt.getCurrentNodeList();
    int currentNode = DTM.NULL;

    if (null != subContextList) {
        if (subContextList instanceof PredicatedNodeTest) {
            LocPathIterator iter = ((PredicatedNodeTest)subContextList)
                                                          .getLocPathIterator();
            currentNode = iter.getCurrentContextNode();
         } else if(subContextList instanceof StepPattern) {
           throw new RuntimeException(XSLMessages.createMessage(
              XSLTErrorResources.ER_PROCESSOR_ERROR,null));
         }
    } else {
        // not predicate => ContextNode == CurrentNode
        currentNode = xctxt.getContextNode();
    }
    return new XNodeSet(currentNode, xctxt.getDTMManager());
  }

  /**
   * No arguments to process, so this does nothing.
   */
  public void fixupVariables(java.util.Vector vars, int globalsSize)
  {
    // no-op
  }

}
