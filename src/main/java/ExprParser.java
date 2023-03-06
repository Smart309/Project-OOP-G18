import java.util.LinkedList;

public class ExprParser implements Parser{
    private Tokenizer tkz;
    public ExprParser( Tokenizer tkz){
        this.tkz = tkz;
    }


    public static boolean isIdentifier(String s){
        //Check that the string is not a reserved word
        switch (s)
        {
            case "collect":
            case "done":
            case "down":
            case "downleft":
            case "downright":
            case "else":
            case "if":
            case "invest":
            case "move":
            case "nearby":
            case "opponent":
            case "relocate":
            case "shoot":
            case "then":
            case "up":
            case "upleft":
            case "upright":
            case "while":
                return false;
            default:
                break;
        }
        // Check that the string starts with a letter
        char firstChar = s.charAt(0);
        return Character.isLetter( firstChar );
    }

    private boolean isSpecialVar(String s){
        // Check that the string is special variables
        switch (s)
        {
            case "rows":
            case "cols":
            case "currow":
            case "curcol":
            case "budget":
            case "deposit":
            case "int":
            case "maxdeposit":
            case "random":
                return true;
            default:
                return false;
        }
    }

    public Node parse() throws SyntaxError, LexicalError, EvalError{
        Node n = parsePlan();
        if( tkz.hasNextToken() ){
            throw new SyntaxError( "leftover token" );
        }
        return n;
    }
    //Plan -> Statement+
    private Node parsePlan() throws SyntaxError, LexicalError,EvalError{
        if( !tkz.hasNextToken() ){
            throw new SyntaxError( "You must have a construction plan!" );
        }
        State all = new State(parseStatement(),null);
        State current = null;
        while( tkz.hasNextToken() ){
            if( all.getNext() == null ){
                current = new State( parseStatement(),null );
                all.nextState( current );
            }
            State next = new State( parseStatement(),null );
            assert current != null;
            current.nextState( next );
            current = next;
        }
        return all;
    }

    // Statement -> Command | BlockStatement | IfStatement | WhileStatement
    private Node parseStatement() throws SyntaxError, LexicalError,EvalError{
        if(tkz.peek("{")){
            return parseBlockStatement();
        }else if( tkz.peek("if") ){
            return parseIfStatement();
        }else if( tkz.peek("while") ){
            return parseWhileStatement();
        }else{
            return parseCommand();
        }
    }

    // Command -> AssignmentStatement | ActionCommand
    private Node parseCommand() throws SyntaxError, LexicalError{
        if( isIdentifier( tkz.peek() ) ){
            return parseAssignmentStatement();
        }else{
            return parseActionCommand();
        }
    }

    // ActionCommand -> done | relocate | MoveCommand | RegionCommand | AttackCommand
    private Node parseActionCommand()throws SyntaxError, LexicalError{
        if(tkz.peek("done")){
            tkz.consume();
            return new Done();
        }else if( tkz.peek("relocate") ){
            tkz.consume();
            return new Relocate();
        }else if( tkz.peek("move") ){
            tkz.consume();
            return parseMove();
        }else if( tkz.peek("invest")||tkz.peek("collect") ){
            return parseRegion();
        }else if(tkz.peek("shoot")){
            tkz.consume();
            return parseAttack();
        }else{
            throw new SyntaxError( "Unknown command" + tkz );
        }
    }

    // AttackCommand -> shoot Direction Expression
    private Node parseAttack() throws LexicalError, SyntaxError{
        return new Attack(parseDirection(),parseExpression());
    }

    // RegionCommand -> invest Expression | collect Expression
    private Node parseRegion() throws LexicalError, SyntaxError{
        return new RegionCommand(tkz.consume(),parseExpression());
    }

    // MoveCommand -> move Direction
    private Node parseMove() throws LexicalError{
        return new Move(parseDirection());
    }

    // Direction -> up | down | upleft | upright | downleft | downright
    private Node parseDirection() throws LexicalError{
        String direction = tkz.consume();
        return switch( direction ){
            case "up" -> Direction.up;
            case "down" -> Direction.down;
            case "upleft" -> Direction.upleft;
            case "upright" -> Direction.upright;
            case "downleft" -> Direction.downleft;
            case "downright" -> Direction.downright;
            default -> throw new AssertionError( "Unknown direction: " + direction );
        };
    }

