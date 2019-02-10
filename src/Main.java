import java.io.File;
import java.io.FileInputStream;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class Main {

    public static void main(String[] args) throws Exception{
        try {
            CPP14Lexer lexer;
            if(args.length>0)
                lexer = new CPP14Lexer(CharStreams.fromFileName(args[0]));
            else
                lexer = new CPP14Lexer(CharStreams.fromStream(System.in));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            CPP14Parser parser = new CPP14Parser(tokens);
            ParseTree tree = parser.translationunit();
            //System.out.println(tree.toStringTree(parser));
            //System.out.println(lexer.getAllTokens());

            //System.out.println(tokens.get);
            for(Token str : tokens.getTokens())
            {
                //imprimimos el objeto pivote
                System.out.println(str);
            }
            /*
            //System.setIn(new FileInputStream(new File(System.getProperty("user.dir")+"/sources/in.txt")));
            System.out.println(CharStreams.fromFileName(System.getProperty("user.dir") + "/sources/in.txt"));
            //ANTLRInputStream input = new ANTLRInputStream(System.in);
            Python3Lexer lexer = new Python3Lexer(CharStreams.fromFileName(System.getProperty("user.dir") + "/sources/in.txt"));
            //Python3Lexer lexer = new Python3Lexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            Python3Parser parser = new Python3Parser(tokens);
            System.out.println(tokens.getTokens().isEmpty());
            */
        }catch (Exception e){
            System.err.println("Error (Test): "+ e);
        }
    }
}
