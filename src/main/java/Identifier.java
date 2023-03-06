import java.util.Map;

public class Identifier implements Expr{
    private String consume;
    public Identifier( String consume ){
        this.consume = consume;
    }

    @Override
    public void prettyPrint( StringBuilder s ){

    }

    @Override
    public int eval( Map<String, Integer> bindings ) throws EvalError{
        return 0;
    }
}
