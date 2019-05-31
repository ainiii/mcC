package compiler.constantpool;

import compiler.ConstantPool;

public class CP_Class implements ICPEntry {
    /*CONSTANT_Class_info {
        u1 tag;
        u2 name_index;
    }*/

    private short name_index;

    public CP_Class(short index) {
        this.name_index = index;
    }

    @Override
    public String get() {
        String result = "";

        result += String.format("%02x", getTag());
        result += String.format("%04x", this.name_index);

        return result;
    }

    @Override
    public byte getTag() {
        return ConstantPool.CONSTANT_Class;
    }
}
