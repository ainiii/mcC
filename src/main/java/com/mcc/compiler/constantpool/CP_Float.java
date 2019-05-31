package compiler.constantpool;

import compiler.ConstantPool;

public class CP_Float implements ICPEntry {
    /*CONSTANT_Float_info {
        u1 tag;
        u4 bytes;
    }*/

    private float bytes;

    public CP_Float(float value) { this.bytes = value; }

    @Override
    public String get() {
        String result = "";

        result += String.format("%02x", getTag());
        result += String.format("%08x", Float.floatToRawIntBits(this.bytes));

        return result;
    }

    @Override
    public byte getTag() { return ConstantPool.CONSTANT_Float; }
}
