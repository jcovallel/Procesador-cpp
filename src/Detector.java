import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Detector {


    //******************************************************//
    //                                                      //
    //      Funcion que encuentra mas de una instruccion    //
    //      en una linea, almacena cada instruccion en una  //
    //      linea independiente.                            //
    //                                                      //
    //******************************************************//

    public static String instruction_by_line(CommonTokenStream tks) throws IOException {

        String [] in= tks.getTokens().get(0).getInputStream().toString().split("\n");
        List<String> out = new ArrayList<String>();

        int line=-1;
        Boolean pyc=false;
        String [] aux;

        for(int i=0; i<in.length-1;i++){
            if(in[i].indexOf(";")!=-1){
                if(in[i].indexOf(";",in[i].indexOf(";"))!=-1){
                    aux=in[i].split(";");
                    for(int j=0; j<aux.length; j++){
                        out.add(aux[j]+";");
                    }

                }
            }else{
                out.add(in[i]);
            }
        }

        writeTxt(out);
        return("ok");

    }


    //******************************************************//
    //                                                      //
    //      Funcion que edita el archivo input.txt          //
    //      recibiendo como parametro una lista de          //
    //      Strings.                                        //
    //                                                      //
    //******************************************************//

    public static void writeTxt(List x) throws IOException {

        File archivo = new File("./sources/input.txt");
        FileWriter escribir = new FileWriter(archivo);


        for(int i=0; i<x.size();i++) {
            escribir.write(x.get(i)+"\n");

        }
        escribir.close();
    }

}


