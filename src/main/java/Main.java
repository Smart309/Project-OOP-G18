import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Main{
    public static void main( String[] args ){
        Path file = Paths.get("D:\\Project OOP old\\Ex construction plan.txt");
        Charset charset = Charset.forName( "UTF-8" );
        try( BufferedReader reader = Files.newBufferedReader( file,charset ) ){
            String line = null;
            String plan = "";
            while((line = reader.readLine())!=null){
                plan += line;
            }
            ExprTokenizer token = new ExprTokenizer( plan );
            ExprParser parser = new ExprParser( token );
            Node node = parser.parse();
            return;

        }catch( IOException x){
            System.err.format("IOException: %s%n" , x);
        }catch( LexicalError e ){
            throw new RuntimeException( e );
        }catch( SyntaxError e ){
            throw new RuntimeException( e );
        }catch( EvalError e ){
            throw new RuntimeException( e );
        }
    }
}