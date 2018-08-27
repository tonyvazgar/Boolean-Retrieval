import java.io.File;
import java.util.*;

/*
 *Inverted index
 *Created by Tony Vazgar on 8/22/18.
 *Copyright © 2018 Tony Vazgar. All rights reserved.
 *contact: luis.vazquezga@udlap.mx
 */

public class BooleanRetrieval{

     public static Map<String, LinkedList<Integer>> index(String[] files){

          Scanner scanner;
          File file;
          Set<String> diccionario = new HashSet<String>();
          List diccionariOrdenado;      //Es para ordenar diccionario
          ArrayList<String[]> palabrasArchivos = new ArrayList<String[]>();     //Se almacenan todas las palabras para leer solo una vez
          Map<String, LinkedList<Integer>> invertedIndex = new HashMap<String, LinkedList<Integer>>();

          ///////////////////////

          /*
           *Agregamos las palabras al diccionario y despues del for se ordena.
           */
          System.out.println("Leyendo el archivo........");
          for(int i = 0; i < files.length; i++){
               try{
                    file = new File("./" + files[i] + ".txt");
                    scanner = new Scanner(file);
                    ArrayList<String> lecturas = new ArrayList<String>();
                    while (scanner.hasNext()){
                         String word = scanner.next().toLowerCase() ;
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
          System.out.println("Lectura finalizada.");

          diccionariOrdenado = new ArrayList(diccionario);
          Collections.sort(diccionariOrdenado);

          ArrayList<Map<String,Integer>> mapDeMaps = new ArrayList<>();
          for(int archivo = 0; archivo < palabrasArchivos.size(); archivo++){
               LinkedList<Integer> post = new LinkedList<Integer>();
               String[] conjuntoPalabras = palabrasArchivos.get(archivo);
               //System.out.println(Arrays.toString(conjuntoPalabras));
               String palabra = "";
               Map<String, Integer> dictionary = new HashMap<String, Integer>();
               for(int p = 0; p < conjuntoPalabras.length;p++){
                    palabra = conjuntoPalabras[p];
                    if(diccionario.contains(palabra)){
                         dictionary.put(palabra, archivo+1);
                    }
               }
               mapDeMaps.add(dictionary);
          }

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
          return invertedIndex;
     }

     public static void imprimirIndex(Map<String, LinkedList<Integer>> invertedIndex){
          print("DICTIONARY             POSTINGS" );
          for (String word: invertedIndex.keySet()) {
               print(String.format("%-15s", word) + " -->    " + invertedIndex.get(word).toString());
          }
     }

     private static void print(String string){
          System.out.println(string);
     }

     public static LinkedList<Integer> buscarPalabra(String palabra, Map<String, LinkedList<Integer>> invertedIndex) {
          LinkedList<Integer> posts = new LinkedList<>();
          Iterator word = invertedIndex.keySet().iterator();
          while (word.hasNext()){
               String p = (String)word.next();
               if(p.equals(palabra)){
                    posts = invertedIndex.get(p);
               }
          }
          print(posts.toString());
          return posts;
     }

     public static void intersect(LinkedList<Integer> p1, LinkedList<Integer> p2){

          /*
           *   IMPLEMENTATION OF THE NEXT ALGORITHM:
           *
           *     INTERSECT(p1, p2)
           *        answer←⟨⟩
           *        while p1 ̸= NIL and p2 ̸= NIL
           *        do if docID(p1) = docID(p2)
           *             then ADD(answer, docID(p1))
           *                  p1 ← next(p1)
           *                  p2 ← next(p2)
           *             else if docID(p1) < docID(p2)
           *                  then p1 ← next(p1)
           *                  else p2 ← next(p2)
           *        return answer
           */
          LinkedList<Integer> answer = new LinkedList<>();
          while (!p1.isEmpty() && !p2.isEmpty()){
               if(p1.getFirst() == p2.getFirst()){
                    answer.add(p1.getFirst());
                    p1.removeFirst();
                    p2.removeFirst();
               }else{
                    if (p1.getFirst() < p2.getFirst()){
                         p1.removeFirst();
                    }else{
                         p2.removeFirst();
                    }
               }
          }
          print("Las palabras buscadas están en el archivo: " + answer.toString());
     }

     public static void menu(Map<String, LinkedList<Integer>> invertedIndex){
          boolean i = true;
          while (i) {
               Scanner scanner = new Scanner(System.in);
               print("Type the number of one of the options below:");
               print("0) See the inverted index.");
               print("1) Words that must be included.");
               print("2) Word that must not be included.");
               print("3) Word ");
               int option = scanner.nextInt();
               switch (option) {
                    case 0:
                         imprimirIndex(invertedIndex);
                         break;
                    case 1:
                         break;
                    case 2:
                         break;
                    case 3:
                         break;
                    default:
                         print("Only numbers with the option acepted");
                         i = false;
                         break;
               }
          }
     }

     public static void main(String[] args) {

          Map<String, LinkedList<Integer>> invertedIndex;

          String files[] = {"1", "2", "3"};
          invertedIndex = index(files);

          menu(invertedIndex);
          intersect(buscarPalabra("mac", invertedIndex) , buscarPalabra("coche", invertedIndex));
     }

}
