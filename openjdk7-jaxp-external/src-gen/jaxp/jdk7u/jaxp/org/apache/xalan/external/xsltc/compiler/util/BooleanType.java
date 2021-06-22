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
 * $Id: BooleanType.java,v 1.2.4.1 2005/09/05 11:03:37 pvedula Exp $
 */

package jdk7u.jaxp.org.apache.xalan.external.xsltc.compiler.util;

import jdk7u.jaxp.org.apache.bcel.external.generic.BranchHandle;
import jdk7u.jaxp.org.apache.bcel.external.generic.BranchInstruction;
import jdk7u.jaxp.org.apache.bcel.external.generic.CHECKCAST;
import jdk7u.jaxp.org.apache.bcel.external.generic.ConstantPoolGen;
import jdk7u.jaxp.org.apache.bcel.external.generic.GOTO;
import jdk7u.jaxp.org.apache.bcel.external.generic.IFEQ;
import jdk7u.jaxp.org.apache.bcel.external.generic.IFGE;
import jdk7u.jaxp.org.apache.bcel.external.generic.IFGT;
import jdk7u.jaxp.org.apache.bcel.external.generic.IFLE;
import jdk7u.jaxp.org.apache.bcel.external.generic.IFLT;
import jdk7u.jaxp.org.apache.bcel.external.generic.IF_ICMPGE;
import jdk7u.jaxp.org.apache.bcel.external.generic.IF_ICMPGT;
import jdk7u.jaxp.org.apache.bcel.external.generic.IF_ICMPLE;
import jdk7u.jaxp.org.apache.bcel.external.generic.IF_ICMPLT;
import jdk7u.jaxp.org.apache.bcel.external.generic.ILOAD;
import jdk7u.jaxp.org.apache.bcel.external.generic.INVOKESPECIAL;
import jdk7u.jaxp.org.apache.bcel.external.generic.INVOKEVIRTUAL;
import jdk7u.jaxp.org.apache.bcel.external.generic.ISTORE;
import jdk7u.jaxp.org.apache.bcel.external.generic.Instruction;
import jdk7u.jaxp.org.apache.bcel.external.generic.InstructionList;
import jdk7u.jaxp.org.apache.bcel.external.generic.NEW;
import jdk7u.jaxp.org.apache.bcel.external.generic.PUSH;
import jdk7u.jaxp.org.apache.xalan.external.xsltc.compiler.Constants;

/**
 * @author Jacek Ambroziak
 * @author Santiago Pericas-Geertsen
 */
public final class BooleanType extends Type {
    protected BooleanType() {}

    public String toString() {
        return "boolean";
    }

    public boolean identicalTo(Type other) {
        return this == other;
    }

    public String toSignature() {
        return "Z";
    }

    public boolean isSimple() {
        return true;
    }

    public jdk7u.jaxp.org.apache.bcel.external.generic.Type toJCType() {
        return jdk7u.jaxp.org.apache.bcel.external.generic.Type.BOOLEAN;
    }

    /**
     * Translates a real into an object of internal type <code>type</code>. The
     * translation to int is undefined since booleans are always converted to
     * reals in arithmetic expressions.
     *
     * @see     jdk7u.jaxp.org.apache.xalan.external.xsltc.compiler.util.Type#translateTo
     */
    public void translateTo(ClassGenerator classGen, MethodGenerator methodGen,
                            Type type) {
        if (type == Type.String) {
            translateTo(classGen, methodGen, (StringType) type);
        }
        else if (type == Type.Real) {
            translateTo(classGen, methodGen, (RealType) type);
        }
        else if (type == Type.Reference) {
            translateTo(classGen, methodGen, (ReferenceType) type);
        }
        else {
            ErrorMsg err = new ErrorMsg(ErrorMsg.DATA_CONVERSION_ERR,
                                        toString(), type.toString());
            classGen.getParser().reportError(Constants.FATAL, err);
        }
    }

    /**
     * Expects a boolean on the stack and pushes a string. If the value on the
     * stack is zero, then the string 'false' is pushed. Otherwise, the string
     * 'true' is pushed.
     *
     * @see     jdk7u.jaxp.org.apache.xalan.external.xsltc.compiler.util.Type#translateTo
     */
    public void translateTo(ClassGenerator classGen, MethodGenerator methodGen,
                            StringType type) {
        final ConstantPoolGen cpg = classGen.getConstantPool();
        final InstructionList il = methodGen.getInstructionList();
        final BranchHandle falsec = il.append(new IFEQ(null));
        il.append(new PUSH(cpg, "true"));
        final BranchHandle truec = il.append(new GOTO(null));
        falsec.setTarget(il.append(new PUSH(cpg, "false")));
        truec.setTarget(il.append(NOP));
    }

