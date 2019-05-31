package compiler.constantpool;

import compiler.ConstantPool;

public class CP_String implements ICPEntry {
    /*CONSTANT_String_info {
        u1 tag;
        u2 string_index;
    }*/

    private short string_index;

    public CP_String(short index) { this.string_index = index; }

    @Override
    public String get() {
        String result = "";

        result += String.format("%02x", getTag());
        result += String.format("%04x", this.string_index);

        return result;
    }

    @Override
    public byte getTag() { return ConstantPool.CONSTANT_String; }
}
