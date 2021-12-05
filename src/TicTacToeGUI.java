import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeGUI extends JFrame {
    private Container pane;
    private String humanSymbol;
    private String currentPlayer;
    private JButton [][] board;
    private boolean hasWinner;
    private JMenuBar menuBar;
    private JMenuItem quit;
    private JMenuItem newGame;
    private JMenuItem selectHuman;
    private JMenuItem selectAI;
    private JMenu menu;
    private JMenu menu2;
    private Timer timer;
    private AI AIPlayer;

    public TicTacToeGUI(){
        super();
        humanSymbol="x";
        pane = getContentPane();
        pane.setLayout(new GridLayout(3,3));
        setTitle("Tic Tac Toe");
        setSize(500,500);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        currentPlayer = "x";
        board = new JButton[3][3];
        hasWinner=false;
        initializeBoard();
        initializeMenuBar();
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(currentPlayer.equals(AIPlayer.getAISymbol())&&!hasWinner) {
                    AIPlayer.bestMove(board);
                    hasWinner();
                    togglePlayer();}


            }
        });
        timer.start();
        AIPlayer = new AI();
    }
    private void showWinnerMessage(){
        hasWinner=true;
        timer.stop();
        JOptionPane.showMessageDialog(null,"Player "+currentPlayer+" has won!");
    };
    private void initializeMenuBar(){
        menuBar = new JMenuBar();

        menu = new JMenu("file");
        quit = new JMenuItem("Quit");
        newGame = new JMenuItem("New Game");

        menu2 = new JMenu("player");
        selectHuman = new JMenuItem("Human");
        selectAI = new JMenuItem("AI");

        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                timer.stop();
                System.exit(0);
            }
        });

        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                resetGame();
            }
        });

        selectHuman.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                humanSymbol="x";
                AIPlayer = new AI("o");
                resetGame();
            }
        });

        selectAI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                humanSymbol="o";
                AIPlayer = new AI("x");
                resetGame();
            }
        });

        menu.add(newGame);
        menu.add(quit);
        menu2.add(selectAI);
        menu2.add(selectHuman);
        menuBar.add(menu);
        menuBar.add(menu2);
        setJMenuBar(menuBar);

    }
    private void resetGame(){
        timer.start();
        currentPlayer="x";
        hasWinner=false;
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                board[i][j].setText("");
            }
        }
        //AIPlayer=new AI();
    }
    private void initializeBoard(){
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                JButton btn = new JButton();
                btn.setFont(new Font(Font.SANS_SERIF,Font.BOLD,100));
                board[i][j]=btn;
                btn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(((JButton)e.getSource()).getText().equals("")&& !hasWinner &&currentPlayer.equals(humanSymbol)){
                            btn.setText(humanSymbol);
                            hasWinner();
                            togglePlayer();
                        }
                    }
                });
                pane.add(btn);
            }
        }
    }
    private void togglePlayer(){
        if(currentPlayer.equals("x")) currentPlayer="o";
        else currentPlayer="x";
    }
    private void hasWinner(){
        for(int i=0;i<3;i++){
            if(board[i][0].getText().equals(currentPlayer)&&board[i][1].getText().equals(currentPlayer)&&board[i][2].getText().equals(currentPlayer)){
                showWinnerMessage();
            }
            if(board[0][i].getText().equals(currentPlayer)&&board[1][i].getText().equals(currentPlayer)&&board[2][i].getText().equals(currentPlayer)){
                showWinnerMessage();
            }
        }
        if(board[0][0].getText().equals(currentPlayer)&&board[1][1].getText().equals(currentPlayer)&&board[2][2].getText().equals(currentPlayer))
            showWinnerMessage();
        if(board[2][0].getText().equals(currentPlayer)&&board[1][1].getText().equals(currentPlayer)&&board[0][2].getText().equals(currentPlayer))
            showWinnerMessage();
        boolean isStillEmpty=false;
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
                if(board[i][j].getText().equals("")) isStillEmpty=true;
        if(!isStillEmpty&&!hasWinner){
            hasWinner=true;
            timer.stop();
            JOptionPane.showMessageDialog(null,"It's a tie!");
        }
    }


}
