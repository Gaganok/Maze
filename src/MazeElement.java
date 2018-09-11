

import javafx.scene.canvas.GraphicsContext;

public interface MazeElement {
	public boolean isReady();
	public void render(GraphicsContext gc, int height, int width);
	public void update();
}
