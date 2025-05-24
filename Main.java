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

        fr = new FileReader("TRACKER.txt");
        rdr = new BufferedReader(fr);

        String ifSolved;
        while(rdr.ready()){
            ifSolved = rdr.readLine();
            String[] parts = ifSolved.split(" ");
            int num = Integer.parseInt(parts[0].replace(".", ""));
            
            if(num <= 315){
                Double ifSolve = Double.parseDouble(parts[1]);
                fenHashMap.get(num).setIfSolved(ifSolve);
            }
        }
        rdr.close();
        
        Runtime.getRuntime().addShutdownHook(new Thread() { 
            public void run(){
                FileWriter wr;
                try {
                    wr = new FileWriter("TRACKER.txt");
                
                
                    System.out.println(" Saving progress..");
                    for(int i = 1; i < 316; i++){
                            wr.write(fenHashMap.get(i).getNum() + ". " + fenHashMap.get(i).ifSolved() + "\n");
                    }
                    for(int i = 316; i < 1003; i++){
                        wr.write(i + ". 0.0\n");
                    }
                    wr.close();
                    System.out.println(" Progress Saved..");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } );
        
        new MainFrame(fenHashMap);
        

    }
}
