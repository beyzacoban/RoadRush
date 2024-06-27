import javafx.scene.layout.Pane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;

//This class allows us to create paths in the map.
public class Path {
	private int type;
	private double x;
	private double y;
	private Pane pane;
	private Path path;

	public Path() {
		this(0, 0, 0);
	}

	public Path(int type, double x, double y) {
		this.type = type;
		this.x = x;
		this.y = y;
		this.pane = new Pane();
		this.path = new Path();
	}

	public MoveTo moveTo() {
		return new MoveTo(x, y);
	}

	public LineTo lineTo() {
		return new LineTo(x, y);
	}

	public Pane getPane() {
		return pane;
	}

	public double getStartX() {
		return x;
	}

	public double getStartY() {
		return y;
	}

	public Path getPath() {
		return path;
	}
}