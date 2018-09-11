

import java.util.Stack;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class MazeCreator implements MazeElement{
	Maze maze;
	int rows, columns, start, escape, size;
	Stack<Cell> route = new Stack<Cell>();
	Cell current;
	Cell[] cells;
	boolean ready = false;


	public MazeCreator(int rows, int columns, int cellSize, int start, int escape) {
		maze = new Maze(rows, columns, cellSize);
		maze.setEscape(escape);
		maze.setStart(start);

		this.rows = rows;
		this.columns = columns;
		this.size = cellSize;
		this.start = start;
		this.escape = escape;
		
		cells = new Cell[rows * columns];
		fillCells(cellSize);
		setStart(start);
		//generateMaze();
		clearStartEscape(start, escape);
		
	

	}
	
	private void setStart(int start) {
		current = cells[start];
		current.visit(true);
		route.push(current);
	}

	private void fillCells(int size) {
		for(int c = 0; c < columns; c++)
			for(int r = 0; r < rows; r++)
				cells[Cell.index(r, c, rows)] = new Cell(r, c, size);
	}

	private void generateMaze() {
		while(!route.isEmpty()) {
			Cell next = getNeighbour();
			if(next != null) {
				deleteWall(current, next);

				current = next;
				current.visit(true);
				route.push(current);

			} else {
				current = route.pop();
			}	
		}
		ready = true;
	}

	private void genMazeStep() {
		if(!route.isEmpty()) {
			Cell next = getNeighbour();
			if(next != null) {
				deleteWall(current, next);

				current = next;
				current.visit(true);
				route.push(current);

			} else {
				current = route.pop();
			}	
		} else 
			ready = true;
	}

	private void deleteWall(Cell current, Cell next) {
		//top
		if(current.getColumn() - next.getColumn() == 1) {
			current.deleteWall(0);
			next.deleteWall(2);
			//right
		} else if(current.getRow() - next.getRow() == -1) {
			current.deleteWall(1);
			next.deleteWall(3);
			//bottom
		}  else if(current.getColumn() - next.getColumn() == -1) {
			current.deleteWall(2);
			next.deleteWall(0);
			//left
		}  else if(current.getRow() - next.getRow() == 1) {
			current.deleteWall(3);
			next.deleteWall(1);
		}

	}

	private void clearStartEscape(int start, int escape) {
		Cell startCell = cells[start];
		startCell.deleteWallAll();
		//top
		if((startCell.getColumn() - 1) >= 0) 
			cells[Cell.index(startCell.getRow(), startCell.getColumn() - 1, rows)].deleteWall(0);
		//right
		if((startCell.getRow() + 1) < rows) 
			cells[Cell.index(startCell.getRow() + 1, startCell.getColumn(), rows)].deleteWall(1);
		//bottom
		if((startCell.getColumn() + 1) < columns) 
			cells[Cell.index(startCell.getRow(), startCell.getColumn() + 1, rows)].deleteWall(2);
		//left
		if((startCell.getRow() - 1) >= 0) 
			cells[Cell.index(startCell.getRow() - 1, startCell.getColumn(), rows)].deleteWall(1);
		
		startCell = cells[escape];
		startCell.deleteWallAll();
		//top
		if((startCell.getColumn() - 1) >= 0) 
			cells[Cell.index(startCell.getRow(), startCell.getColumn() - 1, rows)].deleteWall(0);
		//right
		if((startCell.getRow() + 1) < rows) 
			cells[Cell.index(startCell.getRow() + 1, startCell.getColumn(), rows)].deleteWall(1);	
		//bottom
		if((startCell.getColumn() + 1) < columns) 
			cells[Cell.index(startCell.getRow(), startCell.getColumn() + 1, rows)].deleteWall(2);
		//left
		if((startCell.getRow() - 1) >= 0) 
			cells[Cell.index(startCell.getRow() - 1, startCell.getColumn(), rows)].deleteWall(1);
							
	}

	private Cell getNeighbour() {
		Stack<Cell> neighbours = new Stack<Cell>();
		Cell neighbour;
		// top
		if((current.getColumn() - 1) >= 0) {
			neighbour = cells[Cell.index(current.getRow(), current.getColumn() - 1, rows)];
			if(!neighbour.isVisted())
				neighbours.push(neighbour);
		}
		//right
		if((current.getRow() + 1) < rows) {
			neighbour = cells[Cell.index(current.getRow() + 1, current.getColumn(), rows)];
			if(!neighbour.isVisted())
				neighbours.push(neighbour);
		}
		//bottom
		if((current.getColumn() + 1) < columns) {
			neighbour = cells[Cell.index(current.getRow(), current.getColumn() + 1, rows)];
			if(!neighbour.isVisted())
				neighbours.push(neighbour);
		}
		//left
		if((current.getRow() - 1) >= 0) {
			neighbour = cells[Cell.index(current.getRow() - 1, current.getColumn(), rows)];
			if(!neighbour.isVisted())
				neighbours.push(neighbour);
		}
		
		int x = (int)(Math.random() * neighbours.size());
		System.out.println(neighbours.size() + " " + x);
		if(!neighbours.isEmpty())
			return neighbours.get(x);

		return null;
	}

	public Maze getMaze() {
		maze.setCells(cells);
		return maze;
	}

	public void update() {
		genMazeStep();
	}

	public void render(GraphicsContext gc, int height, int width) {
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, width, height);
		gc.setFill(new Color(0.9, 0, 0.3, 0.3));
		gc.fillRect(current.row * size, current.column * size, size, size);
		gc.setFill(new Color(0.1, 0.9, 0.9, 0.3));
		gc.fillRect(cells[escape].row * size, cells[escape].column * size, size, size);
		gc.setFill(Color.BLACK);
		getMaze().drawMaze(gc);

	}
	
	public boolean isReady() {
		return ready;
	}

}
