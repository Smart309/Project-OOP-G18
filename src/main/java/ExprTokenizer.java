import java.util.NoSuchElementException;

public class ExprTokenizer implements Tokenizer {
    private String src,next; private int pos;
    public ExprTokenizer(String src){
        this.src = src; pos = 0;
        computeNext();
    }
    public boolean hasNextToken(){
        return next != null;
    }
    public String peek(){
        if(!hasNextToken()) throw new NoSuchElementException("no more token");
        return next;
    }
    public boolean peek(String s) {
        return peek().equals(s);
    }
    public String consume(){
        if(!hasNextToken()) throw new NoSuchElementException("no more tokens");
        String result = next;
        computeNext();
        return result;
    }
    public void consume(String s)throws SyntaxError {
        if (peek(s))
            consume();
        else
            throw new SyntaxError(s + " expected");
    }
    private void computeNext(){
        StringBuilder s = new StringBuilder();
        while( pos < src.length() && Character.isSpaceChar( src.charAt( pos ) ) ){
            pos++;
        }
        if(pos == src.length()){
            next = null;
            return;
        }
        char c = src.charAt( pos );
        if(Character.isDigit( c )){
            s.append( c );
            for(pos++ ; pos< src.length() && Character.isDigit( src.charAt( pos )) ; pos++){
                s.append( src.charAt( pos ) );
            }
        }else if(c == '}' || c == '{' || c == '+' || c == '-' || c == '(' || c == ')' || c == '^' || c == '*' || c == '/' ||  c == '=' ||  c == '%'){
            s.append( c );
            pos++;
        }else if( ((c == 't') && !(src.charAt( pos+1 ) == 'h')) || (c == 'm' && !(src.charAt( pos+1 ) == 'o'))){
            s.append( c );
            pos++;
        }else if( Character.isLetter( c ) ){
            int n = 0;
            StringBuilder temp = new StringBuilder();
            if(c == 'w'){ //while
                n = 4;
                pos++;
                temp.append( c );
                for(int i=0 ; pos< src.length() && i<n ; i++){
                    temp.append( src.charAt( pos ) );
                    pos++;
                }
                if(temp.toString().equals( "while" )){
                    s.append( temp );
                }else{
                    throw new IllegalArgumentException( "Illegal character: " + temp);
                }
            }else if( c == 'd' &&  src.charAt( pos+1 ) == 'e'){ //deposit
                n = 6;
                pos++;
                temp.append( c );
                for(int i=0 ; pos< src.length() && i<n ; i++){
                    temp.append( src.charAt( pos ) );
                    pos++;
                }
                if(temp.toString().equals( "deposit" )){
                    s.append( temp );
                }else{
                    throw new IllegalArgumentException( "Illegal character: " + temp);
                }
            }else if( c == 'i' && !(src.charAt( pos+1 ) == 'n')){ //if
                n = 1;
                pos++;
                temp.append( c );
                for(int i=0 ; pos< src.length() && i<n ; i++){
                    temp.append( src.charAt( pos ) );
                    pos++;
                }
                if(temp.toString().equals( "if" )){
                    s.append( temp );
                }else{
                    throw new IllegalArgumentException( "Illegal character: " + temp);
                }
            }else if( c == 'e' ){ //else
                n = 3;
                pos++;
                temp.append( c );
                for(int i=0 ; pos< src.length() && i<n ; i++){
                    temp.append( src.charAt( pos ) );
                    pos++;
                }
                if(temp.toString().equals( "else" )){
                    s.append( temp );
                }else{
                    throw new IllegalArgumentException( "Illegal character: " + temp);
                }
            }else if( c == 'c' && src.charAt( pos+2 ) == 'l'){ // collect
                n = 6;
                pos++;
                temp.append( c );
                for(int i=0 ; pos< src.length() && i<n ; i++){
                    temp.append( src.charAt( pos ) );
                    pos++;
                }
                if(temp.toString().equals( "collect" )){
                    s.append( temp );
                }else{
                    throw new IllegalArgumentException( "Illegal character: " + temp);
                }
            }else if( c == 't' && src.charAt( pos+1 ) == 'h'){ // then
                n = 3;
                pos++;
                temp.append( c );
                for(int i=0 ; pos< src.length() && i<n ; i++){
                    temp.append( src.charAt( pos ) );
                    pos++;
                }
                if(temp.toString().equals( "then" )){
                    s.append( temp );
                }else{
                    throw new IllegalArgumentException( "Illegal character: " + temp);
                }
            }else if( c == 'b' ){ //budget
                n = 5;
                pos++;
                temp.append( c );
                for(int i=0 ; pos< src.length() && i<n ; i++){
                    temp.append( src.charAt( pos ) );
                    pos++;
                }
                if(temp.toString().equals( "budget" )){
                    s.append( temp );
                }else{
                    throw new IllegalArgumentException( "Illegal character: " + temp);
                }
            }else if( c == 'i' && src.charAt( pos+1 ) == 'n'){ // invest
                n = 5;
                pos++;
                temp.append( c );
                for(int i=0 ; pos< src.length() && i<n ; i++){
                    temp.append( src.charAt( pos ) );
                    pos++;
                }
                if(temp.toString().equals( "invest" )){
                    s.append( temp );
                }else{
                    throw new IllegalArgumentException( "Illegal character: " + temp);
                }
            }else if( c == 'o' ){ // opponent and opponentLoc
                n = 7;
                pos++;
                temp.append( c );
                for(int i=0 ; pos< src.length() && i<n ; i++){
                    temp.append( src.charAt( pos ) );
                    pos++;
                }
                c = src.charAt( pos );
                if(c == 'L'){
                    n = 2;
                    pos++;
                    temp.append( c );
                    for(int i=0 ; pos< src.length() && i<n ; i++){
                        temp.append( src.charAt( pos ) );
                        pos++;
                    }
                }
                if( temp.toString().equals( "opponent" ) ){
                    s.append( temp );
                }else if( temp.toString().equals( "opponentLoc" ) ){
                    s.append( temp );
                }else{
                    throw new IllegalArgumentException( "Illegal character: " + temp);
                }
            }else if( c == 'd' && src.charAt( pos+2 ) == 'n' ){ //done
                n = 3;
                pos++;
                temp.append( c );
                for(int i=0 ; pos< src.length() && i<n ; i++){
                    temp.append( src.charAt( pos ) );
                    pos++;
                }
                if(temp.toString().equals( "done" )){
                    s.append( temp );
                }else{
                    throw new IllegalArgumentException( "Illegal character: " + temp);
                }
            }else if( c == 'm' && src.charAt( pos+1 ) == 'o'){ //move
                n = 3;
                pos++;
                temp.append( c );
                for(int i=0 ; pos< src.length() && i<n ; i++){
                    temp.append( src.charAt( pos ) );
                    pos++;
                }
                if(temp.toString().equals( "move" )){
                    s.append( temp );
                }else{
                    throw new IllegalArgumentException( "Illegal character: " + temp);
                }
            }else if( c == 'd' && src.charAt( pos+2 ) == 'w' ){ //down and downleft and downright
                n = 3;
                pos++;
                temp.append( c );
                for(int i=0 ; pos< src.length() && i<n ; i++){
                    temp.append( src.charAt( pos ) );
                    pos++;
                }
                c = src.charAt( pos );
                if(c == 'l'){
                    n = 3;
                    pos++;
                    temp.append( c );
                    for(int i=0 ; pos< src.length() && i<n ; i++){
                        temp.append( src.charAt( pos ) );
                        pos++;
                    }
                }else if( c == 'r' ){
                    n = 4;
                    pos++;
                    temp.append( c );
                    for(int i=0 ; pos< src.length() && i<n ; i++){
                        temp.append( src.charAt( pos ) );
                        pos++;
                    }
                }
                if( temp.toString().equals( "down" ) ){
                    s.append( temp );
                }else if( temp.toString().equals( "downleft" ) ){
                    s.append( temp );
                }else if( temp.toString().equals( "downright" ) ){
                    s.append( temp );
                }else{
                    throw new IllegalArgumentException( "Illegal character: " + temp);
                }
            }else if( c == 'r' && src.charAt( pos+1 ) == 'i'){ // right
                n = 4;
                pos++;
                temp.append( c );
                for(int i=0 ; pos< src.length() && i<n ; i++){
                    temp.append( src.charAt( pos ) );
                    pos++;
                }
                if(temp.toString().equals( "right" )){
                    s.append( temp );
                }else{
                    throw new IllegalArgumentException( "Illegal character: " + temp);
                }
            }else if( c == 'l' ){
                n = 3;
                pos++;
                temp.append( c );
                for(int i=0 ; pos< src.length() && i<n ; i++){
                    temp.append( src.charAt( pos ) );
                    pos++;
                }
                if(temp.toString().equals( "left" )){
                    s.append( temp );
                }else{
                    throw new IllegalArgumentException( "Illegal character: " + temp);
                }
            }else if( c == 'u' ){ //up and upleft and upright
                n = 1;
                pos++;
                temp.append( c );
                for(int i=0 ; pos< src.length() && i<n ; i++){
                    temp.append( src.charAt( pos ) );
                    pos++;
                }
                c = src.charAt( pos );
                if(c == 'l'){
                    n = 3;
                    pos++;
                    temp.append( c );
                    for(int i=0 ; pos< src.length() && i<n ; i++){
                        temp.append( src.charAt( pos ) );
                        pos++;
                    }
                }else if( c == 'r' ){
                    n = 4;
                    pos++;
                    temp.append( c );
                    for(int i=0 ; pos< src.length() && i<n ; i++){
                        temp.append( src.charAt( pos ) );
                        pos++;
                    }
                }
                if( temp.toString().equals( "up" ) ){
                    s.append( temp );
                }else if( temp.toString().equals( "upleft" ) ){
                    s.append( temp );
                }else if( temp.toString().equals( "upright" ) ){
                    s.append( temp );
                }else{
                    throw new IllegalArgumentException( "Illegal character: " + temp);
                }
            }else if( c == 'c' && src.charAt( pos+2 ) == 's' ){ //cost
                n = 3;
                pos++;
                temp.append( c );
                for(int i=0 ; pos< src.length() && i<n ; i++){
                    temp.append( src.charAt( pos ) );
                    pos++;
                }
                if(temp.toString().equals( "cost" )){
                    s.append( temp );
                }else{
                    throw new IllegalArgumentException( "Illegal character: " + temp);
                }
            }else if( c == 'n' ){ //nearby
                n = 5;
                pos++;
                temp.append( c );
                for(int i=0 ; pos< src.length() && i<n ; i++){
                    temp.append( src.charAt( pos ) );
                    pos++;
                }
                if(temp.toString().equals( "nearby" )){
                    s.append( temp );
                }else{
                    throw new IllegalArgumentException( "Illegal character: " + temp);
                }
            }else if( c == 's' ){ //shoot
                n = 4;
                pos++;
                temp.append( c );
                for(int i=0 ; pos< src.length() && i<n ; i++){
                    temp.append( src.charAt( pos ) );
                    pos++;
                }
                if(temp.toString().equals( "shoot" )){
                    s.append( temp );
                }else{
                    throw new IllegalArgumentException( "Illegal character: " + temp);
                }
            }else if( c == 'd' && src.charAt( pos+1 ) == 'i' ){ //dir
                n = 2;
                pos++;
                temp.append( c );
                for(int i=0 ; pos< src.length() && i<n ; i++){
                    temp.append( src.charAt( pos ) );
                    pos++;
                }
                if(temp.toString().equals( "dir" )){
                    s.append( temp );
                }else{
                    throw new IllegalArgumentException( "Illegal character: " + temp);
                }
            }else if( c == 'r' && src.charAt( pos+1 ) == 'a' ){ //random
                n = 5;
                pos++;
                temp.append( c );
                for(int i=0 ; pos< src.length() && i<n ; i++){
                    temp.append( src.charAt( pos ) );
                    pos++;
                }
                if(temp.toString().equals( "random" )){
                    s.append( temp );
                }else{
                    throw new IllegalArgumentException( "Illegal character: " + temp);
                }
            }else{
                throw new IllegalArgumentException( "Illegal character: " + c);
            }
        }else{
            throw new IllegalArgumentException( "Illegal character: " + c);
        }
        next = s.toString();
    }
}
