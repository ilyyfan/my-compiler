package ll1;

import utils.MyFileReader3;

import java.util.ArrayList;

/**
 * @description:
 * @author: ilyyfan
 * @createDate: 2020/6/14
 */
public class TestUnit {
    public static void main(String[] args) {
        String filename = "./file/lab3.txt";
        ArrayList<Token> list = MyFileReader3.getFile(filename);
        char[] tokens = new char[list.size() + 1];
        int i = 0;
        for (Token t : list) {
            tokens[i++] = t.value.toCharArray()[0];
        }
        tokens[i] = '#';//添加一个#作为结束
        LL1Parser parser = new LL1Parser();
        System.out.println(parser.analyze(tokens));
    }
}
