public class FEN {
    private int num;
    private double ifSolved;
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
    public void setIfSolved(double ifSolved){
        this.ifSolved = ifSolved;
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
    public String getStatus(){
        if(ifSolved == 1){
            return "Solved";
        } else if(ifSolved == 0.5){
            return "Partially Solved";
        } else{
            return "Not Solved";
        }
    }
    public String whoseTurn(){
        if(turn == 'w') return "White to move";
        return "Black to move";
        
    }
    public String getSolution(){
        return solution;
    }
    public double ifSolved(){
        return ifSolved;
    }
    public void printSolution(){
        System.out.println(solution);
    }

    public void printDesc(){
        System.out.println(" Number: " + num + " FEN: " + fenString + " " + whoseTurn() + " If Solved: " + ifSolved);
    }
}
