import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

public class Building extends Rectangle {

	private Color[] colors = { Color.rgb(255, 168, 71), Color.rgb(112, 199, 255), Color.rgb(255, 130, 178),
			Color.rgb(138, 220, 51) };
	private int type;
	private int rotation;
	private int colorIndex;
	private int xIndex;
	private int yIndex;
	private Pane pane;

	public Building(int type, int rotation, int colorIndex, int xIndex, int yIndex) {
		this.type = type;
		this.rotation = rotation;
		this.colorIndex = colorIndex;
		this.xIndex = xIndex;
		this.yIndex = yIndex;
		this.pane = new Pane();

		// This switch case adjusts the shape and orientation of buildings.
		switch (type) {
		case 0:

			Rectangle bigRect = new Rectangle(3 * (800 / 15), 2 * (800 / 15));
			bigRect.setRotate(rotation + 90);
			bigRect.setStroke(Color.GRAY);
			bigRect.setFill(Color.WHITE);
			Rectangle square = new Rectangle(1.6 * (800 / 15), 1.6 * (800 / 15));
			square.setFill(colors[colorIndex]);
			Rectangle tinySquare = new Rectangle(1.4 * (800 / 15), 1.4 * (800 / 15));
			tinySquare.setFill(colors[colorIndex]);
			tinySquare.setStroke(Color.rgb(255, 255, 255, 0.35));
			tinySquare.setStrokeWidth(3);

			if (rotation == 0) {
				bigRect.setTranslateX(xIndex * (800 / 15) - 0.5 * (800 / 15) + 2);
				bigRect.setTranslateY(yIndex * (800 / 15) + 0.5 * (800 / 15) + 4);

				square.setTranslateX(xIndex * (800 / 15) - 0.5 * (800 / 15) + 40);
				square.setTranslateY(yIndex * (800 / 15) + 15);

				tinySquare.setTranslateX(xIndex * (800 / 15) - 0.5 * (800 / 15) + 45);
				tinySquare.setTranslateY(yIndex * (800 / 15) + 20);

			} else if (rotation == 90) {
				bigRect.setTranslateX(xIndex * (800 / 15) + 2);
				bigRect.setTranslateY(yIndex * (800 / 15) + 4);

				square.setTranslateX(xIndex * (800 / 15) + 13);
				square.setTranslateY(yIndex * (800 / 15) + 15);

				tinySquare.setTranslateX(xIndex * (800 / 15) + 18);
				tinySquare.setTranslateY(yIndex * (800 / 15) + 20);

			} else if (rotation == 180) {
				bigRect.setTranslateX(xIndex * (800 / 15) - 0.5 * (800 / 15) + 2);
				bigRect.setTranslateY(yIndex * (800 / 15) + 0.5 * (800 / 15) + 4);

				square.setTranslateX(xIndex * (800 / 15) - 0.5 * (800 / 15) + 40);
				square.setTranslateY(yIndex * (800 / 15) - 0.5 * (800 / 15) + 92);

				tinySquare.setTranslateX(xIndex * (800 / 15) - 0.5 * (800 / 15) + 45);
				tinySquare.setTranslateY(yIndex * (800 / 15) - 0.5 * (800 / 15) + 97);

			} else {
				bigRect.setTranslateX(xIndex * (800 / 15));
				bigRect.setTranslateY(yIndex * (800 / 15) + 3);

				square.setTranslateX(xIndex * (800 / 15) + 40);
				square.setTranslateY(yIndex * (800 / 15) + 13);

				tinySquare.setTranslateX(xIndex * (800 / 15) + 45);
				tinySquare.setTranslateY(yIndex * (800 / 15) + 18);

			}
			bigRect.setArcWidth(10);
			bigRect.setArcHeight(10);
			square.setArcWidth(8);
			square.setArcHeight(8);
			tinySquare.setArcWidth(6);
			tinySquare.setArcHeight(6);

			pane.getChildren().addAll(bigRect, square, tinySquare);
			break;
		case 1:
			Rectangle bigRect1 = new Rectangle(3 * (800 / 15), 2 * (800 / 15));
			bigRect1.setRotate(rotation + 90);
			bigRect1.setStroke(Color.GRAY);
			bigRect1.setFill(Color.WHITE);

			Circle circle = new Circle(0.9 * (800 / 15));
			circle.setFill(colors[colorIndex]);

			Circle tinyCircle = new Circle(0.8 * (800 / 15));
			tinyCircle.setStroke(Color.rgb(255, 255, 255, 0.35));
			tinyCircle.setStrokeWidth(3);
			tinyCircle.setFill(colors[colorIndex]);

			if (rotation == 0) {
				bigRect1.setTranslateX(xIndex * (800 / 15) - 0.5 * (800 / 15) + 3);
				bigRect1.setTranslateY(yIndex * (800 / 15) + 0.5 * (800 / 15) + 3);

				circle.setCenterX(xIndex * (800 / 15) - 0.5 * (800 / 15) + 83);
				circle.setCenterY(yIndex * (800 / 15) + 58);

				tinyCircle.setCenterX(xIndex * (800 / 15) - 0.5 * (800 / 15) + 83);
				tinyCircle.setCenterY(yIndex * (800 / 15) + 58);
			} else if (rotation == 90) {
				bigRect1.setTranslateX(xIndex * (800 / 15) - 0.5 * (800 / 15) + 3);
				bigRect1.setTranslateY(yIndex * (800 / 15) + 0.5 * (800 / 15) + 3);

				circle.setCenterX(xIndex * (800 / 15) + 83);
				circle.setCenterY(yIndex * (800 / 15) + 0.5 * (800 / 15) + 58);

				tinyCircle.setCenterX(xIndex * (800 / 15) - 0.5 * (800 / 15) + 83);
				tinyCircle.setCenterY(yIndex * (800 / 15) + 0.5 * (800 / 15) + 58);
			} else if (rotation == 180) {
				bigRect1.setTranslateX(xIndex * (800 / 15) - 0.5 * (800 / 15) + 3);
				bigRect1.setTranslateY(yIndex * (800 / 15) + 0.5 * (800 / 15) + 3);

				circle.setCenterX(xIndex * (800 / 15) - 0.5 * (800 / 15) + 83);
				circle.setCenterY(yIndex * (800 / 15) + 1 * (800 / 15) + 55);

				tinyCircle.setCenterX(xIndex * (800 / 15) - 0.5 * (800 / 15) + 83);
				tinyCircle.setCenterY(yIndex * (800 / 15) + 1 * (800 / 15) + 55);
			}

			else {
				bigRect1.setTranslateX(xIndex * (800 / 15) + 3);
				bigRect1.setTranslateY(yIndex * (800 / 15));

				circle.setCenterX(xIndex * (800 / 15) + 108);
				circle.setCenterY(yIndex * (800 / 15) + 55);

				tinyCircle.setCenterX(xIndex * (800 / 15) + 108);
				tinyCircle.setCenterY(yIndex * (800 / 15) + 55);
			}
			bigRect1.setArcWidth(10);
			bigRect1.setArcHeight(10);

			pane.getChildren().addAll(bigRect1, circle, tinyCircle);
			break;
		case 2:
			Rectangle smallRect = new Rectangle(1.14 * (800 / 15), 1.14 * (800 / 15));
			smallRect.setTranslateX(xIndex * (800 / 15));
			smallRect.setTranslateY(yIndex * (800 / 15));
			smallRect.setRotate(rotation);
			smallRect.setFill(colors[colorIndex]);
			smallRect.setArcWidth(10);
			smallRect.setArcHeight(10);
			pane.getChildren().addAll(smallRect);
			break;
		}

	}

	public Pane getPane() {
		return pane;
	}

	public Color[] getColors() {
		return colors;
	}

	public void setColors(Color[] colors) {
		this.colors = colors;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getRotation() {
		return rotation;
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
	}

	public int getColorIndex() {
		return colorIndex;
	}

	public void setColorIndex(int colorIndex) {
		this.colorIndex = colorIndex;
	}

	public int getxIndex() {
		return xIndex;
	}

	public void setxIndex(int xIndex) {
		this.xIndex = xIndex;
	}

	public int getyIndex() {
		return yIndex;
	}

	public void setyIndex(int yIndex) {
		this.yIndex = yIndex;
	}

	public void setPane(Pane pane) {
		this.pane = pane;
	}

}