    // AssignmentStatement -> <identifier> = Expression
    private Node parseAssignmentStatement()throws SyntaxError, LexicalError{
        String var = tkz.consume();
        if(isSpecialVar( var )){
            return new Identifier( var );
        }else{
            tkz.consume("=");
            return new Assignment( var,"=", parseExpression());
        }
    }

    // Expression -> Expression + Term | Expression - Term | Term
    private Expr parseExpression()throws SyntaxError, LexicalError{
        Expr e = parseTerm();
        while( tkz.hasNextToken()&&(tkz.peek("+")||tkz.peek("-")) ){
            e = new BinaryArithExpr(e,tkz.consume(),parseTerm());
        }
        return e;
    }

    // Term -> Term * Factor | Term / Factor | Term % Factor | Factor
    private Expr parseTerm()throws SyntaxError, LexicalError{
        Expr e = parseFactor();
        while( tkz.hasNextToken()&&(tkz.peek("*")||tkz.peek("/")||tkz.peek("%")) ){
            if(tkz.peek("*")){
                tkz.consume();
                e = new BinaryArithExpr( e,"*",parseFactor() );
            }else if( tkz.peek("/") ){
                tkz.consume();
                e = new BinaryArithExpr( e,"/",parseFactor() );
            }else if( tkz.peek("%") ){
                tkz.consume();
                e = new BinaryArithExpr( e,"%",parseFactor() );
            }
        }
        return e;
    }

    // Factor -> Power ^ Factor | Power
    private Expr parseFactor()throws SyntaxError, LexicalError{
        Expr e = parsePower();
        while( tkz.peek("^") ){
            tkz.consume();
            e = new BinaryArithExpr( e,"^",parseFactor() );
        }
        return e;
    }

    // Power -> <number> | <identifier> | ( Expression ) | InfoExpression
    // InfoExpression -> opponent | nearby Direction
    private Expr parsePower()throws SyntaxError, LexicalError{
        if (tkz.peek().matches(".*[0-9].*"))
        {
            return new IntLit(Long.parseLong(tkz.consume()));
        }
        else if(tkz.peek("opponent"))
        {
            return new InfoExpr(tkz.consume(), null);
        }
        else if (tkz.peek("nearby"))
        {
            return new InfoExpr(tkz.consume(), parseDirection());
        }
        else if (tkz.peek().matches(".*[A-Za-z].*"))
        {
            return new Identifier(tkz.consume());
        }
        else if (tkz.peek("("))
        {
            tkz.consume();
            Expr e = parseExpression();
            tkz.consume(")");
            return e;
        }
        else
        {
            throw new SyntaxError("Unexpected token: " + tkz.peek());
        }
    }

    // BlockStatement -> { Statement* }
    private Node parseBlockStatement()throws SyntaxError, LexicalError,EvalError{
        LinkedList <Node> statement = new LinkedList<>();
        tkz.consume("{");
        while( !tkz.peek("}") ){
            statement.add(parseStatement());
        }
        tkz.consume("}");
        return new BlockState(statement);
    }

    // IfStatement -> if ( Expression ) then Statement else Statement
    private Node parseIfStatement()throws SyntaxError, LexicalError,EvalError{
        tkz.consume("if");
        tkz.consume("(");
        Node expression = parseExpression();
        tkz.consume(")");
        tkz.consume("then");{
        Node thenStatement = parseStatement();
        tkz.consume("else");
        Node elseStatement = parseStatement();
        return new IfState(expression,thenStatement,elseStatement);
        }
    }

    // WhileStatement -> while ( Expression ) Statement
    private Node parseWhileStatement()throws SyntaxError, LexicalError,EvalError{
        tkz.consume("while");
        tkz.consume("(");
        Node expression = parseExpression();
        tkz.consume(")");
        Node bodyInWhile = parseStatement();
        return new WhileState(expression,bodyInWhile);
    }
}
