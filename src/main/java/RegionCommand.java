public class RegionCommand implements Node{
    public String command;
    public Node expression;
    public RegionCommand( String command, Node expression){
        this.command = command;
        this.expression = expression;
    }

    @Override
    public void prettyPrint( StringBuilder s ){

    }
}
