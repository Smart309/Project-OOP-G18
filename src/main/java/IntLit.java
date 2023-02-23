import java.util.Map;

public class IntLit implements Expr{
    private long val;
    public IntLit(long val){
        this.val = val;
    }

    public int eval( Map<String, Integer> bingings ){
        return (int) val;
    }

    @Override
    public void prettyPrint( StringBuilder s ){
        s.append( val );
    }
}
