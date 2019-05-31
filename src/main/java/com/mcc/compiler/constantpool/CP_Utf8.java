package compiler.constantpool;

import compiler.ConstantPool;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;

public class CP_Utf8 implements ICPEntry {
    /*CONSTANT_Utf8_info {
    u1 tag;
    u2 length;
    u1 bytes[length];
    }*/

    private short length;
    private String bytes;

    public CP_Utf8(String utf8) {
        byte[] utf8Bytes = utf8.getBytes(StandardCharsets.UTF_8);

        this.length = (short)utf8Bytes.length;
        this.bytes = DatatypeConverter.printHexBinary(utf8Bytes);
    }

    @Override
    public String get() {
        String result = "";

        result += String.format("%02x", getTag());
        result += String.format("%04x", this.length);
        result += this.bytes;

        return result;
    }

    @Override
    public byte getTag() {
        return ConstantPool.CONSTANT_Utf8;
    }
}
