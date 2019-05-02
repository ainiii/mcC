package compiler.constantpool;

import compiler.ConstantPool;

public class CP_Long implements ICPEntry {
    /*CONSTANT_Long_info {
        u1 tag;
        u4 high_bytes;
        u4 low_bytes;
    }*/
    // two constant pool entries

    private long bytes;

    public CP_Long(long value) {
        this.bytes = value;
    }

    @Override
    public String get() {
        String result = "";

        result += String.format("%02x", getTag());
        result += String.format("%016x", this.bytes);

        return result;
    }

    @Override
    public byte getTag() {
        return ConstantPool.CONSTANT_Long;
    }
}
