import com.sun.corba.se.spi.ior.ObjectKey;
import org.omg.CORBA.MARSHAL;
import sun.tools.jconsole.InternalDialog;

import java.io.File;
import java.lang.reflect.Array;
import java.text.Collator;
import java.util.*;
public class BooleanRetrieval{

     public static void index(String[] files){

          Scanner scanner;
          File file;

          //ArrayList<String> diccionario = new ArrayList<String>();    //Todas las palabras de los 3 textos
          Set<String> diccionario = new HashSet<String>();
          List diccionariOrdenado;      //Es para ordenar diccionario

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
          //System.out.println(diccionariOrdenado + "\ntamañoooo:"+ diccionariOrdenado.size());

          /*
           *Se empieza a buscar por cada palabra
           *
           * Se quiere hacer que por cada palabra busque si existen en los conjuntos o repositorio global
           * si es asi se hace una linked list con la posicion del conjunto
           * y esta linked list se mete al map<String, LinkedList<Integer>>
           *     con el string de la palabra y la linked list simula el posting
           */
          ArrayList<Map<String,Integer>> mapDeMaps = new ArrayList<>();
          for(int archivo = 0; archivo < palabrasArchivos.size(); archivo++){
               LinkedList<Integer> post = new LinkedList<Integer>();
               String[] conjuntoPalabras = palabrasArchivos.get(archivo);
               //System.out.println(Arrays.toString(conjuntoPalabras));
               String palabra = "";
               Map<String, Integer> dictionary = new HashMap<String, Integer>();
               for(int p = 0; p < conjuntoPalabras.length;p++){
                    //System.out.println(conjuntoPalabras[p]);
                    palabra = conjuntoPalabras[p];
                    if(diccionario.contains(palabra)){
                         //System.out.println("EXISTEE");
                         //post.add(archivo);
                         //System.out.println(palabra + " *existe en*" + post);
                         //invertedIndex.put(palabra, post);
                         dictionary.put(palabra, archivo+1);
                    }
               }
               mapDeMaps.add(dictionary);
          }
          System.out.println("MAPS: " + mapDeMaps);

          for(Object w: diccionariOrdenado){      //Cada palabra en el diccionariOrdenado
               String word = (String) w;
               LinkedList<Integer> post = new LinkedList<Integer>();
               for(int i = 0; i < mapDeMaps.size(); i++){   //Cada diccionario (indica palabra = indice)
                    Map<String, Integer> cadaDictionary = mapDeMaps.get(i);

                    String[] palabras = cadaDictionary.keySet().toArray(new String[cadaDictionary.size()]);
                    Integer[] numDocs = cadaDictionary.values().toArray(new Integer[cadaDictionary.size()]);

                    for(int j = 0; j < palabras.length; j++){
                         if(word.equals(palabras[j])){
                              post.add(numDocs[i]);
                         }
                    }
               }
               invertedIndex.put(word, post);

          }

          print("______________\nThe inverted index is: \n  ".toUpperCase() + invertedIndex.toString() + "\n______________");


     }



     private static void print(String string){
          System.out.println(string);
     }

     public static void main(String[] args) {
          System.out.println("Leyendo el archivo........");
          String files[] = {"texto1", "texto2", "texto3"};
          index(files);
          System.out.println("Lectura finalizada.");
     }
}