    /**
     * Expects a boolean on the stack and pushes a real. The value "true" is
     * converted to 1.0 and the value "false" to 0.0.
     *
     * @see     jdk7u.jaxp.org.apache.xalan.external.xsltc.compiler.util.Type#translateTo
     */
    public void translateTo(ClassGenerator classGen, MethodGenerator methodGen,
                            RealType type) {
        methodGen.getInstructionList().append(I2D);
    }

    /**
     * Expects a boolean on the stack and pushes a boxed boolean.
     * Boxed booleans are represented by an instance of
     * <code>java.lang.Boolean</code>.
     *
     * @see     jdk7u.jaxp.org.apache.xalan.external.xsltc.compiler.util.Type#translateTo
     */
    public void translateTo(ClassGenerator classGen, MethodGenerator methodGen,
                            ReferenceType type) {
        final ConstantPoolGen cpg = classGen.getConstantPool();
        final InstructionList il = methodGen.getInstructionList();
        il.append(new NEW(cpg.addClass(BOOLEAN_CLASS)));
        il.append(DUP_X1);
        il.append(SWAP);
        il.append(new INVOKESPECIAL(cpg.addMethodref(BOOLEAN_CLASS,
                                                     "<init>",
                                                     "(Z)V")));
    }

    /**
     * Translates an internal boolean into an external (Java) boolean.
     */
    public void translateTo(ClassGenerator classGen, MethodGenerator methodGen,
                            Class clazz) {
        if (clazz == java.lang.Boolean.TYPE) {
            methodGen.getInstructionList().append(NOP);
        }
        // Is Boolean <: clazz? I.e. clazz in { Boolean, Object }
        else if (clazz.isAssignableFrom(java.lang.Boolean.class)) {
            translateTo(classGen, methodGen, Type.Reference);
        }
        else {
            ErrorMsg err = new ErrorMsg(ErrorMsg.DATA_CONVERSION_ERR,
                                        toString(), clazz.getName());
            classGen.getParser().reportError(Constants.FATAL, err);
        }
    }

    /**
     * Translates an external (Java) boolean into internal boolean.
     */
    public void translateFrom(ClassGenerator classGen, MethodGenerator methodGen,
                              Class clazz) {
        translateTo(classGen, methodGen, clazz);
    }

    /**
     * Translates an object of this type to its boxed representation.
     */
    public void translateBox(ClassGenerator classGen,
                             MethodGenerator methodGen) {
        translateTo(classGen, methodGen, Type.Reference);
    }

    /**
     * Translates an object of this type to its unboxed representation.
     */
    public void translateUnBox(ClassGenerator classGen,
                               MethodGenerator methodGen) {
        final ConstantPoolGen cpg = classGen.getConstantPool();
        final InstructionList il = methodGen.getInstructionList();
        il.append(new CHECKCAST(cpg.addClass(BOOLEAN_CLASS)));
        il.append(new INVOKEVIRTUAL(cpg.addMethodref(BOOLEAN_CLASS,
                                                     BOOLEAN_VALUE,
                                                     BOOLEAN_VALUE_SIG)));
    }

    public Instruction LOAD(int slot) {
        return new ILOAD(slot);
    }

    public Instruction STORE(int slot) {
        return new ISTORE(slot);
    }

    public BranchInstruction GT(boolean tozero) {
        return tozero ? (BranchInstruction) new IFGT(null) :
            (BranchInstruction) new IF_ICMPGT(null);
    }

    public BranchInstruction GE(boolean tozero) {
        return tozero ? (BranchInstruction) new IFGE(null) :
            (BranchInstruction) new IF_ICMPGE(null);
    }

    public BranchInstruction LT(boolean tozero) {
        return tozero ? (BranchInstruction) new IFLT(null) :
            (BranchInstruction) new IF_ICMPLT(null);
    }

    public BranchInstruction LE(boolean tozero) {
        return tozero ? (BranchInstruction) new IFLE(null) :
            (BranchInstruction) new IF_ICMPLE(null);
    }
}