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
 * $Id: RealExpr.java,v 1.2 2005/08/16 22:30:35 jeffsuttor Exp $
 */

package jdk7u.jaxp.org.apache.xalan.external.xsltc.compiler;

import jdk7u.jaxp.org.apache.bcel.external.generic.ConstantPoolGen;
import jdk7u.jaxp.org.apache.bcel.external.generic.InstructionList;
import jdk7u.jaxp.org.apache.bcel.external.generic.PUSH;
import jdk7u.jaxp.org.apache.xalan.external.xsltc.compiler.util.ClassGenerator;
import jdk7u.jaxp.org.apache.xalan.external.xsltc.compiler.util.MethodGenerator;
import jdk7u.jaxp.org.apache.xalan.external.xsltc.compiler.util.Type;
import jdk7u.jaxp.org.apache.xalan.external.xsltc.compiler.util.TypeCheckError;

/**
 * @author Jacek Ambroziak
 * @author Santiago Pericas-Geertsen
 */
final class RealExpr extends Expression {
    private double _value;

    public RealExpr(double value) {
        _value = value;
    }

    public Type typeCheck(SymbolTable stable) throws TypeCheckError {
        return _type = Type.Real;
    }

    public String toString() {
        return "real-expr(" + _value + ')';
    }

    public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
        ConstantPoolGen cpg = classGen.getConstantPool();
        InstructionList il = methodGen.getInstructionList();
        il.append(new PUSH(cpg, _value));
    }
}
