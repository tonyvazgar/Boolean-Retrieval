import java.io.File;
import java.util.*;
public class BooleanRetrieval{

     public static ArrayList<String> leerDocumento(String nombre, ArrayList<String> diccionario){
          Scanner scanner;
          File file ;
          try{
               file = new File("./" + nombre + ".txt");
               scanner =  new Scanner(file);
               while (scanner.hasNext()){
                    String palabra = scanner.next();
                    diccionario.add(palabra);
               }
          }catch (Exception e) {
               System.out.println("No hay ese documento");
          }
          Collections.sort(diccionario);
          return diccionario;
     }

     public static void main(String[] args) {
          ArrayList<String> diccionario1 = new ArrayList<String>();
          ArrayList<String> diccionario2 = new ArrayList<String>();
          ArrayList<String> diccionario3 = new ArrayList<String>();

          //java.util.Map<String, String> dic = new java.util.Map<>();

          System.out.println("Leyendo el archivo........");
          diccionario1 = leerDocumento("texto1", diccionario1);
          diccionario2 = leerDocumento("texto2", diccionario2);
          diccionario3 = leerDocumento("texto3", diccionario3);
          System.out.println(diccionario1.toString());
          System.out.println(diccionario2.toString());
          System.out.println(diccionario3.toString());
          System.out.println("Lectura finalizada.");
     }
}
