package compiler.constantpool;

public class CPEntry {
    public int index;
    public ICPEntry entry;

    public CPEntry(int index, ICPEntry entry) {
        this.index = index;
        this.entry = entry;
    }
}
