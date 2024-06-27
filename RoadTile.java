import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Rectangle;

public class RoadTile {
	private int roadType;
	private int rotation;
	private int gridX;
	private int gridY;
	private double cellWidth = 800 / 15;
	private double cellHeight = 800 / 15;
	Pane pane;

	public RoadTile(int roadType, int rotation, int gridX, int gridY) {
		this.roadType = roadType;
		this.rotation = rotation;
		this.gridX = gridX;
		this.gridY = gridY;
		this.pane = new Pane();
		double X = gridX * cellWidth;
		double Y = gridY * cellHeight;
		double width = cellWidth;
		double height = cellHeight;

		// This switch case adjusts the shape and orientation of roads.
		switch (roadType) {

		case 0:
			Rectangle rect = new Rectangle(X + 3, Y + 2, width, height);
			rect.setFill(Color.rgb(245, 245, 245, 0.99));
			pane.getChildren().add(rect);
			rect.setRotate(rotation);
			break;

		case 1:

			double centerX = X + 800 / 30;
			double centerY = Y + 800 / 30;

			double radius = 800 / 15;

			double length = 90;

			Arc arc;

			if (rotation == 90) {
				arc = new Arc(centerX + (800 / 30) + 4, centerY + (800 / 30) + 3, radius, radius, rotation, length);
			} else if (rotation == 0) {
				arc = new Arc(centerX - 800 / 30 + 2, centerY + (800 / 30) + 3, radius, radius, rotation, length);
			} else if (rotation == 180) {
				arc = new Arc(centerX + (800 / 30) + 4, centerY - (800 / 30) + 2, radius, radius, rotation, length);
			} else {
				arc = new Arc(centerX - (800 / 30) + 2, centerY - (800 / 30) + 2, radius, radius, rotation, length);

			}

			arc.setFill(Color.rgb(245, 245, 245, 0.99));
			arc.setStroke(Color.rgb(245, 245, 245, 0.99));
			arc.setType(ArcType.ROUND);

			pane.getChildren().addAll(arc);

			break;

		case 2:
			double x = cellWidth;
			double y = cellHeight;

			Rectangle rectangle1 = new Rectangle(X + 3, Y + 2, width, height);
			rectangle1.setFill(Color.rgb(242, 242, 242, 0.97));

			Rectangle rectangle2 = new Rectangle(X + 3, Y + 2, width, height);
			rectangle2.setFill(Color.rgb(242, 242, 242, 0.97));
			rectangle2.setRotate(90);

			pane.getChildren().addAll(rectangle1, rectangle2);
			break;
		case 3:
			double rect1X = X;
			double rect1Y = Y;
			double rect1Width = width;
			double rect1Height = height;

			Rectangle rect1 = new Rectangle(X + 3, rect1Y + 2, 800 / 15, 800 / 15);
			rect1.setFill(Color.rgb(245, 245, 245, 0.98));
			rect1.setRotate(rotation);

			pane.getChildren().addAll(rect1);
			break;

		default:
			throw new IllegalArgumentException("Invalid road type");

		}

	}

	public int getRoadType() {
		return roadType;
	}

	public void setRoadType(int roadType) {
		this.roadType = roadType;
	}

	public double getRotation() {
		return rotation;
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
	}

	public int getGridX() {
		return gridX;
	}

	public void setGridX(int gridX) {
		this.gridX = gridX;
	}

	public int getGridY() {
		return gridY;
	}

	public void setGridY(int gridY) {
		this.gridY = gridY;
	}

	public double getCellWidth() {
		return cellWidth;
	}

	public void setCellWidth(double cellWidth) {
		this.cellWidth = cellWidth;
	}

	public double getCellHeight() {
		return cellHeight;
	}

	public void setCellHeight(double cellHeight) {
		this.cellHeight = cellHeight;
	}

	public Node getPane() {
		return pane;
	}

}