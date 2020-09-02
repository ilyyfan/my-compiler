package lexer;

import java.io.*;

/**
 * @description: 程序入口、词法分析器工作逻辑
 *               程序输入为带路径的文件名
 *               程序输出为经词法分析器分析后的单词列表
 * @author: ilyyfan
 * @createDate: 2020/5/12
 */
public class TestUnit {
    public static void main(String[] args) {

        //源文件、目标文件路径
        String sourceFile = "./file/lab1.c";
        String objectFile = "./file/lab1.txt";

        //新建词法分析器（源文件路径作为输入）
        Lexer lexer = null;
        try {
            lexer = new Lexer(sourceFile);
            lexer.analyze();//本来想设计成懒加载的，没弄好，有时间再优化
        } catch (Exception e) {
            e.printStackTrace();
        }

        //输出结果到目标文件
        try {
            PrintWriter pw = new PrintWriter(new File(objectFile));
            assert lexer != null;
            for (Token t : lexer.getResultList()) {
                pw.println(t.toString());
                //打印到控制台方便看结果和调试
                System.out.println(t.toString());
            }
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
