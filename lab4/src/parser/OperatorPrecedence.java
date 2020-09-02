package parser;

import domain.Production;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @description: 算符优先语法分析器
 *               构造参数是一系列产生式 ArrayList<Production>
 *               核心功能函数是分析函数 analyze()
 *                  该函数传递的参数一个单词序列 ArrayList<Token>
 *                  返回一个布尔值，表示这个序列是否文法的语句
 *
 *               为了表意清楚和简化编程难度，输入的二元式列表只取第二项即value
 *                  所有analyze的输入类型是char[]，命名为tokens表示输入串
 * @author: ilyyfan
 * @createDate: 2020/6/15
 */
public class OperatorPrecedence {

    public ArrayList<Character> vn;
    public ArrayList<Character> vt;
    public HashMap<Character, ArrayList<Character>> firstVt;
    public HashMap<Character, ArrayList<Character>> lastVt;
    public char[][] priorityMatrix;  //优先矩阵

    public OperatorPrecedence(ArrayList<Production> productions) {
        vn = calculateVn(productions);
        vt = calculateVt(productions);
        firstVt = calculateFirstVt(productions);
        lastVt = calculateLastVt(productions);
        priorityMatrix = new char[vt.size()][vt.size()];
        calculatePriorityMatrix(productions);
    }

    private void calculatePriorityMatrix(ArrayList<Production> productions) {
        for (Production p : productions) {
            for (int i = 0; i < p.right.length - 1; i++) {
                if (isVt(p.right[i])) {
                    if (isVt(p.right[i+1])) {
                        priorityMatrix[idxOfVt(p.right[i])][idxOfVt(p.right[i+1])] = '=';
                    } else {
                        for (Character b : firstVt.get(p.right[i+1])) {
                            priorityMatrix[idxOfVt(p.right[i])][idxOfVt(b)] = '<';
                        }
                    }
                } else if (isVt(p.right[i+1])) {
                    for (Character a : lastVt.get(p.right[i])) {
                        priorityMatrix[idxOfVt(a)][idxOfVt(p.right[i+1])] = '>';
                    }
                }
                if (i < p.right.length - 2) {
                    if (isVt(p.right[i]) && !isVt(p.right[i+1]) && isVt(p.right[i+2])) {
                        priorityMatrix[idxOfVt(p.right[i])][idxOfVt(p.right[i+2])] = '=';
                    }
                }
            }
        }
        int idxOfHash = idxOfVt('#');
        priorityMatrix[idxOfHash][idxOfHash] = '=';
        char startOfGrammar = productions.get(0).left;
        for (Character c : firstVt.get(startOfGrammar)) {
            priorityMatrix[idxOfHash][idxOfVt(c)] = '<';
        }
        for (Character c : lastVt.get(startOfGrammar)) {
            priorityMatrix[idxOfVt(c)][idxOfHash] = '<';
        }
    }

    private HashMap<Character, ArrayList<Character>> calculateFirstVt(ArrayList<Production> productions) {
        HashMap<Character, ArrayList<Character>> map = new HashMap<>();
        // U=+>b… 或 U =+> Vb… b∈FirstVt(U)
        for (Character u : vn) {
            map.put(u, new ArrayList<>());
            for (Production p : productions) {
                if (p.left == u) { //每个非终结符的每条产生式
                    if (isVt(p.right[0])) {
                        map.get(u).add(p.right[0]);
                    } else if (p.right.length > 1) {
                        if (!isVt(p.right[0]) && isVt(p.right[1])) {
                            map.get(u).add(p.right[1]);
                        }
                    }
                }
            }
        }
        // U->V 则 FirstVt(U) += FirstVt(V)
        for (int i = vn.size() - 1; i >= 0; i--) {
            Character u = vn.get(i);
            for (Production p : productions) {
                if (p.left == u) { //每个非终结符的每条产生式
                    for (Character v : vn) {
                        if (p.right[0] == v) { // U->V
                            ArrayList<Character> firstV = map.get(v);
                            for (Character c : firstV) {
                                if (!map.get(u).contains(c)) {
                                    map.get(u).add(c);
                                }
                            }
                        }
                    }
                }
            }
        }
        return map;
    }

