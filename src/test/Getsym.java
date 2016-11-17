package test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hcq on 2016/11/2.
 */

public class Getsym {
    int Max_Keyword = 10;
    int Max_ID = 10;
    int Max_Length = 50;
    String strToken = "";
    String[] Keyword = {"CONST", "VAR", "PROCEDURE", "END", "ODD", "BEGIN", "IF", "THEN",
            "CALL", "WHILE", "DO", "READ", "WRITE"};
    String[] Class = {" ", "CONSTSYM", "VARSYM", "PROCEDURESYM", "ENDSYM", "ODDSYM", "BEGINSYM",
             "IFSYM", "THENSYM", "CALLSYM", "WHILESYM", "DOSYM", "READSYM", "WRITESYM"};
    String[] SYM = new String[Max_Length];
    String[] ID = new String[Max_Length];
    String[] NUM = new String[Max_Length];

    int index = 0;
    int count = 0;
    char ch;

    public void Getchar(String language) {

        ch = language.charAt(index);
        index++;

    }

    public boolean GetBC() {


        boolean space = Character.isSpaceChar(ch);
        return space;

    }

    public boolean IsLetter() {

        boolean letter = Character.isLetter(ch);
        return letter;

    }

    public boolean IsDigit() {

        boolean digit = Character.isDigit(ch);
        return digit;

    }

    public String Contact() {

        strToken = strToken + ch;
        return strToken;

    }

    public int Retract() {

        index--;
        ch = ' ';
        strToken = "";
        return index;

    }

    public int Reserve() {

        int code = 0;

        for (int i = 0; i <= strToken.length(); i++) {

            if (strToken.equals(Keyword[i]) && strToken.length() <= Max_Keyword) {
                code = i + 1;
                SYM[count] = Class[code];
                count++;
                break;
            } else
                code = 0;
        }
        return code;

    }

    public int InsertId() {

        int code;
        int i;

        if (strToken.length() <= Max_ID) {

            for (i = 0; i <= Max_Length && !ID[i].equals(" "); i++) {

                if (strToken.equals(ID[i])) {
                    code = i;
                    break;
                }

            }
            ID[i] = strToken;
            SYM[count] = "IDENT";
            count++;
            code = i;
        } else
            code = -1;
        return code;

    }

    public int InsertConst() {

        int code;
        int i;

        for (i = 0; i <= Max_Length && !NUM[i].equals(" "); i++) {

            if (strToken.equals(NUM[i])) {
                code = i;
                break;
            }

        }
        NUM[i] = strToken;
        SYM[count] = "NUMBER";
        count++;
        code = i;

        return code;

    }

    public String[] WordScanner(String program) {

        int code, value = 0;
        String result[] = new String[1];

        Getchar(program);
        GetBC();

        if (IsLetter()) {

            while (IsLetter() || IsDigit()) {

                Contact();
                Getchar(program);

            }

            Retract();
            code = Reserve();

            if (code == 0) {

                value = InsertId();
                String b = value + "";
                result[0] = "IDENT";
                result[1] = b;

            } else {
                result[0] = SYM[code];
                result[1] = "";
            }

        } else if (IsDigit()) {

            while (IsDigit()) {

                Contact();
                Getchar(program);
            }

            Retract();
            value = InsertConst();
            String b = value + "";
            result[0] = "NUMBER";
            result[1] = b;

        } else if (ch == ';') {

            result[0] = "$SEMICOLON";
            result[1] = "";
            SYM[count] = "SEMICOLON";
            count++;

        } else if (ch == '+') {

            result[0] = "$PLUS";
            result[1] = "";
            SYM[count] = "PLUS";
            count++;

        } else if (ch == '*') {

            Getchar(program);
            if (ch == '*') {

                result[0] = "$POWER";
                result[1] = "";
                SYM[count] = "POWER";
                count++;

            }

            Retract();

            result[0] = "$STAR";
            result[1] = "";
            SYM[count] = "STAR";
            count++;

        } else if (ch == '=') {

            result[0] = "$ASSIGN";
            result[1] = "";
            SYM[count] = "ASSIGN";
            count++;

        } else if (ch == '(') {

            result[0] = "$LPAR";
            result[1] = "";
            SYM[count] = "LPAR";
            count++;

        } else if (ch == ')') {

            result[0] = "$RPAR";
            result[1] = "";
            SYM[count] = "RPAR";
            count++;

        } else if (ch == '{') {

            result[0] = "$LBRACE";
            result[1] = "";
            SYM[count] = "LBRACE";
            count++;
        } else if (ch == '}') {

            result[0] = "$RBRACE";
            result[1] = "";
            SYM[count] = "RBRACE";
            count++;
        } else if (ch == '<') {

            Getchar(program);
            if (ch == '=') {

                result[0] = "$LEASE_ASSIGN";
                result[1] = "";
                SYM[count] = "LEASE_ASSIGN";
                count++;
                Retract();

            } else {

                Retract();
                result[0] = "$LEASE";
                result[1] = "";
                SYM[count] = "LEASE";
                count++;

            }

        } else if (ch == '>') {

            Getchar(program);
            if (ch == '=') {

                result[0] = "$MORE_ASSIGN";
                result[1] = "";
                SYM[count] = "MORE_ASSIGN";
                count++;
                Retract();

            } else {

                Retract();
                result[0] = "$MORE";
                result[1] = "";
                SYM[count] = "MORE";
                count++;

            }

        }

        return result;

    }

    public static void main(String[] args) {
//        Tools.printSYM();

//        Matcher matcher = Tools.number.matcher("6767&");
//        Matcher matcher1 = Tools.word.matcher("wewr2#");
//        Matcher matcher2 = Tools.symbol.matcher(":=");
//        Matcher matcher3 = Tools.space.matcher("\r");
//        System.out.println(matcher.find()+" "+matcher.end());
//        System.out.println(matcher1.find()+" "+matcher1.end());
//        System.out.println(matcher2.find(0)+" "+matcher2.end());

//        System.out.println(matcher3.find());
//        Matcher matcher2 = Tools.symbol.matcher("+");
//        matcher2.lookingAt();
//        System.out.println(matcher2.end());
//        GetSymbol.parseSrcByRegex();
//        SymTable.printSymTable();

        GetSymbol.parseSrc();
        SymTable.printSymTable();
    }

}

