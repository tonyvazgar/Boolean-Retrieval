import java.io.File;
import java.util.*;

public class Index {
    Map<Integer, List<String>> indice = new HashMap<Integer, List<String>>();

    public static void index(String[] files){
        Scanner scanner;
        File file;
        for(String nombre: files){
            try{
                file = new File("./" + nombre + ".txt");
                scanner = new Scanner(file);
                while (scanner.hasNext()){
                    String word = scanner.next().toLowerCase();
                    System.out.println("Archivo: " + nombre.toUpperCase() + "-->" + word);
                }
            }catch (Exception e){
                System.out.println("No existe ese documento :(");
            }
        }
    }
}
