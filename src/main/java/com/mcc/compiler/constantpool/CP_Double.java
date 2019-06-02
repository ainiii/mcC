package compiler.constantpool;

import compiler.ConstantPool;

public class CP_Double implements ICPEntry {
    /*CONSTANT_Double_info {
        u1 tag;
        u4 high_bytes;
        u4 low_bytes;
    }*/
    // two constant pool entries

    public double bytes;

    public CP_Double(double value) {
        this.bytes = value;
    }

    @Override
    public String get() {
        String result = "";

        result += String.format("%02x", getTag());
        result += String.format("%016x", Double.doubleToRawLongBits(this.bytes));

        return result;
    }

    @Override
    public byte getTag() {
        return ConstantPool.CONSTANT_Double;
    }
}
