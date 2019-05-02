package compiler.constantpool;

import compiler.ConstantPool;

public class CP_Fieldref implements ICPEntry {
    /*CONSTANT_Fieldref_info {
        u1 tag;
        u2 class_index;
        u2 name_and_type_index;
    }*/

    private short class_index;
    private short name_and_type_index;

    public CP_Fieldref(short classIndex, short nameAndTypeIndex) {
        this.class_index = classIndex;
        this.name_and_type_index = nameAndTypeIndex;
    }

    @Override
    public String get() {
        String result = "";

        result += String.format("%02x", getTag());
        result += String.format("%04x", this.class_index);
        result += String.format("%04x", this.name_and_type_index);

        return result;
    }

    @Override
    public byte getTag() { return ConstantPool.CONSTANT_Fieldref; }
}
