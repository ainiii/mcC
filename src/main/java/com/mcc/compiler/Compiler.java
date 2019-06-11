package compiler;

import ast.*;
import compiler.constantpool.CP_Utf8;

import java.util.ArrayList;

public class Compiler {
    // take RootNode as argument
    // parse to bytecode
    // save to .class file
    public Compiler() { }

    public String compile(RootNode tree) {
        /*
        ClassFile {
            u4             magic;
            u2             minor_version;
            u2             major_version;
            u2             constant_pool_count;
            cp_info        constant_pool[constant_pool_count-1];
            u2             access_flags;
            u2             this_class;
            u2             super_class;
            u2             interfaces_count;
            u2             interfaces[interfaces_count];
            u2             fields_count;
            field_info     fields[fields_count];
            u2             methods_count;
            method_info    methods[methods_count];
            u2             attributes_count;
            attribute_info attributes[attributes_count];
        }

        method_info {
            2 bytes        Methods access flags
            2 bytes        Name of method. UTF8 index in constant pool
            2 bytes        Type of method. UTF8 index in constant pool
            2 bytes        Number of attributes
            * bytes        Variable bytes describing attribute_info structs
        }

        Code_attribute {
            u2 attribute_name_index;
            u4 attribute_length;
            u2 max_stack;
            u2 max_locals;
            u4 code_length;
            u1 code[code_length];
            u2 exception_table_length;
            {   u2 start_pc;
                u2 end_pc;
                u2 handler_pc;
                u2 catch_type;
            } exception_table[exception_table_length];
            u2 attributes_count;
            attribute_info attributes[attributes_count];
        }
        */

        String header = ""; // everything up to constant pool
        String headerBottom = ""; // after constant pool and before code

        header += "cafe babe"; // magic
        header += "00 00 00 34"; // minor/major version (52.0, java 8)

        // constant pool skip

        headerBottom += "00 21"; // super public flags
        headerBottom += String.format("%04x", ConstantPool.findUtf8("mcC") - 1); // this_class
        headerBottom += String.format("%04x", ConstantPool.findUtf8("java/lang/Object") - 1); // super_class
        headerBottom += "00 00"; // interfaces
        headerBottom += "00 00"; // fields
        headerBottom += "00 01"; // methods

        headerBottom += "00 09"; // method flags - public static
        headerBottom += String.format("%04x", ConstantPool.findUtf8("main")); // method name
        headerBottom += String.format("%04x", ConstantPool.findUtf8("([Ljava/lang/String;)V")); // method type (take string, return void)
        headerBottom += "00 01"; // attributes number
        headerBottom += String.format("%04x", ConstantPool.findUtf8("Code"));

        // code attribute body skip
        String body = this.compileTree(tree); // main code

        headerBottom += String.format("%08x", (body.length() / 2) + 12); // size of code attribute
        headerBottom += "0002 0001"; // max size stack 2, max local var size 1
        headerBottom += String.format("%08x", body.length() / 2); // size of code

        headerBottom += body;
        headerBottom += "0000 0000"; // exception table, attributes

        headerBottom += "00 00"; // attributes

        return header + ConstantPool.getPoolSize() + ConstantPool.getPool() + headerBottom;
    }

    public String compileTree(RootNode tree) {
        StringBuilder result = new StringBuilder();

        for (AstNode node : tree.nodes) {
            ArrayList<HexEntry> compiled = compileNode(node);

            for (HexEntry entry : compiled) {
                result.append(String.format("%02x", entry.opcode.hex));

                if (!entry.optional.isEmpty()) {
                    result.append(entry.optional);
                }
            }
        }

        return result.toString() + result.append(String.format("%02x", Opcode.RETURN.hex));
    }

    public ArrayList<HexEntry> compileNode(AstNode node) {
        ArrayList<HexEntry> result = new ArrayList<>();

        if (node instanceof DeclareBoolNode) {

        }

        if (node instanceof DeclareIntNode) {

        }

        if (node instanceof DeclareDoubleNode) {

        }

        if (node instanceof DeclareStringNode) {

        }

        if (node instanceof OperatorPlusNode) {

        }

        if (node instanceof OperatorMinusNode) {

        }

        if (node instanceof OperatorMultiplicationNode) {

        }

        if (node instanceof OperatorIntDivisionNode) {

        }

        if (node instanceof OperatorDoubleDivisionNode) {

        }

        if (node instanceof FunctionPrintNode) {
            ConstantPool.addString(new CP_Utf8(((ValueNode)node.right).value));

            result.add(new HexEntry(Opcode.GETSTATIC, String.format("%04x", ConstantPool.findUtf8("Ljava/io/PrintStream") + 2)));
            result.add(new HexEntry(Opcode.LDC, String.format("%02x", ConstantPool.findUtf8(((ValueNode)node.right).value) - 1)));
            result.add(new HexEntry(Opcode.INVOKEVIRTUAL, String.format("%04x", ConstantPool.findUtf8("(Ljava/lang/String;)V") + 2)));
        }

        return result;
    }

    private class HexEntry {
        Opcode opcode;
        String optional = "";

        public HexEntry(Opcode opcode) {
            this.opcode = opcode;
        }

        public HexEntry(Opcode opcode, String optional) {
            this.opcode = opcode;
            this.optional = optional;
        }
    }
}
