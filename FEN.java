public class FEN {
    private int num;
    private String fullFenString, fenString, solution;
    private char turn;

    FEN(int num, String fenString, char turn){
        this.num = num;
        this.fenString = fenString;
        this.fullFenString = fenString + " " + turn + " - - 0 1";
        this.turn = turn;
    }
    public void setSolution(String solution){
        this.solution = solution;
    }
    public int getNum(){
        return num;
    }
    public String getFenString(){
        return fenString;
    }
    public String getFullFENString(){
        return fullFenString;
    }
    public String whoseTurn(){
        if(turn == 'w') return "White to move";
        return "Black to move";
        
    }
    public String getSolution(){
        return solution;
    }

    public void printSolution(){
        System.out.println(solution);
    }

    public void printDesc(){
        System.out.println(" Number: " + num + " FEN: " + fenString + " " + whoseTurn());
    }
}
