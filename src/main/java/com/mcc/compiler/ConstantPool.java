package compiler;

import compiler.constantpool.*;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ConstantPool {
    public static final byte CONSTANT_Utf8 = 1;
    public static final byte CONSTANT_Integer = 3;
    public static final byte CONSTANT_Float = 4;
    public static final byte CONSTANT_Long = 5;
    public static final byte CONSTANT_Double = 6;
    public static final byte CONSTANT_Class = 7;
    public static final byte CONSTANT_String = 8;
    public static final byte CONSTANT_Fieldref = 9;
    public static final byte CONSTANT_Methodref = 10;
    public static final byte CONSTANT_InterfaceMethodref = 11;
    public static final byte CONSTANT_NameAndType = 12;
    public static final byte CONSTANT_MethodHandle = 15;
    public static final byte CONSTANT_MethodType = 16;
    public static final byte CONSTANT_InvokeDynamic = 18;

    private static int constantPoolLastIndex = 1;
    public static ArrayList<CPEntry> constantPool = new ArrayList<>();

    public static void initPool() {
        addClass(new CP_Utf8("mcC")); // main class name
        addClass(new CP_Utf8("java/lang/Object"));
        addClass(new CP_Utf8("java/lang/System"));
        addClass(new CP_Utf8("java/io/PrintStream"));
        addUtf8(new CP_Utf8("out"));
        addUtf8(new CP_Utf8("Ljava/io/PrintStream"));
        constantPool.add(new CPEntry(getNextIndex(1), new CP_NameAndType((short)findUtf8("out"), (short)findUtf8("Ljava/io/PrintStream"))));
        constantPool.add(new CPEntry(getNextIndex(1), new CP_Fieldref((short)(findUtf8("java/lang/System") + 1), (short)(findUtf8("Ljava/io/PrintStream") + 1))));
        addUtf8(new CP_Utf8("println"));
        addUtf8(new CP_Utf8("(Ljava/lang/String;)V"));
        constantPool.add(new CPEntry(getNextIndex(1), new CP_NameAndType((short)findUtf8("println"), (short)findUtf8("(Ljava/lang/String;)V"))));
        constantPool.add(new CPEntry(getNextIndex(1), new CP_Methodref((short)(findUtf8("java/io/PrintStream") + 1), (short)(findUtf8("(Ljava/lang/String;)V") + 1))));
        addUtf8(new CP_Utf8("main"));
        addUtf8(new CP_Utf8("([Ljava/lang/String;)V"));
        addUtf8(new CP_Utf8("Code"));
    }

    public static String getPool() {
        StringBuilder result = new StringBuilder();

        for (CPEntry e : constantPool) {
            result.append(e.entry.get());
        }

        return result.toString();
    }

    public static String getPoolSize() {
        return String.format("%04x", constantPoolLastIndex);
    }

    public static int getNextIndex(int size) {
        constantPoolLastIndex += size;

        return constantPoolLastIndex - size;
    }

    public static void addInteger(CP_Integer entry) {
        constantPool.add(new CPEntry(getNextIndex(1), entry));
    }

    public static void addDouble(CP_Double entry) {
        constantPool.add(new CPEntry(getNextIndex(2), entry));
    }

    // booleans are 0 or 1 int, not in constant pool

    public static void addString(CP_Utf8 entry) {
        int stringEntryIndex = getNextIndex(1);

        constantPool.add(new CPEntry(stringEntryIndex, new CP_String((short)(stringEntryIndex + 1))));
        constantPool.add(new CPEntry(getNextIndex(1), entry));
    }

    public static void addClass(CP_Utf8 entry) {
        int classEntryIndex = getNextIndex(1);

        constantPool.add(new CPEntry(classEntryIndex, new CP_Class((short)(classEntryIndex + 1))));
        constantPool.add(new CPEntry(getNextIndex(1), entry));
    }

    public static void addUtf8(CP_Utf8 entry) {
        constantPool.add(new CPEntry(getNextIndex(1), entry));
    }

    public static int findUtf8(String str) {
        byte[] utf8Bytes = str.getBytes(StandardCharsets.UTF_8);
        String bytes = DatatypeConverter.printHexBinary(utf8Bytes);

        for (CPEntry v : constantPool) {
            if (v.entry instanceof CP_Utf8 && ((CP_Utf8)v.entry).bytes.equals(bytes)) {
                return v.index;
            }
        }

        return -1;
    }
}
