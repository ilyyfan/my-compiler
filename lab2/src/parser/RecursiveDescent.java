package parser;

/**
 * @description: 对输入的单词串（二元式序列）用下面文法进行递归下降的语法分析
 *               返回布尔值
 *      文法G[B]:
 *              B→V=E
 *              E→TE′
 *              E′→ATE′|ε
 *              T→FT′
 *              T′→MFT′|ε
 *              F→(E)|i
 *              A→+|-
 *              M→*|/
 *              V→i
 *      设计思路：
 *              每个词法分析器实例设计为可以分析单文件
 *                  私有成员变量有，一个Token列表和一个指针
 *                  构造函数参数为待分析文件文件名
 *              主要方法是布尔函数judge()，它调用文法开始符号对应的函数B()
 *                  judge()的返回值就是递归分析的成功/失败结果
 *                  递归下降分析过程中把文法语句打印到控制台方便观察调试
 *      改进化简：
 *              输入二元式列表中的第二项在这个文法的分析过程中没有意义
 *              可以忽略，词法分析器的输入化简为整型数组
 * @author: ilyyfan
 * @createDate: 2020/5/20
 */
public class RecursiveDescent {

    private int[] tokens;
    private int size;
    private int curToken;

    public RecursiveDescent(int[] tokens) {
        curToken = -1;
        this.tokens = tokens;
        size = tokens.length;
    }

    public boolean judge() {
        advance();
        B();
        return eof();
    }

    //B->V=E
    public void B() {
        V();
        if (matches(Type.EQ)) {
            advance();
            E();
        } else {
            System.out.println("'=' expected");
        }
    }

    //E->TE'
    public void E() {
        T();
        E1();
    }

    //E'->ATE'|ε
    public void E1() {
        if(matches(Type.ADD) || matches(Type.SUB)) {
            A();
            T();
            E1();
        }
    }

    //T->FT'
    public void T() {
        F();
        T1();
    }

    //T'->MFT'|ε
    public void T1() {
        if (matches(Type.MUL) || matches(Type.DIV)) {
            M();
            F();
            T1();
        }
    }

    //F->(E)|i
    public void F() {
        if (matches(Type.ID)) {
            advance();
        } else if (matches(Type.BL)) {
            advance();
            E();
            if (matches(Type.BR)) {
                advance();
            } else {
                System.out.println("')' expected");
            }
        } else {
            System.out.println("number or identifier expected");
        }
    }

    //A->+|-
    public void A() {
        if (matches(Type.ADD) || matches(Type.SUB)) {
            advance();
        } else {
            System.out.println("'+' or '-' expected");
        }
    }

    //M->*|/
    public void M() {
        if (matches(Type.MUL) || matches(Type.DIV)) {
            advance();
        } else {
            System.out.println("'*' or '/' expected");
        }
    }

    //V->i
    public void V() {
        if (matches(Type.ID)) {
            advance();
        } else {
            System.out.println("identifier expected");
        }
    }

    public void advance() {
        curToken ++;
    }

    public void retreat() {
        curToken --;
    }

    public boolean matches(int type) {
        if (curToken < size)
            return tokens[curToken] == type;
        return false;
    }

    private boolean eof() {
        return curToken == size;
    }
}
