public class WhileState implements Node{
    Node expression;
    Node bodyInWhile;
    public WhileState( Node expression, Node bodyInWhile ){
        this.expression = expression;
        this.bodyInWhile = bodyInWhile;
    }
    @Override
    public void prettyPrint( StringBuilder s ){
        s.append( expression );
        s.append( bodyInWhile );
    }
}
