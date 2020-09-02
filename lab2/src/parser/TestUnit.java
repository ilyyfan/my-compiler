package parser;

import utils.MyFileReader2;

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
        String filename = "./file/lab2.txt";
        ArrayList<Token> list = MyFileReader2.getFile(filename);
        int[] tokens = new int[list.size()];
        int i = 0;
        for (Token t : list) {
            tokens[i++] = t.type;
        }
        RecursiveDescent parser = new RecursiveDescent(tokens);
        System.out.println(parser.judge());
    }
}
