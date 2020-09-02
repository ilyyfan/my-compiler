package utils;

import domain.Production;
import domain.Token;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description: 读文件为语法分析器需要的格式
 * @author: ilyyfan
 * @createDate: 2020/6/14
 */
public class MyFileReader4 {
    /**
     * 读文件为单词序列
     * @param path
     * @return ArrayList<Token>
     */
    public static ArrayList<Token> getFileAsTokenList(String path) {
        ArrayList<Token> res = new ArrayList<>();
        String reg1 = "\\((.*?),";
        String reg2 = "\"(.*?)\"";
        String line;
        int type;
        String value;
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(path)));
            while ((line = br.readLine()) != null) {
                Pattern pattern = Pattern.compile(reg1);
                Matcher matcher = pattern.matcher(line);
                Pattern pattern1 = Pattern.compile(reg2);
                Matcher matcher2 = pattern1.matcher(line);
                if (matcher.find() && matcher2.find()) {
                    type = Integer.parseInt(matcher.group(1));
                    value = matcher2.group(1);
                    res.add(new Token(type, value));
                } else {
                    System.out.println("输入错误");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static ArrayList<Production> getFileAsProductionList(String path) {
        ArrayList<Production> res = new ArrayList<>();
        String line;
        char left;
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(path)));
            while ((line = br.readLine()) != null) {
                left = line.charAt(0);
                int rightStart = 3;
                int rightEnd = rightStart;
                while (true) {
                    if (rightEnd == line.length()) {
                        res.add(new Production(left, line.substring(rightStart, rightEnd).toCharArray()));
                        break;
                    }
                    if (line.charAt(rightEnd) == '|') {
                        res.add(new Production(left, line.substring(rightStart, rightEnd).toCharArray()));
                        rightStart = rightEnd + 1;
                    }
                    rightEnd ++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
}
