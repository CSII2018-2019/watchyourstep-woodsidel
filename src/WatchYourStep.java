import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
		private void clickedTerrain(int row, int col) {
			check(row, col);
		}
		private void check(int row, int col) {
			if(row > -1 && row < GRIDSIZE && col > -1 && col < GRIDSIZE && hole == false && terrain[row][col].reveal(false)) {
				terrain[row][col].reveal(true);	
			}
				
			
		
	}
	public class TerrainButton extends JButton{
		JButton terrainButton = new JButton();
		private static final int SIZE = 50;
		private int  row = 0;
		private int col = 0;
		private int nextToHoles = 0;
		private boolean hole = false;
		private boolean revealed = false;
		public TerrainButton(int r, int c) {
			row = r;
			col = c;
			Dimension size = new Dimension();
			Dimension sizeWide = new Dimension();
			Dimension sizeHigh = new Dimension();
			terrainButton.setPreferredSize(size);
			row = getRow();
			col = getCol();
		}
		public void setHole(boolean h) {
			hole = h;
		}
		public void increaseHoleCount() {
			nextToHoles++;
		}
		public boolean isNextToHoles() {
			if(nextToHoles > 0) {
				return true;
			}
			return false;
		}
		public int getRow() {
			return row;
			
		}
		public int getCol() {
			return col;
			
		}
		public boolean hasHole() {
			return hole;
			
		}
		public boolean isRevealed() {
			return revealed;
		}
		public void reveal(boolean reveal) {
			revealed = reveal;
		
		if(revealed = true) {
			terrainButton.setBackground(Color.BLACK);
		}
		else if(revealed = false) {
			terrainButton.setBackground(Color.CYAN);
			if(nextToHoles > 0) {
				terrainButton.setText(Integer.toString(nextToHoles));
			}
		}
		else {
			terrainButton.setBackground(null);
			terrainButton.setText(" ");
		}
		}
		public void reset() {
			hole = false;
			revealed = false;
			nextToHoles = 0;
			terrainButton.setText(" ");
			terrainButton.setBackground(Color.WHITE); 
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
			terrain[pickRow][pickCol].setHole(true);
			
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
		addToHoleCount(row, col);
	}
	private void addToHoleCount(int row, int col) {
		if(row > -1 && row < GRIDSIZE && col > -1 && col < GRIDSIZE ) {
			terrain[row][col].increaseHoleCount();
			terrain[row][col].reveal(true);
		}
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

	
	




