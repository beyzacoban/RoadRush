import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

//This class allows traffic lights on the map to be created and their colors to change.
public class TrafficLight {
	private Line line;
	private Circle circle;
	private Pane pane;
	private double startX;
	private double startY;

	// The traffic light consists of a circle and a line that change color when
	// pressed.
	public TrafficLight(double startX, double startY, double endX, double endY) {
		this.startX = startX;
		this.startY = startY;

		line = new Line(0, 0, endX - startX, endY - startY);
		line.setStrokeWidth(1);
		double centerX = (startX + endX) / 2;
		double centerY = (startY + endY) / 2;
		circle = new Circle(centerX - startX, centerY - startY, 6);
		circle.setFill(Color.LIGHTGREEN);
		circle.setStroke(Color.BLACK);

		circle.setOnMouseClicked(e -> {
			if (circle.getFill() == Color.LIGHTGREEN) {
				circle.setFill(Color.RED);
			} else {
				circle.setFill(Color.LIGHTGREEN);
			}
		});

		pane = new Pane();
		pane.getChildren().addAll(line, circle);
		pane.setLayoutX(startX);
		pane.setLayoutY(startY);
	}

	public Node getPane() {
		return pane;
	}

	public Color getLightColor() {
		if (circle.getFill() == Color.LIGHTGREEN) {
			return Color.LIGHTGREEN;
		} else {
			return Color.RED;
		}
	}

	public double getStartX() {
		return startX;
	}

	public double getStartY() {
		return startY;
	}

	public double getWidth() {
		return circle.getRadius() * 2;
	}

	public double getHeight() {
		return circle.getRadius() * 2;
	}
}