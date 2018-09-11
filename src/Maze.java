
import java.util.ArrayList;

import javafx.scene.canvas.*;

public class Maze {
	private Cell[] cells;
	private int rows, columns;
	private int start, escape;
	private int size;
	private int visited = 0;
	
	Maze(int rows, int columns, int size) {
		this.setRows(rows);
		this.setColumns(columns);
		this.size = size;
	}
	
	public void drawMaze(GraphicsContext gc) {
		for(int i = 0; i < cells.length; i++)
			cells[i].drawCell(gc);
	}

	public void setCells(Cell[] cells) {
		this.cells = cells;
	}
	
	public void resetVisit() {
		for(int i = 0; i < cells.length; i++)
			cells[i].visit(false);
	}
	
	public boolean hasUnvisited() {
		return cells.length > visited;
	}

	public void setEscape(int escape) {
		this.escape = escape;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getStart() {
		return start;
	}
	
	public int getEscape() {
		return escape;
	}

	public Cell[] getCells() {
		return cells;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}
