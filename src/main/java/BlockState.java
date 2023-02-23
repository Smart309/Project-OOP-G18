import java.util.LinkedList;

public class BlockState implements Node{
    public LinkedList<Node> statement;
    public BlockState(LinkedList<Node> statement){
        this.statement = statement;
    }
    @Override
    public void prettyPrint( StringBuilder s ){
        s.append( statement );
    }
}
