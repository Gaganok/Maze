
import java.util.ArrayList;


import javafx.animation.AnimationTimer;
import javafx.application.*;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.canvas.*;
import javafx.concurrent.*;


public class Main extends Application{
	int height = 900, width = 900;
	int row = 90, column = 90, size = 10, start = 0;
	int builderSpeed = 2, escapistSpeed = 2;

	private int state;

	Canvas canvas = new Canvas(height, width);
	MazeCreator mc = new MazeCreator(row, column, size, start, row*column - 1);
	MazeEscapist me;
	GraphicsContext gc = canvas.getGraphicsContext2D();

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Group group = new Group();
		canvas.setFocusTraversable(true);
		group.getChildren().addAll(canvas);

		primaryStage.setScene(new Scene(group, height, width));
		primaryStage.show();

		state = 0;
		render();
	}

	public void render() {
		new AnimationTimer() {
			@Override
			public void handle(long now) {
				if(state == 0) {
					if(!mc.isReady()) 
						mc.render(gc, height, width);
					else{
						mc.render(gc, height, width);
						me = new MazeEscapist(mc.getMaze());
						state = 1;
					}
				} else if( state == 1) {
					if(!me.isReady()) 
						me.render(gc, height, width);
					else{
						me.render(gc, height, width);
						state = 2;
					}
				} else 
					this.stop();
			}
		}.start();

		new Thread(() -> {
			long updateSpeed = 1;
			long currentTime = System.currentTimeMillis();
			while(true) { 
				if(currentTime + updateSpeed <= System.currentTimeMillis()) {
					if(state == 0) { 
						if(!mc.isReady()) 
							mc.update();
					} else if( state == 1) {
						if(!me.isReady()) 
							me.update();
					} else 
						break;
					currentTime = System.currentTimeMillis();
				}
			}
			try {
				this.stop();
			} catch (Exception e) {
				System.out.println(e);
			}
		}).start();
	} 

}


