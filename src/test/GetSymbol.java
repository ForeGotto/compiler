package test;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hcq on 2016/11/9.
 */
public class GetSymbol {

    static void parseSrc() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("in.src"))) {
            in = bufferedReader;
            while ((src = in.readLine()) != null) {
                src = src.trim();
                end=0;

                while (end < src.length()) {
                    System.out.println("next to parse at "+end+" :"+src.substring(end,src.length()));
                    String tmp = getWord();
                    if (!tmp.matches("\\s")) {
                        SymTable.insertEntry(tmp);
                    }
                }
                end=0;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static String getWord() {
        tmpS.append(getChar());

        if (isDigit(tmpS)) {
            concat();
            while (end<src.length()) {
                concat();
                if (isDigit(tmpS.append(getChar()))) {
                    concat();
                } else if (tmpS.toString().equals(".")) {
                    concat();
                    break;
                } else {
                    retract();
                    return returnWord();
                }
            }
            while (end < src.length()) {
                concat();
                if (isDigit(tmpS.append(getChar()))) {
                    concat();
                } else {
                    retract();
                    return returnWord();
                }
            }
            return returnWord();
        }

        if (isLetter(tmpS)) {
            concat();
            while (end < src.length()) {
                concat();
                if (isDigitOrLetter(tmpS.append(getChar()))) {
                    concat();
                } else {
                    retract();
                    return returnWord();
                }
            }
            return returnWord();
        }

        if (isSingleOperaor(tmpS)) {
            concat();
            return returnWord();
        }

        if (isDoubleOperator(tmpS)) {
            concat();
            if (tmpS.append(getChar()).toString().endsWith("=")) {
                concat();
                return returnWord();
            }
            retract();
            return returnWord();
        }

        if (tmpS.toString().equals(":")) {
            concat();
            if (tmpS.append(getChar()).toString().endsWith("=")) {
                concat();
                return returnWord();
            }
            retract();
            return returnWord();
        }

        if (isSpace(tmpS)) {
            concat();
            return returnWord();
        }

        System.out.println("error at "+end+" : "+tmpS);
        System.exit(1);

        tmpS.delete(0,tmpS.length());
        result.delete(0,result.length());
        return "error";
    }

    static char getChar() {
        return src.charAt(end++);
    }

    /**
     *
     */
    static void retract() {
        tmpS.delete(0,tmpS.length());
        end--;
    }

    static void concat() {
        result.append(tmpS);
        tmpS.delete(0,tmpS.length());
    }

    static boolean isDigit(StringBuffer stringBuffer) {
        return stringBuffer.toString().matches("[0-9]");
    }

    static boolean isLetter(StringBuffer stringBuffer) {
        return stringBuffer.toString().matches("[a-zA-Z]");
    }

    static boolean isDigitOrLetter(StringBuffer stringBuffer) {
        return stringBuffer.toString().matches("[a-zA-Z0-9]");
    }

    static boolean isSingleOperaor(StringBuffer stringBuffer) {
        return stringBuffer.toString().matches("[-*/=#+;,()]");
    }

    static boolean isDoubleOperator(StringBuffer stringBuffer) {
        return stringBuffer.toString().matches("[<>]");
    }

    static boolean isColon(StringBuffer stringBuffer) {
        return stringBuffer.toString().equals(":");
    }

    static boolean isSpace(StringBuffer stringBuffer) {
        return stringBuffer.toString().matches("\\s");
    }

    private static String returnWord() {
        String tmp = result.toString();
        result.delete(0,result.length());
//        System.out.println(tmp+" "+result.length());
        return tmp;
    }

    /**
     * end标记当前单词最后一个字符在src中的索引
     */
    static int end=-1;
    static StringBuffer tmpS = new StringBuffer();
    static StringBuffer result=new StringBuffer();

    static void initialMatcheres(String psrc) {
        System.out.println("next to parse:"+psrc);
        for (int i=0; i<4; i++) {
            matchers[i].reset(psrc);
        }
    }

    static String getWordByRegex() {
        String csrc = src.substring(index);
        initialMatcheres(csrc);
        for (Matcher matcher : matchers) {
            if (matcher.lookingAt()) {
                index += matcher.end();
                return csrc.substring(matcher.start(),matcher.end());
            }
        }
        System.out.println("error");
        System.exit(0);
        return null;
    }

    static void parseSrcByRegex() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("in.src"))) {
            in = bufferedReader;
            while ((src = in.readLine()) != null) {
                src = src.trim();
                index=0;
                initialMatcheres(src);
                while (index < src.length()) {
                    String tmp = getWordByRegex();
                    if (!tmp.matches("\\s")) {
                        SymTable.insertEntry(tmp);
                    }
                }
                index = 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static String [] strings = {"[0-9][0-9]*\\.?[0-9]*","[a-zA-Z][a-zA-Z0-9]*",
            "[-*/=#<>+;,()]|:=|<=|>=", "\\s"};
    static Pattern [] patterns = new Pattern[4];
    static Matcher [] matchers = new Matcher[4];
    static {
        for(int i=0; i<4; i++) {
            patterns[i] = Pattern.compile(strings[i]);
            matchers[i] = patterns[i].matcher("");
        }
    }

    static int index;
    static BufferedReader in = null;
    static String src=null;

}
