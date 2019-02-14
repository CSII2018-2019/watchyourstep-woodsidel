import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class WatchYourStep extends JFrame{
	private static final int GRIDSIZE = 10;
	private static final int NUMBEROFHOLES = 10;
	private static TerrainButton[] [] terrain = new TerrainButton[GRIDSIZE][GRIDSIZE];
	private int totalRevealed = 0;
	
	
	public WatchYourStep() {
		initGUI();
		setHoles();
		setTitle("Watch Your Step");
		//setSize(200, 100); //pixels
		setResizable(false);
		pack();
		setLocationRelativeTo(null); //centers on screen, do this after packing but before visible
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	private void initGUI() {
		JLabel titleLabel = new JLabel("Watch Your Step");
		//customize the label
		Font titleFont = new  Font ("Papyrus", Font.BOLD + Font.ITALIC, 64);
		titleLabel.setFont(titleFont);
		titleLabel.setForeground(Color.GREEN);
		titleLabel.setBackground(Color.WHITE);
		titleLabel.setOpaque(true);
		//add the label to your window
		add(titleLabel, BorderLayout.PAGE_START);
		titleLabel.setHorizontalAlignment(JLabel.CENTER); //left or right
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(GRIDSIZE, GRIDSIZE));
		add(centerPanel, BorderLayout.CENTER);
		for(int r = 0; r < GRIDSIZE; r++) {
			for(int c = 0; c < GRIDSIZE; c++) {
				terrain[r][c] = new TerrainButton(r, c);
				terrain[r][c].addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						TerrainButton button = (TerrainButton) e.getSource();
						int row = button.getRow();
						int col = button.getCol();
						clickedTerrain(row, col);
					}
				});
				centerPanel.add(terrain[r][c]);
			}
		}
			
		}
		private void setHoles() {
			Random rand = new Random();
			int pickRow;
			int pickCol;
			for(int i = 0; i < NUMBEROFHOLES; i++) {
				do { 
					pickRow = rand.nextInt(GRIDSIZE);
					pickCol = rand.nextInt(GRIDSIZE);
				} while(terrain[pickRow][pickCol].hasHole());
				terrain[pickRow][pickCol].setHole(true);
				addToNeighborsHoleCount(pickRow, pickCol);
				//terrain[pickRow][pickCol].reveal(true);
				}
		}
	
		private void clickedTerrain(int row, int col) {
			//System.out.println("in clicked terrain");
			//terrain[row][col].reveal(true);
			
			if(terrain[row][col].hasHole() == true) {
				String message = "You stepped in a hole!\nGAME OVER\nDo you want to play again?";
				promptForNewGame(message);
			}
			else {
				check(row, col);
				checkNeighbors(row, col);
				if(totalRevealed == GRIDSIZE * GRIDSIZE - NUMBEROFHOLES) {
					String message = "YOU WON!/nDo you want to play again?";
					promptForNewGame(message);
				}
			}
			
		}
		private void promptForNewGame(String message) {
			showHoles();
			int option = JOptionPane.showConfirmDialog(this, message, "Play Again?", JOptionPane.YES_NO_OPTION);
			if(option == JOptionPane.YES_OPTION) {
				newGame();
			}else {
				System.exit(0);
			}
		}
		private void showHoles() {
			for(int r = 0; r < GRIDSIZE; r++) {
				for(int c = 0; c < GRIDSIZE; c++) {
					if(terrain[r][c].hasHole()){
						terrain[r][c].reveal(true);
					}
				}
			}
		}
		private void newGame() {
			totalRevealed = 0;
			for(int r = 0; r < GRIDSIZE; r++) {
				for(int c = 0; c < GRIDSIZE; c++) {
					terrain[r][c].reset();
				}
			}
			setHoles();
		}
		private void check(int row, int col) {
			if(row > -1 && row < GRIDSIZE && col > -1 && col < GRIDSIZE
					&& ! terrain[row][col].hasHole() && ! terrain[row][col].isRevealed()) {
				terrain[row][col].reveal(true);	
				totalRevealed++;
				if(terrain[row][col].isNextToHoles()!=true) {
					checkNeighbors(row, col);
				}
			}	
		}
		private void addToNeighborsHoleCount(int row, int col) {
			addToHoleCount(row-1, col-1);
			addToHoleCount(row+1, col-1);
			addToHoleCount(row+1, col+1);
			addToHoleCount(row-1, col+1);
			addToHoleCount(row+1, col);
			addToHoleCount(row-1, col);
			addToHoleCount(row, col+1);
			addToHoleCount(row, col-1);
		}
		private void addToHoleCount(int row, int col) {
			if(row > -1 && row < GRIDSIZE && col > -1 && col < GRIDSIZE ) {
				terrain[row][col].increaseHoleCount();
				//terrain[row][col].reveal(true);
			}
		}
		private void checkNeighbors(int row, int col) {
			check(row-1, col-1);
			check(row+1, col+1);
			check(row-1, col+1);
			check(row+1, col-1);
			check(row, col-1);
			check(row-1, col);
			check(row+1, col);
			check(row, col+1);
		}

	public static void main(String[] args) {
		try {
            String className = UIManager.getCrossPlatformLookAndFeelClassName();
            UIManager.setLookAndFeel(className);
        } catch ( Exception e) {}
        
        EventQueue.invokeLater(new Runnable (){
            @Override
            public void run() {
                new WatchYourStep();
            }
        });
        
	}
	}

	
	




