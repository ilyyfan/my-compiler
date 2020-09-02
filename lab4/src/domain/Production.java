package domain;

import java.util.Arrays;

/**
 * @description: 产生式
 * @author: ilyyfan
 * @createDate: 2020/6/15
 */
public class Production {

    public char left;
    public char[] right;

    public Production(char left, char[] right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return left + "->" + Arrays.toString(right);
    }
}
