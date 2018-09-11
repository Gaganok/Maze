

import java.util.ArrayList;
import java.util.Stack;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class MazeEscapist implements MazeElement {
	private Stack<Cell> escapeRoute = new Stack<Cell>();
	private Maze maze;
	private int escape, start, columns, rows;
	private Cell current;
	private Cell[] cells;
	private boolean escaped;
	
	MazeEscapist(Maze maze){
		this.maze = maze;
		this.maze.resetVisit();
		this.cells = this.maze.getCells();
		this.start = maze.getStart();
		this.escape = maze.getEscape();
		this.columns = maze.getColumns();
		this.rows = maze.getRows();
		this.current = cells[start];
		
		current.visit(true);
		escaped = false;
	}
	
	public boolean isReady() {
		return escaped;
	}

	private void genEscStep() {
		if(current != cells[escape]) {
			Cell next = getNeighbour();
			if(next != null) {
				
				escapeRoute.push(current);
				current = next;
				current.visit(true);
				
			} else 
				current = escapeRoute.pop();
		} else
			escaped = true;
	}
	
	private Cell getNeighbour() {
		Stack<Cell> neighbours = new Stack<Cell>();
		Cell neighbour;
		// top
		if((current.getColumn() - 1) >= 0) {
			neighbour = cells[Cell.index(current.getRow(), current.getColumn() - 1, rows)];
			if(!neighbour.hasWallBottom())
				if(!neighbour.isVisted())
					neighbours.push(neighbour);
		}
		//right
		if((current.getRow() + 1) < rows) {
			neighbour = cells[Cell.index(current.getRow() + 1, current.getColumn(), rows)];
			if(!neighbour.hasWallLeft())
				if(!neighbour.isVisted())
					neighbours.push(neighbour);
		}
		//bottom
		if((current.getColumn() + 1) < columns) {
			neighbour = cells[Cell.index(current.getRow(), current.getColumn() + 1, rows)];
			if(!neighbour.hasWallTop() && !neighbour.isVisted())
				neighbours.push(neighbour);
		}
		//left
		if((current.getRow() - 1) >= 0) {
			neighbour = cells[Cell.index(current.getRow() - 1, current.getColumn(), rows)];
			if(!neighbour.hasWallRight() && !neighbour.isVisted())
				neighbours.push(neighbour);
		}
		
		int x = (int)(Math.random() * neighbours.size());
		System.out.println(neighbours.size() + " " + x);
		if(!neighbours.isEmpty())
			return neighbours.get(x);

		return null;
	}
	
	public void render(GraphicsContext gc, int height, int width) {
		int size = maze.getSize();
		
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, width, height);
		gc.setFill(new Color(0.9, 0, 0.3, 0.3));
		gc.fillRect(current.row * size, current.column * size, size, size);
		
		gc.setFill(new Color(0.9, 0, 0.3, 0.3));
		gc.fillRect(cells[escape].row * size, cells[escape].column * size, size, size);
		
		gc.setFill(Color.BLACK);
		maze.drawMaze(gc);
		
		gc.setFill(new Color(0.2, 0.1, 0.9, 0.1));
		for(int i = 0; i < escapeRoute.size(); i++) {
			Cell c = escapeRoute.get(i);
			gc.fillRect(c.getRow() * size, c.getColumn() * size, size , size);
		}
			
	}
	public void update() {
		genEscStep();
	}
}
