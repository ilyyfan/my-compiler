package lexer;

import utils.MyFileReader;

import java.util.ArrayList;

/**
 * @description: 词法分析器
 * @author: ilyyfan
 * @createDate: 2020/5/12
 */
public class Lexer {

    private String source;
    private ArrayList<Token> resultList;

    public Lexer(String filename) {
        this.source = MyFileReader.getFile(filename);
        this.resultList = new ArrayList<>();
    }

    public ArrayList<Token> getResultList() {
        return this.resultList;
    }

    public void analyze() {
        cur_index = 0;
        //当前行数
        resultList.clear();
        char ch;
        while ((ch = getNext(source)) != 0) {
            if (ch == ' ' || ch == '\t') {
                //忽略空格和制表符
            } else if (ch == '\n' || ch == '\r') { //回车换行
            } else if ((ch <= 'Z' && ch >= 'A') || (ch <= 'z' && ch >= 'a')) { //关键字或标识符
                String tmpString = ch + "";
                while ((ch = getNext(source)) != 0) {
                    if ((ch <= 'Z' && ch >= 'A') || (ch <= 'z' && ch >= 'a') || (ch <= '9' && ch >= '0') || ch == '_') {
                        tmpString += ch;
                    } else { //回退一个字符，跳出循环
                        cur_index --;
                        break;
                    }
                }
                if (isKeyword(tmpString)) { //关键字
                    int type = 0;
                    while (type ++ < 32){
                        if (tmpString.equals(keywords[type]))
                            break;
                    }
                    resultList.add(new Token(type, ""));
                } else { //标识符
                    resultList.add(new Token(Type.IDENTIFIER, tmpString));
                }
            } else if (ch <= '9' && ch >= '0') { //数字常量
                String tmpString = ch + "";
                while ((ch = getNext(source)) != 0) {
                    if (ch <= '9' && ch >= '0') {
                        tmpString += ch;
                    } else {
                        //cur_index --;
                        break;
                    }
                }
                if (ch == '.') { //浮点型常量
                    tmpString += ch;
                    while ((ch = getNext(source)) != 0) {
                        if (ch <= '9' && ch >= '0') {
                            tmpString += ch;
                        } else {
                            cur_index --; //读完了
                            break;
                        }
                    }
                } else {
                    cur_index --;
                    resultList.add(new Token(Type.NUMBER, tmpString));
                }
            } else if (ch == '\"') { //文本字符串
                StringBuilder temString = new StringBuilder();
                ch = getNext(source);
                while (ch != '\"') {
                    temString.append(ch);
                    ch = getNext(source);
                }
                //cur_index --;
                resultList.add(new Token(Type.TEXT, temString.toString()));
            } else if (ch == '\'') { //字符常量
                String temString = "";
                ch = getNext(source);
                if (ch == '\\') { //转义字符
                    ch = getNext(source);
                    temString += ch;
                    ch = getNext(source);
                    if ((ch != '\'')) {
                        System.out.println("识别错误");
                    } else {
                        resultList.add(new Token(Type.CHARACTER, temString));
                    }
                }
            } else if (ch == '/') { //注释，除号
                ch = getNext(source);
                if (ch == '/') { //c++型注释
                    while ((ch = getNext(source)) != 0) { //注释内不扫描
                        if (ch == '\n' || ch == '\r') {
                            break;
                        }
                    }
                    resultList.add(new Token(Type.NOTE1, ""));
                } else if (ch == '*') { //c型注释
                    while ((ch = getNext(source)) != 0) {
                        if (ch == '*') {
                            ch = getNext(source);
                            if (ch == '/') { //注释块结束
                                break;
                            } else {
                                cur_index --;
                            }
                        }
                    }
                    resultList.add(new Token(Type.NOTE2, ""));
                } else if (ch == '=') { //除等于
                    resultList.add(new Token(Type.DIV_BY, ""));
                } else { //除号
                    cur_index --;
                    resultList.add(new Token(Type.DIV, ""));
                }
            } else if (ch == '=') { //=、==
                ch = getNext(source);
                if (ch == '=') {
                    resultList.add(new Token(Type.EQUAL, ""));
                } else {
                    cur_index --;
                    resultList.add(new Token(Type.ASSIGN, ""));
                }
            } else if (ch == '<') { //<、<=、<>、<<、<<=
                ch = getNext(source);
                if (ch == '=') {
                    resultList.add(new Token(Type.LE, ""));
                } else if (ch == '<') {
                    ch = getNext(source);
                    if (ch == '=') {
                        resultList.add(new Token(Type.LEFT_SHIFT_BY, ""));
                    } else {
                        cur_index --;
                        resultList.add(new Token(Type.LEFT_SHIFT, ""));
                    }
                } else {
                    cur_index --;
                    resultList.add(new Token(Type.LT, ""));
                }

            } else if (ch == '>') { //>、>=、>>、>>=
                ch = getNext(source);
                if (ch == '=') {
                    resultList.add(new Token(Type.GE, ""));
                } else if (ch == '>') {
                    ch = getNext(source);
                    if (ch == '=') {
                        resultList.add(new Token(Type.RIGHT_SHIFT_BY, ""));
                    } else {
                        cur_index --;
                        resultList.add(new Token(Type.RIGHT_SHIFT, ""));
                    }
                } else {
                    cur_index --;
                    resultList.add(new Token(Type.GT, ""));
                }
            } else if (ch == '+') { //+、++、+=
                ch = getNext(source);
                if (ch == '=') { //加等于
                    resultList.add(new Token(Type.ADD_BY, ""));
                } else if (ch == '+') { //自增
                    resultList.add(new Token(Type.INCREASE, ""));
                } else { //加运算
                    cur_index --;
                    resultList.add(new Token(Type.ADD, ""));
                }
            } else if (ch == '-') { //-、--、-=
                ch = getNext(source);
                if (ch == '=') { //减等于
                    resultList.add(new Token(Type.SUB_BY, ""));
                } else if (ch == '-') { //自减
                    resultList.add(new Token(Type.DECREASE, ""));
                } else { //减运算
                    cur_index --;
                    resultList.add(new Token(Type.SUB, ""));
                }
            } else if (ch == '*') { //*、*=
                ch = getNext(source);
                if (ch == '=') { //乘等于
                    resultList.add(new Token(Type.MUL_BY, ""));
                } else { //乘
                    cur_index--;
                    resultList.add(new Token(Type.MUL, ""));
                }
            } else if (ch == '|') { //|、|=、||
                ch = getNext(source);
                if (ch == '=') { //按位或等于
                    resultList.add(new Token(Type.BITWISE_OR_BY, ""));
                } else if (ch == '&') { //逻辑或
                    resultList.add(new Token(Type.OR, ""));
                } else { //按位或
                    cur_index --;
                    resultList.add(new Token(Type.BITWISE_OR, ""));
                }
            } else if (ch == '&') { //&、&=、&&
                ch = getNext(source);
                if (ch == '=') { //按位与等于
                    resultList.add(new Token(Type.BITWISE_AND_BY, ""));
                } else if (ch == '&') { //逻辑与
                    resultList.add(new Token(Type.AND, ""));
                } else { //按位与
                    cur_index --;
                    resultList.add(new Token(Type.BITWISE_AND, ""));
                }
            } else if (ch == '!') { //!、!=
                ch = getNext(source);
                if (ch == '=') { // 不等于
                    resultList.add(new Token(Type.NE, ""));
                } else { //逻辑非
                    cur_index --;
                    resultList.add(new Token(Type.NOT, ""));
                }
            } else if (ch == '^') { //按位异或
                resultList.add(new Token(Type.XOR, ""));
            } else if (ch == '#') { //界符
                resultList.add(new Token(Type.POUND, ""));
            } else if (ch == ',') {
                resultList.add(new Token(Type.COMMA, ""));
            } else if (ch == ';') {
                resultList.add(new Token(Type.SEMICOLON, ""));
            } else if (ch == '(') {
                resultList.add(new Token(Type.PARENTHESIS_L, ""));
            } else if (ch == ')') {
                resultList.add(new Token(Type.PARENTHESIS_R, ""));
            } else if (ch == '[') {
                resultList.add(new Token(Type.BRACKET_L, ""));
            } else if (ch == ']') {
                resultList.add(new Token(Type.BRACKET_R, ""));
            } else if (ch == '{') {
                resultList.add(new Token(Type.BRACE_L, ""));
            } else if (ch == '}') {
                resultList.add(new Token(Type.BRACE_R, ""));
            }
        }
    }

    //判断是否关键字
    public boolean isKeyword(String str) {
        for (String keyword : keywords) {
            if (keyword.equals(str)) {
                return true;
            }
        }
        return false;
    }

    //取字符串下一个
    public static char getNext(String str){
        if (cur_index >= str.length()) {
            cur_index++;
            return 0;
        } else {
            return str.charAt(cur_index++);
        }
    }
    private static int cur_index;   //字符串下标位置

    private static String[] keywords ={
            "auto","double","int","struct","break","else","long","switch",
            "case","enum","register","typedef","char","return","union","const",
            "extern","float","short","unsigned","continue","for","signed","void",
            "default","goto","sizeof","volatile","do","if","static","while"
    };
}
