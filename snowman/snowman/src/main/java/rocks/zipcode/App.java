package rocks.zipcode;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        String input = "(add 2 (subtract 4 2))";
        Compiler compiler = new Compiler();
        String output = compiler.compile(input);
        System.out.println(output);
    }
}