    private HashMap<Character, ArrayList<Character>> calculateLastVt(ArrayList<Production> productions) {
        HashMap<Character, ArrayList<Character>> map = new HashMap<>();
        // U=+>…a 或 U=+>…aV a∈LastVt(U)
        for (Character u : vn) {
            map.put(u, new ArrayList<>());
            for (Production p : productions) {
                if (p.left == u) { //每个非终结符的每条产生式
                    int endIdx = p.right.length - 1;
                    if (isVt(p.right[endIdx])) {
                        map.get(u).add(p.right[endIdx]);
                    } else if (p.right.length > 1) {
                        if (!isVt(p.right[endIdx]) && isVt(p.right[endIdx - 1])) {
                            map.get(u).add(p.right[endIdx - 1]);
                        }
                    }
                }
            }
        }
        // U->V 则 LastVt(U) += LastVt(V)
        for (int i = vn.size() - 1; i >= 0; i--) {
            Character u = vn.get(i);
            for (Production p : productions) {
                if (p.left == u) { //每个非终结符的每条产生式
                    for (Character v : vn) {
                        int endIdx = p.right.length - 1;
                        if (p.right[endIdx] == v) { // U->V
                            ArrayList<Character> lastV = map.get(v);
                            for (Character c : lastV) {
                                if (!map.get(u).contains(c)) {
                                    map.get(u).add(c);
                                }
                            }
                        }
                    }
                }
            }
        }
        return map;
    }

    private boolean isVt(char c) {
        for (Character ch : vt) {
            if (c == ch)
                return true;
        }
        return false;
    }

    private int idxOfVt(char c) {
        for (int i = 0; i < vt.size(); i++) {
            if (c == vt.get(i))
                return i;
        }
        return -1;
    }

    /**
     * 对输入的单词序列，判断是否文法的语句
     * 实例化词法分析器时已计算出文法的算符优先矩阵
     */
    public boolean analyze(char[] tokens) {
        StringBuilder sb = new StringBuilder(String.valueOf(tokens));
        String s = sb.toString();
        char Q;
        int k=1,j;
        char[] stack =new char[50];//分析栈内容
        stack[k]='#';
        boolean success=true;
        int i=-1;
        System.out.println("------------------分析开始！------------------");

        System.out.println("\t分析栈\t\t\t\t剩余符号串\t\t\t\t动作");
        do {
            i ++;
            if(isVt(stack[k])) {
                j = k;
            } else {
                j = k - 1;
            }

            while (getPriorityRelationship(stack[j], s.charAt(i)) == '>') {
                do {
                    Q = stack[j];
                    if(isVt(stack[j - 1])) {
                        j = j - 1;
                    } else {
                        j = j - 2;
                    }
                } while(getPriorityRelationship(stack[j], Q) != '<');

                for (int i1 = 0; i1 <= k; i1++) {
                    System.out.print(stack[i1] + " ");
                }
                System.out.print("\t\t\t\t");

                for (int i1 = i; i1 < s.length(); i1++) {
                    System.out.print(s.charAt(i1)+" ");
                }
                System.out.print("\t\t\t\t规约");
                for (int i1 = j + 1; i1 <= k; i1 ++) {
                    System.out.print(stack[i1] + " ");
                }
                System.out.println();
                k = j + 1;
                if (stack[k] == '(' && stack[k + 1] == ')') {
                    System.out.println("( 与 ) 之间缺少表达式或者变量");
                    success = false;
                }
                stack[k] = 'N';

                for (int i1 = 0; i1 <= k; i1 ++) {
                    System.out.print(stack[i1] + " ");
                }
                System.out.print("\t\t\t\t");

                for(int i1 = i; i1<s.length(); i1 ++) {
                    System.out.print(s.charAt(i1) + " ");
                }
                System.out.print("\n");
            }
            char flag1 = getPriorityRelationship(stack[j], s.charAt(i));
            if(flag1 == '<' || flag1 == '=') {
                k=k+1;
                stack[k]=s.charAt(i);
            } else {
                System.out.print("Error");
                success=false;

            }

        } while(s.charAt(i)!='#');
        
        return success;
    }

    private char getPriorityRelationship(char c1, char c2) {
        return priorityMatrix[idxOfVt(c1)][idxOfVt(c2)];
    }

    /**
     * 文法的非终结符号集
     */
    public ArrayList<Character> calculateVn(ArrayList<Production> productions) {
        ArrayList<Character> res = new ArrayList<>();
        for (Production p : productions) {
            if (!res.contains(p.left))
                res.add(p.left);
        }
        return res;
    }

    /**
     * 终结符号集：
     * 1. 遍历Production.right得到文法所有符号
     * 2. 去掉非终结符
     * 3. 加上'#'符号
     */
    public ArrayList<Character> calculateVt(ArrayList<Production> productions) {
        ArrayList<Character> res = new ArrayList<>();
        ArrayList<Character> vn = calculateVn(productions);
        for (Production p : productions) {
            for (char c : p.right) {
                if (!vn.contains(c)) {
                    if (!res.contains(c)) {
                        res.add(c);
                    }
                }
            }
        }
        res.add('#');
        return res;
    }
}
