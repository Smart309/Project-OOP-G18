import java.util.Map;

public class InfoExpr implements Expr{
    public InfoExpr(String consume, Object o ){
    }

    @Override
    public void prettyPrint( StringBuilder s ){

    }
    @Override
    public int eval( Map<String, Integer> bindings ) throws EvalError{
        return 0;
    }
}
