package compiler.constantpool;

import compiler.ConstantPool;

public class CP_MethodHandle implements ICPEntry {
    /*CONSTANT_MethodHandle_info {
        u1 tag;
        u1 reference_kind;
        u2 reference_index;
    }*/

    private byte reference_kind;
    private short reference_index;

    public CP_MethodHandle(byte referenceKind, short referenceIndex) {
        this.reference_kind = referenceKind;
        this.reference_index = referenceIndex;
    }

    @Override
    public String get() {
        String result = "";

        result += String.format("%02x", getTag());
        result += String.format("%02x", this.reference_kind);
        result += String.format("%04x", this.reference_index);

        return result;
    }

    @Override
    public byte getTag() { return ConstantPool.CONSTANT_MethodHandle; }
}
