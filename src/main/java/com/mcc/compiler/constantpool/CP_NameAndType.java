package compiler.constantpool;

import compiler.ConstantPool;

public class CP_NameAndType implements ICPEntry {
    /*CONSTANT_NameAndType_info {
        u1 tag;
        u2 name_index;
        u2 descriptor_index;
    }*/

    public short name_index;
    public short descriptor_index;

    public CP_NameAndType(short nameIndex, short descriptorIndex) {
        this.name_index = nameIndex;
        this.descriptor_index = descriptorIndex;
    }

    @Override
    public String get() {
        String result = "";

        result += String.format("%02x", getTag());
        result += String.format("%04x", this.name_index);
        result += String.format("%04x", this.descriptor_index);

        return result;
    }

    @Override
    public byte getTag() { return ConstantPool.CONSTANT_NameAndType; }
}
