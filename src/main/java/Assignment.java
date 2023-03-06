public class Assignment implements Node{
    String var;
    String s;
    Expr expr;
    public Assignment( String var, String s, Expr expr ){
        this.var =var; this.s=s; this.expr =expr;
    }
    @Override
    public void prettyPrint( StringBuilder s ){

    }
}
