package test;

import java.util.LinkedList;

/**
 * Created by hcq on 2016/11/14.
 */
public class SymTable {

    public static SymbolEntry insertEntry(String name) {
        int index = Tools.getSymType(name);
        if (index >= 0) {
            return insertKeyEntry(index);
        }
        if (name.matches("[0-9][0-9]*\\.?[0-9]*")) {
            return insertNumber(name);
        }
        return insertId(name);

    }

    public static SymbolEntry insertKeyEntry(int index) {
        SymbolEntry entry = new SymbolEntry(index, null);
        symbolTable.add(entry);
        return new SymbolEntry(index, null);
    }

    /**
     * 此处可能需要先复制name再构造entry
     *
     * @param name
     */
    public static SymbolEntry insertId(String name) {
        SymbolEntry entry = new SymbolEntry(Tools.SYM.IDENTIFIER.ordinal(), name);
        symbolTable.add(entry);
        return entry;
    }

    public static SymbolEntry insertNumber(String value) {
        SymbolEntry entry;
        if (value.contains(".")) {
            entry = new SymbolEntry(Tools.SYM.NUMBER.ordinal(), new Float(Float.parseFloat(value)));
        } else {
            entry = new SymbolEntry(Tools.SYM.NUMBER.ordinal(), new Integer(Integer.parseInt(value)));
        }
        symbolTable.add(entry);
        return entry;
    }

    public static void printSymTable() {
        System.out.println("SymTable:");
        for (SymbolEntry symbolEntry : symbolTable) {
            System.out.println(symbolEntry.type+" "+symbolEntry.value);
        }
    }


    static LinkedList<SymbolEntry> symbolTable = new LinkedList<SymbolEntry>();

    static class SymbolEntry {
        public SymbolEntry(int index, Object value) {
            type = Tools.SYM.values()[index];
            this.value = value;
        }

        Tools.SYM type;
        Object value;
    }
}
