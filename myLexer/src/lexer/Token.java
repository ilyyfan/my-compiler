package lexer;

/**
 * @description: 单词
 * @author: ilyyfan
 * @createDate: 2020/5/12
 */
public class Token {
    public int type;
    public String value;
    public Token(int type,String value){
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return "(" + type + ", \"" + value + '\"' + ')';
    }
}
