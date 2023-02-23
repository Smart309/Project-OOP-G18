public class IfState implements Node{
    Node expression;
    Node thenStatement;
    Node elseStatement;
    public IfState( Node expression, Node thenStatement, Node elseStatement ){
        this.expression = expression;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }
    @Override
    public void prettyPrint( StringBuilder s ){
        s.append( expression );
        s.append( thenStatement );
        s.append( elseStatement );
    }
}
