import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

//import org.antlr.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import sun.awt.geom.AreaOp;

public class Main {

    public static void main(String[] args) throws Exception{
        CommonTokenStream tokens= read();

        //System.out.println(input);

        for(Token str : tokens.getTokens())
        {
            //imprimimos el objeto pivote

            System.out.println(str.getText()+","+str.getLine()+","+str.getCharPositionInLine());
            //System.out.println(str.getInputStream().toString().split("\n")[0]);
            //System.out.println(str.get());
        }

        System.out.println("-------------------");
        Detector d = new Detector();

        d.instruction_by_line(tokens);
        tokens=read();
        d.brackets_indentation(tokens);



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
