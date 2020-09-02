package parser;

import domain.Production;
import domain.Token;
import utils.MyFileReader4;

import java.util.ArrayList;

/**
 * @description: 对测试样例文件
 *               用写好的工具类转化为词法分析器的输入类型（Token列表）
 *
 * @author: ilyyfan
 * @createDate: 2020/5/20
 */
public class TestUnit {

    public static void main(String[] args) {
        String tokenFilename = "./file/lab4.txt";
        String productionFilename = "./file/pro.txt";
        ArrayList<Token> tokenList = MyFileReader4.getFileAsTokenList(tokenFilename);
        ArrayList<Production> productions = MyFileReader4.getFileAsProductionList(productionFilename);

        System.out.println("文法的产生式：");
        for (Production p : productions) {
            System.out.println(p.toString());
        }

        //实例化一个词法分析器
        OperatorPrecedence parser = new OperatorPrecedence(productions);
        //这个词法分析器的主要数据结构
        System.out.println("非终结符号集：");
        System.out.println(parser.vn);
        System.out.println("终结符号集：");
        System.out.println(parser.vt);
        System.out.println("FirstVt集：");
        System.out.println(parser.firstVt);
        System.out.println("LastVt集：");
        System.out.println(parser.lastVt);
        System.out.println("OPG矩阵：");
        System.out.print(" ");
        for (char c : parser.vt) {
            System.out.print(" " + c);
        }
        System.out.println();
        for (int j = 0; j < parser.vt.size(); j++) {
            System.out.print(parser.vt.get(j));
            for (int k = 0; k < parser.vt.size(); k++) {
                System.out.print(" " + parser.priorityMatrix[j][k]);
            }
            System.out.println();
        }

        //打印一下输入串
        System.out.println("输入单词串：");
        char[] tokens = new char[tokenList.size() + 1];
        int i = 0;
        for (Token t : tokenList) {
            tokens[i++] = t.value.toCharArray()[0];
        }
        tokens[i] = '#';//添加一个#作为结束
        System.out.println(tokens);

        //调用analyze()函数，传入单词串
        boolean b = parser.analyze(tokens);
        //打印返回的布尔值
        System.out.println(b);
    }
}
