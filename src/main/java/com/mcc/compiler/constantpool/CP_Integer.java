package compiler.constantpool;

import compiler.ConstantPool;

public class CP_Integer implements ICPEntry {
    /*CONSTANT_Integer_info {
        u1 tag;
        u4 bytes;
    }*/

    private int bytes;

    public CP_Integer(int value) { this.bytes = value; }

    @Override
    public String get() {
        String result = "";

        result += String.format("%02x", getTag());
        result += String.format("%08x", this.bytes);

        return result;
    }

    @Override
    public byte getTag() { return ConstantPool.CONSTANT_Integer; }
}
