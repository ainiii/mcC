package compiler.constantpool;

import compiler.ConstantPool;

public class CP_InvokeDynamic implements ICPEntry {
    /*CONSTANT_InvokeDynamic_info {
        u1 tag;
        u2 bootstrap_method_attr_index;
        u2 name_and_type_index;
    }*/

    public short bootstrap_method_attr_index;
    public short name_and_type_index;

    public CP_InvokeDynamic(short bootstrapMethodAttrIndex, short nameAndTypeIndex) {
        this.bootstrap_method_attr_index = bootstrapMethodAttrIndex;
        this.name_and_type_index = nameAndTypeIndex;
    }

    @Override
    public String get() {
        String result = "";

        result += String.format("%02x", getTag());
        result += String.format("%04x", this.bootstrap_method_attr_index);
        result += String.format("%04x", this.name_and_type_index);

        return result;
    }

    @Override
    public byte getTag() { return ConstantPool.CONSTANT_InvokeDynamic; }
}
