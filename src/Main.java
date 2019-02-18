import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//import org.antlr.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import sun.awt.geom.AreaOp;

public class Main {

    public static void main(String[] args) throws Exception{
        CommonTokenStream tokens= read();

        Detector d = new Detector();

        d.instruction_by_line(tokens);
        tokens=read();
        d.brackets_indentation(tokens);
        d.vertical_align(tokens);
        d.L_changer(tokens);
        //System.out.println(d.firstCharacter("     {"));
        tokens=read();
        d.conditionals(tokens);
        tokens=read();
        d.loops(tokens);




    }

    public static CommonTokenStream read() throws IOException {
        System.setIn(new FileInputStream(new File("sources/input.txt")));
        ANTLRInputStream input= new ANTLRInputStream(System.in);
        CPP14Lexer lexer= new CPP14Lexer(input);
        CommonTokenStream tokens= new CommonTokenStream(lexer);
        CPP14Parser parser= new CPP14Parser(tokens);
        //ParseTree tree = parser.translationunit();
        tokens.fill();
        return tokens;
    }
}
