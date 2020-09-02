package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @description: 工具类，读文件为字符串
 *                  由于jdk11字符串在编译时候有最大长度65534限制
 *                  导致我们的扫描器可处理的文件十分小
 *                  改进思路（尚未完成）：
 *                  由于词法分析工作是按字符分析处理
 *                  我们可以直接把文件转化为没有大小限制的字符数组而非字符串
 * @author: ilyyfan
 * @createDate: 2020/5/12
 */
public class MyFileReader {
    public static String getFile(String path) {
        StringBuilder sb = new StringBuilder();
        String line = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(path)));
            while ((line = br.readLine()) != null) {
                sb.append(line).append('\n');
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
