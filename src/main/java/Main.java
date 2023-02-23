import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main{
    public static void main( String[] args ){
        String line = null;
        Path fileRead  = Paths.get( "D:\\Project OOP\\Ex construction plan.txt" );
        Path fileWrite  = Paths.get( "D:\\Project OOP\\Write.txt" );
        Charset charset = Charset.forName( "UTF-8" );
        try ( BufferedWriter writer = Files.newBufferedWriter(fileWrite, charset)) {
            try( BufferedReader reader = Files.newBufferedReader( fileRead,charset ) ){
                String text = "";
                while ((line = reader.readLine()) != null) {
                    int index = line.indexOf("#");
                    if (index >= 0) {
                        line = line.substring(0, index);
                    }
                    text += line;
                }
                writer.write( text );
                ExprTokenizer test = new ExprTokenizer( text );
                Player p = new Player();
                ExprParser pa = new ExprParser( p,test );
                pa.parse();
//                int n = 418;
//                for( int i = 1; i <= n ; i++ ){
//                    System.out.println(test.consume());
//                }
            }catch( IOException e ){
                System.err.format("IOE: %s%n",e);
            }catch( LexicalError e ){
                throw new RuntimeException( e );
            }catch( SyntaxError e ){
                throw new RuntimeException( e );
            }catch( EvalError e ){
                throw new RuntimeException( e );
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }
}