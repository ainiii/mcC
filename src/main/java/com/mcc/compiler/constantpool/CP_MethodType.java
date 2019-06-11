package compiler.constantpool;

import compiler.ConstantPool;

public class CP_MethodType implements ICPEntry {
    /*CONSTANT_MethodType_info {
        u1 tag;
        u2 descriptor_index;
    }*/

    public short descriptor_index;

    public CP_MethodType(short descriptorIndex) { this.descriptor_index = descriptorIndex; }

    @Override
    public String get() {
        String result = "";

        result += String.format("%02x", getTag());
        result += String.format("%04x", this.descriptor_index);

        return result;
    }

    @Override
    public byte getTag() { return ConstantPool.CONSTANT_MethodType; }
}
