package ll1;

import java.util.Stack;

/**
 * @description: 对输入的单词串（二元式序列）用下面文法用预测分析法进行语法分析
 *      文法G[S]:
 *              S→V=E
 *              E→TE′
 *              E′→ATE′|ε
 *              T→FT′
 *              T′→MFT′|ε
 *              F→(E)|i
 *              A→+|-
 *              M→*|/
 *              V→i
 *      设计思路：
 *              题目要求完成表驱动程序
 *              输入是单词串，用ArrayList<Token>构造
 *              输出是一个布尔值，表示输入串是否该文法定义的算数表达式的判断结果
 *
 * @author: ilyyfan
 * @createDate: 2020/5/20
 */
public class LL1Parser {

    public static char[] vn = {'S', 'E', 'e', 'T', 't', 'F', 'A', 'M', 'V'};
    public static char[] vt = {'#', 'i', '=', '+', '-', '*', '/', '(', ')'};
    public static String[][] analyzeTable;
    public Stack<Character> analyzeStack;

    private static int curToken;

    /**
     * 构造分析器时初始化
     *      初始化分析表
     *      栈底符号'#'和文法开始符号vn[0]入分析栈
     *      初始化输入串指针（指示器）
     */
    public LL1Parser() {
        initTable();
        analyzeStack = new Stack<>();
        analyzeStack.push('#');
        analyzeStack.push(vn[0]);
        curToken = 0;
    }

    private void initTable() {
        analyzeTable = new String[9][9];
        //analyzeTable[i][j]表示M[vn[i],vt[j]]
        analyzeTable[0][1] = "S->V=E";
        analyzeTable[1][1] = "E->Te";
        analyzeTable[1][7] = "E->Te";
        analyzeTable[2][0] = "e->ε";
        analyzeTable[2][3] = "e->ATe";
        analyzeTable[2][4] = "e->ATe";
        analyzeTable[2][8] = "e->ε";
        analyzeTable[3][1] = "T->Ft";
        analyzeTable[3][7] = "T->Ft";
        analyzeTable[4][0] = "t->ε";
        analyzeTable[4][3] = "t->ε";
        analyzeTable[4][4] = "t->ε";
        analyzeTable[4][5] = "t->MFt";
        analyzeTable[4][6] = "t->MFt";
        analyzeTable[4][8] = "t->ε";
        analyzeTable[5][1] = "F->i";
        analyzeTable[5][7] = "F->(E)";
        analyzeTable[6][3] = "A->+";
        analyzeTable[6][4] = "A->-";
        analyzeTable[7][5] = "M->*";
        analyzeTable[7][6] = "M->/";
        analyzeTable[8][1] = "M->i";
    }

    public boolean analyze(char[] tokens) {
        char ai;
        char Xm;
        while (true) {
            //获取两指示器内容
            Xm = analyzeStack.peek();
            ai = tokens[curToken];
            //情况3
            if (Xm == '#' && ai =='#') {
                System.out.println("分析成功");
                return true;
            }
            //情况1
            if (isVt(Xm)) {
                if (Xm == ai) {
                    analyzeStack.pop();
                    advance();
                } else {
                    System.out.println("Syntax ERROR!");
                    return false;
                }
            }
            //情况2
            if (isVn(Xm)) {
                //查表获取产生式
                int iXm = searchIndex(vn, Xm);
                int iAi = searchIndex(vt, ai);
                String production = analyzeTable[iXm][iAi];
                if (production != null) {
                    //Xm退栈
                    analyzeStack.pop();
                    //产生式右端反序入栈
                    int idx = production.length() - 1;
                    char ch;
                    while (idx > 0) {
                        ch = production.charAt(idx --);
                        if (ch != '>' && ch != 'ε') {
                            analyzeStack.push(ch);
                        } else {
                            break;
                        }
                    }
                } else {
                    System.out.println("Syntax ERROR!");
                    return false;
                }
            }
        }
    }

    //Arrays.binarySearch 需要array有序
    private int searchIndex(char[] array, char ch) {
        for (int i = 0; i<array.length; i++) {
            if (array[i] == ch)
                return i;
        }
        return -1;
    }

    //字符是否文法的终结符
    public boolean isVt(Character ch) {
        for (Character c : vt) {
            if (c == ch)
                return true;
        }
        return false;
    }

    //字符是否文法的非终结符
    public boolean isVn(Character ch) {
        for (Character c : vn) {
            if (c == ch)
                return true;
        }
        return false;
    }

    //读取下一个单词
    public void advance() {
        curToken ++;
    }
}
