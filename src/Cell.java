
import javafx.scene.canvas.*;

public class Cell {
	private boolean visited = false;
	public int row, column, size;
	public boolean[] lines = {true, true, true, true}; //top right bottom left

	Cell(int r, int c, int size) {
		row = r;
		column = c;
		this.size = size;
	}

	public void drawCell(GraphicsContext gc) {
		int row = this.row * size;
		int column = this.column * size;
		if(lines[0])//top
			gc.strokeLine(row, column, row + size, column);
		if(lines[1])//right
			gc.strokeLine(row + size, column, row + size, column + size );
		if(lines[2])//bottom
			gc.strokeLine(row, column + size, row + size, column + size);
		if(lines[3])//left
			gc.strokeLine(row, column, row , column + size);
	}

	public static int index(int row, int column, int rowsNum) {
		return row + (column * rowsNum);
	}

	public boolean isVisted() {
		return visited;
	}
	
	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}
	public void deleteWall(int wall) {
		lines[wall] = false;
	}

	public void deleteWallAll() {
		for(int i = 0; i < lines.length; i++) 
			lines[i] = false;
	}
	
	public void visit(boolean t) {
		visited = t;
	}

	public boolean hasWallTop() {
		return lines[0];
	}
	public boolean hasWallRight() {
		return lines[1];
	}
	public boolean hasWallBottom() {
		return lines[2];
	}
	public boolean hasWallLeft() {
		return lines[3];
	}
}
