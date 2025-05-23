import java.io.*;
import java.util.HashMap;

public class Main{
    
    public static void main(String[] args) throws IOException{
        FileReader fr = new FileReader("FEN.txt");
        BufferedReader rdr = new BufferedReader(fr);
        HashMap<Integer, FEN> fenHashMap = new HashMap<Integer, FEN>();

        String fen;

        while(rdr.ready()){
            fen = rdr.readLine();
            // System.out.println(fen);
            String[] parts = fen.split(" ");
            
            int number = Integer.valueOf(parts[0].replace(".", "")); 
            String fenString = parts[1];
            char turn = parts[2].charAt(0);

            FEN newFen = new FEN(number, fenString, turn);
            fenHashMap.put(number, newFen);
        }

        fr = new FileReader("SOLUTIONS.txt");
        rdr = new BufferedReader(fr);

        String solution;
        while(rdr.ready()){
            solution = rdr.readLine();
            String[] parts = solution.split(" | ");
            int number = Integer.parseInt(parts[0]);
            if(number < 316) fenHashMap.get(number).setSolution(solution.substring(parts[0].length() + 3));
        }
        rdr.close();
        new MainFrame(fenHashMap);
        
    }
}
