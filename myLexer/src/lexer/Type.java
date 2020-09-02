package lexer;

/**
 * @description: 单词类别：
 *               一次一类：关键字、运算符、界限符
 *               其他类：常数、标识符
 * @author: ilyyfan
 * @createDate: 2020/5/13
 */
public class Type {
    /*C语言的32个关键字*/
    public static final int AUTO = 0;
    public static final int DOUBLE = 1;
    public static final int INT = 2;
    public static final int STRUCT = 3;
    public static final int BREAK = 4;
    public static final int ELSE = 5;
    public static final int LONG = 6;
    public static final int SWITCH = 7;
    public static final int CASE = 8;
    public static final int ENUM = 9;
    public static final int REGISTER = 10;
    public static final int TYPEDEF = 11;
    public static final int CHAR = 12;
    public static final int EXTERN = 13;
    public static final int RETURN = 14;
    public static final int UNION = 15;
    public static final int CONST = 16;
    public static final int FLOAT = 17;
    public static final int SHORT = 18;
    public static final int UNSIGNED = 19;
    public static final int CONTINUE = 20;
    public static final int FOR = 21;
    public static final int SIGNED = 22;
    public static final int VOID = 23;
    public static final int DEFAULT = 24;
    public static final int GOTO = 25;
    public static final int SIZEOF = 26;
    public static final int VOLATILE = 27;
    public static final int DO = 28;
    public static final int IF = 29;
    public static final int STATIC = 30;
    public static final int WHILE = 31;
    /*标识符*/
    public static final int IDENTIFIER = 40;
    /*常数*/
    public static final int NUMBER = 41;
    /*文本字符串*/
    public static final int TEXT = 42;
    /*字符常量*/
    public static final int CHARACTER = 43;
    /*运算符*/
    public static final int ASSIGN = 50;//=
    public static final int EQUAL = 51;//==
    public static final int ADD = 52;//+
    public static final int ADD_BY = 53;//+=
    public static final int INCREASE = 54;//++
    public static final int SUB = 55;//-
    public static final int SUB_BY = 56;//-=
    public static final int DECREASE = 57;//--
    public static final int MUL = 58;//*
    public static final int MUL_BY = 59;//*=
    public static final int DIV = 60;///
    public static final int DIV_BY = 61;///=
    public static final int LT = 62;//<
    public static final int LE = 63;//<=
    public static final int LEFT_SHIFT = 64;//<<
    public static final int LEFT_SHIFT_BY = 65;//<<=
    public static final int GT = 66;//>
    public static final int GE = 67;//>=
    public static final int RIGHT_SHIFT = 68;//>>
    public static final int RIGHT_SHIFT_BY = 69;//>>=
    public static final int BITWISE_OR = 70;//|
    public static final int BITWISE_OR_BY = 71;//|
    public static final int OR = 72;//||
    public static final int BITWISE_AND = 73;//&
    public static final int BITWISE_AND_BY = 74;//&=
    public static final int AND = 75;//&&
    public static final int XOR = 76;//^
    public static final int NOT = 77;//!
    public static final int NE = 78;//!=
    /*界符*/
    public static final int COMMA = 80;//,
    public static final int SEMICOLON = 81;//;
    public static final int BRACE_L = 82;//{
    public static final int BRACE_R = 83;//}
    public static final int BRACKET_L = 84;//[
    public static final int BRACKET_R = 85;//]
    public static final int PARENTHESIS_L = 86;//(
    public static final int PARENTHESIS_R = 87;//)
    public static final int POUND = 88;//#
    /*注释*/
    public static final int NOTE1 = 90;////
    public static final int NOTE2 = 91;///**/

}
