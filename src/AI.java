import javax.swing.*;
import java.util.*;
import java.util.Vector;

public class AI {
    private final String[][] state;
    private final String AISymbol;
    private final String HumanSymbol;

    public AI(){
        AISymbol="o";
        HumanSymbol="x";
        state = new String[3][3];
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
                state[i][j]="";
    }
    public AI(String smb){
        AISymbol=smb;
        if(smb.equals("x")) HumanSymbol="o";
        else HumanSymbol="x";
        state = new String[3][3];
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
                state[i][j]="";
    }

    public String getAISymbol(){
        return this.AISymbol;
    }
    public void getState(JButton[][] board){
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++){
                this.state[i][j]=board[i][j].getText();
            }
    }
    private boolean isWinner(String currentPlayer){
        for(int i=0;i<3;i++){
            if(this.state[i][0].equals(currentPlayer)&&this.state[i][1].equals(currentPlayer)&&this.state[i][2].equals(currentPlayer)){
                return true;
            }
            if(this.state[0][i].equals(currentPlayer)&&this.state[1][i].equals(currentPlayer)&&this.state[2][i].equals(currentPlayer)){
                return true;
            }
        }
        if(this.state[0][0].equals(currentPlayer)&&this.state[1][1].equals(currentPlayer)&&this.state[2][2].equals(currentPlayer))
            return true;
        if(this.state[2][0].equals(currentPlayer)&&this.state[1][1].equals(currentPlayer)&&this.state[0][2].equals(currentPlayer))
            return true;
        return false;
    }
    private boolean isFree(int i){
        return state[i/3][i%3].equals("");
    }


    public void moveRandom(JButton[][] board){
        Random ran = new Random();
        this.getState(board);
        Vector<Integer> vct = new Vector<Integer>();
        int count=0;
        for(int i=0;i<9;i++){
            if(this.isFree(i)){
                vct.add(i);
                count++;}
        }
        int val= vct.get(ran.nextInt(count));
        board[val/3][val%3].setText(AISymbol);

    }
    public int freeCellLeft(){
        int count=0;
        for(String[] i : this.state)
            for(String j: i)
                if(j.equals("")) count++;

        return count;
    }

    public void moveWithoutCheck(int i, String currentPlayer){
        this.state[i/3][i%3]=currentPlayer;
    }

    public void printState(){
        for(String[] line : this.state){
            System.out.print("|");
            for(String cell: line)
                System.out.print(cell+"|");
            System.out.print("\n");}
        System.out.print("\n");
    }


    public int minmax( boolean isAI, int alpha, int beta){
        //System.out.println("minmax");
        //this.printState();
        if(this.isWinner(this.AISymbol)) return 1;
        if(this.isWinner(this.HumanSymbol)) return -1;
        if(this.freeCellLeft() == 0) return 0;
        if(isAI){
            int max=Integer.MIN_VALUE;
            for(int i=0; i<9; i++){
                if(this.isFree(i)){
                    this.moveWithoutCheck(i,this.AISymbol);
                    int val=minmax(false,alpha,beta);
                    this.moveWithoutCheck(i,"");
                    max=Math.max(max,val);
                    if(max>=beta) return max;
                    alpha=Math.max(alpha,max);
                }
            }
            //System.out.println("max: "+max);
            return max;
        } else{
            int min=Integer.MAX_VALUE;
            for(int i=0; i<9; i++){
                if(this.isFree(i)){
                    this.moveWithoutCheck(i,this.HumanSymbol);
                    int val=minmax(true,alpha,beta);
                    this.moveWithoutCheck(i,"");
                    min=Math.min(min,val);
                    if(min<=alpha) return min;
                    beta=Math.min(min,beta);
                }
            }
            //System.out.println("max: "+min);
            return min;

        }


        }

    public void bestMove(JButton[][] board){
        this.getState(board);
        //this.printState();
        int move=-1; int max=Integer.MIN_VALUE;
        for(int i=0;i<9;i++){
            if(this.isFree(i)){
                this.moveWithoutCheck(i,this.AISymbol);
                int val=minmax(false,Integer.MIN_VALUE,Integer.MAX_VALUE);
                this.moveWithoutCheck(i,"");
                //System.out.println(i+": "+val);
                if(val> max){
                    move=i;
                    max=val;
                }

            }
        }
        board[move/3][move%3].setText(this.AISymbol);
    }
}
