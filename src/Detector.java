import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Detector {

    //******************************************************//
    //                                                      //
    //      Funcion que encuentra mas de una instruccion    //
    //      en una linea, almacena cada instruccion en una  //
    //      linea independiente.                            //
    //                                                      //
    //******************************************************//

    public void instruction_by_line(CommonTokenStream tks) throws IOException {

        String [] in= tks.getTokens().get(0).getInputStream().toString().split("\n");
        List<String> out = new ArrayList<String>();
        String [] aux;
        String coment="";
        int count;

        for (int i=0; i<in.length-1;i++) {

            if(in[i].contains("for")){
                continue;
            }

            coment="";

            if(in[i].indexOf("//")!=-1){
                coment=in[i].substring(in[i].indexOf("//"));
                in[i]=in[i].substring(0,in[i].indexOf("//"));

            }
            if(in[i].indexOf(";")!=-1){
                if(in[i].indexOf(";",in[i].indexOf(";"))!=-1){
                    aux=in[i].split(";");
                    count=count_X(in[i],';');
                    for(int j=0; j<count; j++){
                        out.add(aux[j]+";"+coment);
                    }

                }
            }else{
                out.add(in[i]+coment);
            }

        }
        writeTxt(out);
    }

    //******************************************************//
    //                                                      //
    //      Funcion formato para condicional if             //
    //                                                      //
    //******************************************************//

    public void conditionals (CommonTokenStream tks) throws IOException{
        String [] in= tks.getTokens().get(0).getInputStream().toString().split("\n");
        List<String> out = new ArrayList<String>();

        for (int i=0; i<in.length-1;i++) {
            if(in[i].contains("if") || in[i].contains("else")) {
                if (in[i].contains("if(")){
                    in[i]=(in[i].replace("if(", "if ("));
                }

                if (in[i].contains("){")) {

                    in[i]=(in[i].replace("){", ") {"));
                }
                if (in[i].contains("}else")) {

                    in[i]=(in[i].replace("}else", "} else"));
                }
                if (in[i].contains("else{")) {

                    in[i]=(in[i].replace("else{", "else {"));
                }

            }
            else {
                    out.add(in[i]);
                    continue;
            }
            out.add(in[i]);

        }
        writeTxt(out);



    }

    public void loops (CommonTokenStream tks) throws IOException{
        String [] in= tks.getTokens().get(0).getInputStream().toString().split("\n");
        List<String> out = new ArrayList<String>();

        for (int i=0; i<in.length-1;i++) {

                if (in[i].contains("for(")) {
                    in[i] = (in[i].replace("for(", "for ("));
                }

                if (in[i].contains("){")) {
                        in[i] = (in[i].replace("){", ") {"));
                }

                if(in[i].contains("while(") ) {
                    in[i] = (in[i].replace("while(", "while ("));

            }
            else {
                out.add(in[i]);
                continue;
            }
            out.add(in[i]);

        }
        writeTxt(out);



    }

    //******************************************************//
    //                                                      //
    //      Funcion que tabula line por linea dependiendo   //
    //      de la hubicacion por corchetes {}               //
    //                                                      //
    //******************************************************//

    public void brackets_indentation(CommonTokenStream tks) throws IOException{
        Stack<Integer> stack = new Stack<Integer>();
        List<String> out = new ArrayList<String>();
        int line=0;
        int count=1;
        String text="";
        String [] in= tks.getTokens().get(0).getInputStream().toString().split("\n");
        boolean b=false;



        for(Token str : tks.getTokens()) {

            if(str.getText().contains("{")){
                if(stack.size()==0) {
                    b=true;
                }
                stack.push(str.getLine());
            }else if(str.getText().contains("}")){
                stack.pop();
            }

            if(str.getLine()!=line ){
                line=str.getLine();

                if(stack.size()==0){
                    out.add(in[line-1].substring(firstCharacter(in[line-1])));

                }else if(stack.peek()==line){
                    if(b){
                        out.add(in[line-1].substring(firstCharacter(in[line-1])));
                        b=false;

                    }else {
                        if(firstCharacter(in[line-1])==in[line-1].indexOf("{")+1) {
                            out.add(esp((stack.size() - 2) * 2) + in[line - 1].substring(firstCharacter(in[line - 1])));

                        }else {
                            out.add(esp((stack.size()-1) * 2) + in[line - 1].substring(firstCharacter(in[line - 1])));
                        }
                    }
                }else{

                    out.add(esp(stack.size() * 2) + in[line - 1].substring(firstCharacter(in[line - 1])));


                }

            }

        }
        System.out.println(stack);
        writeTxt(out.subList(0,out.size()-1));

    }


    public int firstCharacter(String x){
        for(int i=0; i< x.length(); i++){
            if(x.charAt(i)!=' '){
                return i;
            }
        }
        return 0;
    }


    //******************************************************//
    //                                                      //
    //      Funcion que encuentra la frecuencia de un       //
    //      caracter en un String                           //
    //                                                      //
    //******************************************************//

    public static int count_X(String x, char b){
        int sum=0;
        for(int i=0; i<x.length();i++){
            if(x.charAt(i)==b){
                sum=sum+1;
            }
        }
        return sum;
    }

    //******************************************************//
    //                                                      //
    //      Funcion que edita el archivo input.txt          //
    //      recibiendo como parametro una lista de          //
    //      Strings.                                        //
    //                                                      //
    //******************************************************//

    public void writeTxt(List x) throws IOException {

        File archivo = new File("./sources/input.txt");
        FileWriter escribir = new FileWriter(archivo);

        for(int i=0; i<x.size();i++) {
            escribir.write(x.get(i)+"\n");
        }

        escribir.close();
    }

    //******************************************************//
    //                                                      //
    //      Funcion que genera un String con n espacion,    //
    //      usada para tabular dependiendo el nivel del     //
    //      corchete                                        //
    //                                                      //
    //******************************************************//

    public static String esp(int x){
        String out="";
        for(int i=0;i<x;i++){
            out=out+" ";
        }
        return out;
    }


}


