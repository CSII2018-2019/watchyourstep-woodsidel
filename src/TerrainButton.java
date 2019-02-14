import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;

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
		terrainButton.isFocusPainted();
		}
		public void reset() {
			hole = false;
			revealed = false;
			nextToHoles = 0;
			terrainButton.setText(" ");
			terrainButton.setBackground(Color.WHITE); 
		}
}
