import java.util.Map;

public class BinaryArithExpr implements Expr{
    private Expr left,right;
    private String op;
    public BinaryArithExpr( Expr left, String op, Expr right ){
        this.left = left;
        this.op = op;
        this.right = right;
    }
    public int eval( Map<String,Integer> bindings) throws EvalError{
        double lv = left.eval(bindings);
        double rv = right.eval( bindings );
        if(op.equals( "+" ))return (int) (lv + rv);
        if( op.equals( "-" ) )return (int) (lv - rv);
        if(op.equals( "*" ))return (int) (lv * rv);
        if( op.equals( "/" ) )return (int) (lv / rv);
        if( op.equals( "%" ) )return (int) (lv % rv);
        if( op.equals( "^" ) )return (int) Math.pow( lv,rv );
        throw new EvalError( "Unknown op " + op  );
    }

    @Override
    public void prettyPrint( StringBuilder s ){
        s.append( "(" );
        left.prettyPrint( s );
        s.append( op );
        right.prettyPrint( s );
        s.append( ")" );
    }
}
