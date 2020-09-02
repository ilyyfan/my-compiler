package ll1;

/**
 * @description:
 * @author: ilyyfan
 * @createDate: 2020/6/14
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
