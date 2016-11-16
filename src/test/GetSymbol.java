package test;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hcq on 2016/11/9.
 */
public class GetSymbol {

    static void parseSrcByRegex() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("in.src"))) {
            in = bufferedReader;
            while ((src = in.readLine()) != null) {
                src = src.trim();
                index=0;
                initialMatcheres(src);
                while (index < src.length()) {
                    String tmp = getWord();
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

    static void initialMatcheres(String psrc) {
        System.out.println("next to parse:"+psrc);
        for (int i=0; i<4; i++) {
            matchers[i].reset(psrc);
        }
    }

    static String getWord() {
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

    static String [] strings = {"[([1-9][0-9]*.?[0-9]*)||(0.[0-9]+)]","[a-zA-Z][a-zA-Z0-9]*",
            "[-*/=#<>(\\+)(:=)(<=)(>=)(\\;)(\\,)(\\()(\\))]", "\\s"};
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
    static StringBuffer result=new StringBuffer();
}
