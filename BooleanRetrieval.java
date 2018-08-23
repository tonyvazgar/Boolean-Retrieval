import com.sun.corba.se.spi.ior.ObjectKey;

import java.io.File;
import java.lang.reflect.Array;
import java.text.Collator;
import java.util.*;
public class BooleanRetrieval{

     public static LinkedList<Object> leerDocumento(String nombre, LinkedList<Object> diccionario){
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
          //Collections.sort(diccionario);
            System.out.println(diccionario);
          return diccionario;
     }

     //Map<String, LinkedList<Integer>>

     public static void index(String[] files){

          Scanner scanner;
          File file;

          ArrayList<String> diccionario = new ArrayList<String>();    //Todas las palabras de los 3 textos
          //Set<String> diccionario = new HashSet<String>();
          List diccionariOrdenado;      //Es para ordenar diccionario
          int[] posting = new int[3];

          ArrayList<String[]> palabrasArchivos = new ArrayList<String[]>();     //Se almacenan todas las palabras para leer solo una vez


          Map<String, LinkedList<Integer>> invertedIndex = new HashMap<String, LinkedList<Integer>>();


          ///////////////////////

          /*
           *Agregamos las palabras al diccionario y despues del for se ordena.
           */
          for(int i = 0; i < files.length; i++){
               try{
                    file = new File("./" + files[i] + ".txt");
                    scanner = new Scanner(file);
                    ArrayList<String> lecturas = new ArrayList<String>();
                    while (scanner.hasNext()){
                         String word = scanner.next().toLowerCase();
                         diccionario.add(word);   //Se agregan al diccionario global
                         lecturas.add(word);      //Se a palabras de cada archivo
                    }
                    int numberOfWords = lecturas.size();    //Para saber el numero de palabras para el texto en el que vamos
                    String[] palabras = new String[numberOfWords];    //Se hace arreglo para cada uno exacto
                    for (int j = 0; j < numberOfWords; j++) {
                         palabras[j] = lecturas.get(j);     //Se agregan
                    }
                    Arrays.sort(palabras);        //Se guardan en orden alfabetico
                    palabrasArchivos.add(palabras);         //se agrega el conjuto de palabras al repositorio global
               }catch (Exception e){
                    System.err.println("No existe ese documento :(");
               }
          }

          diccionariOrdenado = new ArrayList(diccionario);
          Collections.sort(diccionariOrdenado);
          System.out.println(diccionariOrdenado + "\ntamañoooo:"+ diccionariOrdenado.size());

          /*
           *Se empieza a buscar por cada palabra
           *
           * Se quiere hacer que por cada palabra busque si existen en los conjuntos o repositorio global
           * si es asi se hace una linked list con la posicion del conjunto
           * y esta linked list se mete al map<String, LinkedList<Integer>>
           *     con el string de la palabra y la linked list simula el posting
           */
          for(Object palabra: diccionariOrdenado){
               for(int archivo = 0; archivo < palabrasArchivos.size(); archivo++){
                    System.out.println(Arrays.toString(palabrasArchivos.get(archivo)));
                    /*for(int i = 0; i < posts.length; i++){
                         if(palabra.equals(posts[i])){
                              System.out.println(palabra);
                         }
                    }*/
               }
          }
     }



     public static void main(String[] args) {
          //LinkedList<Object> index = new LinkedList<Object>();
          ArrayList<String> diccionario1 = new ArrayList<String>();
          ArrayList<String> diccionario2 = new ArrayList<String>();
          ArrayList<String> diccionario3 = new ArrayList<String>();

          //java.util.Map<String, String> dic = new java.util.Map<>();

          System.out.println("Leyendo el archivo........");
          String files[] = {"texto1", "texto2", "texto3"};
          index(files);
          /*
          //index.add(leerDocumento("texto1", index));
          //index.add(leerDocumento("texto2", index));
          //index.add(leerDocumento("texto3", index));


          //System.out.println(index.get(12));
          System.out.println(diccionario1.toString());
          System.out.println(diccionario2.toString());
          System.out.println(diccionario3.toString());
          */
          System.out.println("Lectura finalizada.");

     }
}
