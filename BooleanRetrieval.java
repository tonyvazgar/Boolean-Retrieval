import java.io.File;
import java.util.*;

/*
 *Inverted index
 *Created by Tony Vazgar on 8/22/18.
 *Copyright © 2018 Tony Vazgar. All rights reserved.
 *Contact: luis.vazquezga@udlap.mx
 *         @tonyvazgar on Twitter and Instagram
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
          //print(posts.toString());
          return posts;
     }

     public static LinkedList<Integer> intersect(LinkedList<Integer> p1, LinkedList<Integer> p2){

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
          LinkedList<Integer> pos1 = (LinkedList<Integer>) p1.clone();
          LinkedList<Integer> pos2 = (LinkedList<Integer>) p2.clone();

          while (!pos1.isEmpty() && !pos2.isEmpty()){
               if(pos1.getFirst() == pos2.getFirst()){
                    answer.add(pos1.getFirst());
                    pos1.removeFirst();
                    pos2.removeFirst();
               }else{
                    if (pos1.getFirst() < pos2.getFirst()){
                         pos1.removeFirst();
                    }else{
                         pos2.removeFirst();
                    }
               }
          }
          print("Las palabras buscadas están en el archivo: " + answer.toString());
          return answer;
     }

     public static List<Integer> not(LinkedList<Integer> p1, LinkedList<Integer> p2){
          LinkedList<Integer> answer = (LinkedList<Integer>) p1.clone();
          LinkedList<Integer> remove = (LinkedList<Integer>) p2.clone();
          print("hoala");
          print(answer.toString());
          print(p2.toString());

          for (Integer i: remove) {
               if(answer.contains(i)){
                    answer.remove(i);
               }
          }
          return answer;
     }

     public static void menu(Map<String, LinkedList<Integer>> invertedIndex){
          boolean i = true;
          /*
           * word1 AND word2 AND NOT word3
           * word1 AND word2 OR word3
           * word1 OR word2 AND NOT word3
           */
          try {
               while (i) {
                    Scanner scanner = new Scanner(System.in);
                    print("\nType the number of one of the options below:");
                    print("0) Print the inverted index.");
                    print("1) word1 AND word2 AND NOT word3");
                    print("2) word1 AND word2 OR word3");
                    print("3) word1 OR word2 AND NOT word3");
                    print("4) word1 AND word2");

                    int option = scanner.nextInt();
                    switch (option) {
                         case 0:
                              imprimirIndex(invertedIndex);
                              print("");
                              break;
                         case 1:
                              print("word1 AND word2 AND NOT word3");
                              print("   word1: ");
                              String word1 = new Scanner(System.in).nextLine();
                              print("   word2: ");
                              String word2 = new Scanner(System.in).nextLine();
                              print("   word3: ");
                              String word3 = new Scanner(System.in).nextLine();
                              LinkedList<Integer> r1 = intersect(buscarPalabra(word1, invertedIndex), buscarPalabra(word2, invertedIndex));
                              print("\nRESULT FOR THE QUERY:\n" + word1 + " AND " + word2 + " AND NOT " + word3 + " --> " + not(r1, buscarPalabra(word3, invertedIndex)).toString());
                              break;
                         case 2:
                              print("word1 AND word2 OR word3");
                              print("   word1: ");
                              String word21 = new Scanner(System.in).nextLine();
                              print("   word2: ");
                              String word22 = new Scanner(System.in).nextLine();
                              print("   word3: ");
                              String word23 = new Scanner(System.in).nextLine();

                              break;
                         case 3:
                              print("word1 OR word2 AND NOT word3");
                              print("   word1: ");
                              String word31 = new Scanner(System.in).nextLine();
                              print("   word2: ");
                              String word32 = new Scanner(System.in).nextLine();
                              print("   word3: ");
                              String word33 = new Scanner(System.in).nextLine();

                              break;
                         case 4:
                              print("word1 AND word2");
                              print("   word1: ");
                              String word41 = new Scanner(System.in).nextLine();
                              print("   word2: ");
                              String word42 = new Scanner(System.in).nextLine();
                              print("\nRESULT FOR THE QUERY:\n" + word41 + " AND " + word42 + " --> " + intersect(buscarPalabra(word41, invertedIndex), buscarPalabra(word42, invertedIndex)).toString());
                         default:
                              print("Only numbers bewteen 1 and 4");
                    }
               }
          }catch (InputMismatchException e){
               System.err.println("Only numbers with the option acepted");
          }

     }

     public static void main(String[] args) {

          Map<String, LinkedList<Integer>> invertedIndex;

          String files[] = {"1", "2", "3"};
          invertedIndex = index(files);

          menu(invertedIndex);
          //intersect(buscarPalabra("mac", invertedIndex) , buscarPalabra("coche", invertedIndex));
     }

}
