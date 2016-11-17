package test;


import java.util.LinkedList;
import java.util.regex.Pattern;

/**
 * Created by hcq on 2016/11/14.
 */
public class Tools {
    /**
     * 定义各关键字的类型编码，以及标识符的类型编码
     * 静态整型变量用于记忆查表
     * 枚举主要在构造符号表时使用
     * 其中枚举实例的name()方法返回标识符的类型名
     * getName()方法对于关键字返回关键字的符号，对于标识符返回"identifier",对于常数返回"number"
     */
    static final int CONST = 0;
    static final int VAR = 1;
    static final int PROC = 2;//关键字
    static final int BEGIN = 3;
    static final int END = 4;
    static final int IF = 5;
    static final int THEN = 6;
    static final int CALL = 7;
    static final int WHILE = 8;
    static final int DO = 9;
    static final int READ = 10;
    static final int WRITE = 11;
    static final int PLUS = 12;
    static final int MINUS = 13;
    static final int MUL = 14;
    static final int DIV = 15;
    static final int IS = 16;
    static final int ISNOT = 17;
    static final int LP = 18;
    static final int RP = 19;
    static final int SEMICOLON = 20;
    static final int COMMA = 21;
    static final int ASIGNTO = 22;
    static final int UNDER = 23;
    static final int ATMOST = 24;
    static final int ABOVE = 25;
    static final int ATLEAST = 26;
    static final int IDENTIFIER = 27;
    static final int NUMBER = 28;


    public static enum SYM {
        CONST("const"), VAR("var"), PROC("procedure"), BEGIN("begin"), END("end"),
        IF("if"), THEN("then"), CALL("call"), WHILE("while"), DO("do"),
        READ("read"), WRITE("write"),
        PLUS("+"), MINUS("-"), MUL("*"), DIV("/"), IS("="),
        ISNOT("#"), LP("("), RP(")"), SEMICOLON(";"), COMMA(","),
        ASIGNTO(":="), UNDER("<"), ATMOST("<="), ABOVE(">"), ATLEAST(">="),
        IDENTIFIER("identifier"), NUMBER("number");

        SYM(String name) {
            this.name = name;
        }

        /**
         * @return 对于关键字，返回关键字对应字符，对于标识符，返回"identifier",对于常量，返回"number"
         */
        public String getName() {
            return name;
        }

        protected final String name;

    }

    public static Pattern number = Pattern.compile("[([1-9][0-9]*.?[0-9]*)(0.[0-9]+)]");

    public static Pattern word = Pattern.compile("[a-zA-Z][a-zA-Z0-9]*");

    public static Pattern symbol = Pattern.compile("[-*/=#<>(\\+)(:=)(<=)(>=)(\\;)(\\,)(\\()(\\))]");

    public static Pattern space = Pattern.compile("\\s");


    /**
     * 打印所有符号类型及对应字符串
     */
    public static void printSYM() {
        System.out.println("SYM:");
        for (int i = 0; i < SYMBOL.size(); i++) {
            System.out.println(i + ":SYM[" + Tools.SYM.values()[i] + "]:" + SYMBOL.get(i));
        }
    }


    static private LinkedList<String> SYMBOL;

    static {
        SYMBOL = new LinkedList<String>();
        for (int i = 0; i < 29; i++) {
            final String string = SYM.values()[i].getName();
            SYMBOL.add(string);
        }
    }

    /**
     * @param string 作为单词传入的字符串，注意，此函数不能区分常量与标识符，应在调用此函数之前过滤掉常数
     * @return 如果该字符串属于保留字，则返回对应号码，否则返回-1
     */

    public static int getSymType(String string) {
        return SYMBOL.indexOf(string) <= 26 ? SYMBOL.indexOf(string) : -1;
    }
}
