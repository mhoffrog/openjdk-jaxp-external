/*
 * Copyright (c) 2015, Oracle and/or its affiliates. All rights reserved.
 */
/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
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
 * $Id: AttributeSet.java,v 1.5 2005/09/28 13:48:04 pvedula Exp $
 */

package jdk8u.jaxp.org.apache.xalan.external.xsltc.compiler;

import jdk8u.jaxp.org.apache.bcel.external.generic.ConstantPoolGen;
import jdk8u.jaxp.org.apache.bcel.external.generic.INVOKESPECIAL;
import jdk8u.jaxp.org.apache.bcel.external.generic.InstructionList;
import jdk8u.jaxp.org.apache.xalan.external.xsltc.compiler.util.AttributeSetMethodGenerator;
import jdk8u.jaxp.org.apache.xalan.external.xsltc.compiler.util.ClassGenerator;
import jdk8u.jaxp.org.apache.xalan.external.xsltc.compiler.util.ErrorMsg;
import jdk8u.jaxp.org.apache.xalan.external.xsltc.compiler.util.MethodGenerator;
import jdk8u.jaxp.org.apache.xalan.external.xsltc.compiler.util.Type;
import jdk8u.jaxp.org.apache.xalan.external.xsltc.compiler.util.TypeCheckError;
import jdk8u.jaxp.org.apache.xalan.external.xsltc.compiler.util.Util;
import jdk8u.jaxp.org.apache.xml.external.utils.XML11Char;
import java.util.Iterator;
import java.util.List;

/**
 * @author Jacek Ambroziak
 * @author Santiago Pericas-Geertsen
 * @author Morten Jorgensen
 */
final class AttributeSet extends TopLevelElement {

    // This prefix is used for the method name of attribute set methods
    private static final String AttributeSetPrefix = "$as$";

    // Element contents
    private QName            _name;
    private UseAttributeSets _useSets;
    private AttributeSet     _mergeSet;
    private String           _method;
    private boolean          _ignore = false;

    /**
     * Returns the QName of this attribute set
     */
    public QName getName() {
        return _name;
    }

    /**
     * Returns the method name of this attribute set. This method name is
     * generated by the compiler (XSLTC)
     */
    public String getMethodName() {
        return _method;
    }

    /**
     * Call this method to prevent a method for being compiled for this set.
     * This is used in case several <xsl:attribute-set...> elements constitute
     * a single set (with one name). The last element will merge itself with
     * any previous set(s) with the same name and disable the other set(s).
     */
    public void ignore() {
        _ignore = true;
    }

    /**
     * Parse the contents of this attribute set. Recognised attributes are
     * "name" (required) and "use-attribute-sets" (optional).
     */
    public void parseContents(Parser parser) {

        // Get this attribute set's name
        final String name = getAttribute("name");

        if (!XML11Char.isXML11ValidQName(name)) {
            ErrorMsg err = new ErrorMsg(ErrorMsg.INVALID_QNAME_ERR, name, this);
            parser.reportError(Constants.ERROR, err);
        }
        _name = parser.getQNameIgnoreDefaultNs(name);
        if ((_name == null) || (_name.equals(EMPTYSTRING))) {
            ErrorMsg msg = new ErrorMsg(ErrorMsg.UNNAMED_ATTRIBSET_ERR, this);
            parser.reportError(Constants.ERROR, msg);
        }

        // Get any included attribute sets (similar to inheritance...)
        final String useSets = getAttribute("use-attribute-sets");
        if (useSets.length() > 0) {
            if (!Util.isValidQNames(useSets)) {
                ErrorMsg err = new ErrorMsg(ErrorMsg.INVALID_QNAME_ERR, useSets, this);
                parser.reportError(Constants.ERROR, err);
            }
            _useSets = new UseAttributeSets(useSets, parser);
        }

        // Parse the contents of this node. All child elements must be
        // <xsl:attribute> elements. Other elements cause an error.
        final List<SyntaxTreeNode> contents = getContents();
        final int count = contents.size();
        for (int i=0; i<count; i++) {
            SyntaxTreeNode child = contents.get(i);
            if (child instanceof XslAttribute) {
                parser.getSymbolTable().setCurrentNode(child);
                child.parseContents(parser);
            }
            else if (child instanceof Text) {
                // ignore
            }
            else {
                ErrorMsg msg = new ErrorMsg(ErrorMsg.ILLEGAL_CHILD_ERR, this);
                parser.reportError(Constants.ERROR, msg);
            }
        }

        // Point the symbol table back at us...
        parser.getSymbolTable().setCurrentNode(this);
    }

    /**
     * Type check the contents of this element
     */
    public Type typeCheck(SymbolTable stable) throws TypeCheckError {

        if (_ignore) return (Type.Void);

        // _mergeSet Point to any previous definition of this attribute set
        _mergeSet = stable.addAttributeSet(this);

        _method = AttributeSetPrefix + getXSLTC().nextAttributeSetSerial();

        if (_useSets != null) _useSets.typeCheck(stable);
        typeCheckContents(stable);
        return Type.Void;
    }

    /**
     * Compile a method that outputs the attributes in this set
     */
    public void translate(ClassGenerator classGen, MethodGenerator methodGen) {

        if (_ignore) return;

        // Create a new method generator for an attribute set method
        methodGen = new AttributeSetMethodGenerator(_method, classGen);

        // Generate a reference to previous attribute-set definitions with the
        // same name first.  Those later in the stylesheet take precedence.
        if (_mergeSet != null) {
            final ConstantPoolGen cpg = classGen.getConstantPool();
            final InstructionList il = methodGen.getInstructionList();
            final String methodName = _mergeSet.getMethodName();

            il.append(classGen.loadTranslet());
            il.append(methodGen.loadDOM());
            il.append(methodGen.loadIterator());
            il.append(methodGen.loadHandler());
            il.append(methodGen.loadCurrentNode());
            final int method = cpg.addMethodref(classGen.getClassName(),
                                                methodName, ATTR_SET_SIG);
            il.append(new INVOKESPECIAL(method));
        }

        // Translate other used attribute sets first, as local attributes
        // take precedence (last attributes overrides first)
        if (_useSets != null) _useSets.translate(classGen, methodGen);

        // Translate all local attributes
        final Iterator<SyntaxTreeNode> attributes = elements();
        while (attributes.hasNext()) {
            SyntaxTreeNode element = attributes.next();
            if (element instanceof XslAttribute) {
                final XslAttribute attribute = (XslAttribute)element;
                attribute.translate(classGen, methodGen);
            }
        }
        final InstructionList il = methodGen.getInstructionList();
        il.append(RETURN);

        classGen.addMethod(methodGen);
    }

    public String toString() {
        StringBuffer buf = new StringBuffer("attribute-set: ");
        // Translate all local attributes
        final Iterator<SyntaxTreeNode> attributes = elements();
        while (attributes.hasNext()) {
            final XslAttribute attribute =
                (XslAttribute)attributes.next();
            buf.append(attribute);
        }
        return(buf.toString());
    }
}
