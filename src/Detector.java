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

            if(in[i].contains("if")){
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
    //      Funcion que tabula line por linea dependiendo   //
    //      de la hubicacion por corchetes {}               //
    //                                                      //
    //******************************************************//

    public void brackets_indentation(CommonTokenStream tks) throws IOException{
        Stack<Integer> stack = new Stack<Integer>();
        List<String> out = new ArrayList<String>();
        int line=0;
        String text="";
        String [] in= tks.getTokens().get(0).getInputStream().toString().split("\n");



        for(Token str : tks.getTokens()) {

            if(str.getText().contains("{")){
                stack.push(str.getLine());
            }else if(str.getText().contains("}")){
                stack.pop();
            }
            if(str.getLine()!=line ){
                line=str.getLine();
                if(str.getCharPositionInLine()==stack.size()*2){
                    out.add(in[line-1]);

                }else if(stack.size()>0) {

                    if(str.getCharPositionInLine()>=stack.size()*2){
                        out.add(in[line-1].substring(str.getCharPositionInLine()-stack.size()*2));
                    }else if (stack.peek() != line) {
                        out.add(esp(stack.size() * 2 - str.getCharPositionInLine()) + in[line - 1]);
                    }else{
                        if(stack.peek()==line){
                            out.add(esp((stack.size()-1)*2)+"{");
                        }

                    }
                }
            }

        }
        System.out.println(stack);
        writeTxt(out.subList(0,out.size()-1));

    }


    /*public void loops(CommonTokenStream tks) throws IOException{
        String [] in= tks.getTokens().get(0).getInputStream().toString().split("\n");
        List<String> out = new ArrayList<String>();
        String tipe;
        String []aux1,aux2;

        for(int i=0; i<in.length;i++){
            if(in[i].indexOf("for")!= -1){
                aux1=in[i].split("for");
                out.add(aux1[0]+"for "+aux1[1].substring(0,aux1[1].indexOf(")")+1)+" "+aux1[1].substring(aux1[1].indexOf(")")+1));

            }else{
                out.add(in[i]);
            }
        }
        writeTxt(out.subList(0,out.size()-1));
    }*/

   /* public void brackets(CommonTokenStream tks) throws IOException{
        String [] in= tks.getTokens().get(0).getInputStream().toString().split("\n");
        List<String> out = new ArrayList<>();
        String aux;

        for(int i=0; i<in.length;i++){

            if(in[i].indexOf("{")!=-1) {
                aux=in[i].substring(0,in[i].indexOf("{"));
                out.add(aux);
                out.add("{");

            }else if(in[i].indexOf("}")!=-1){
                aux=in[i].substring(0,in[i].indexOf("}"));
                out.add(aux);
                out.add("}");

            }else{
                out.add(in[i]);
            }
        }
        writeTxt(out.subList(0,out.size()-1));
    }*/

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


