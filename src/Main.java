import java.io.File;
import java.io.FileInputStream;

//import org.antlr.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import sun.awt.geom.AreaOp;

public class Main {

    public static void main(String[] args) throws Exception{
        System.setIn(new FileInputStream(new File("sources/input.txt")));
        ANTLRInputStream input= new ANTLRInputStream(System.in);
        CPP14Lexer lexer= new CPP14Lexer(input);
        CommonTokenStream tokens= new CommonTokenStream(lexer);
        CPP14Parser parser= new CPP14Parser(tokens);
        //ParseTree tree = parser.translationunit();

        tokens.fill();

        //System.out.println(input);

        for(Token str : tokens.getTokens())
        {
            //imprimimos el objeto pivote

            System.out.println(str.getText()+","+str.getLine()+","+str.getCharPositionInLine());
            //System.out.println(tokens.getTokenSource());
        }

        System.out.println("-------------------");

        //System.out.println(Detector.divArray("aux=[1,2,3,4,5]"));


    }
}
