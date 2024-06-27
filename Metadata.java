import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

//This class helps us create the background and control the number of score and collisions.
public class Metadata {
	private double width;
	private double height;
	private int numHorizontalLines;
	private int numVerticalLines;
	private int numPaths;
	private int carsToWin;
	private int allowedAccidents;
	private Pane pane;

	public Metadata(double width, double height, int numHorizontalLines, int numVerticalLines, int numPaths,
			int carsToWin, int allowedAccidents) {
		this.width = width;
		this.height = height;
		this.numHorizontalLines = numHorizontalLines;
		this.numVerticalLines = numVerticalLines;
		this.numPaths = numPaths;
		this.carsToWin = carsToWin;
		this.allowedAccidents = allowedAccidents;
		this.pane = new Pane();

		Group root = new Group();

		// These loops draw the horizontal and vertical lines in the background of the
		// map.
		for (int i = 0; i < numHorizontalLines; i++) {
			javafx.scene.shape.Line line = new javafx.scene.shape.Line(0, i * (height / numHorizontalLines), width,
					i * (height / numHorizontalLines));
			line.setStroke(Color.rgb(115, 181, 222, 0.69));
			line.setStrokeWidth(3);
			pane.getChildren().add(line);
		}

		for (int i = 0; i < numVerticalLines; i++) {
			javafx.scene.shape.Line line = new javafx.scene.shape.Line(i * (width / numVerticalLines), 0,
					i * (width / numVerticalLines), height);
			pane.getChildren().add(line);
			line.setStrokeWidth(3);
			line.setStroke(Color.rgb(115, 181, 222, 0.69));
		}

	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public int getNumHorizontalLines() {
		return numHorizontalLines;
	}

	public void setNumHorizontalLines(int numHorizontalLines) {
		this.numHorizontalLines = numHorizontalLines;
	}

	public int getNumVerticalLines() {
		return numVerticalLines;
	}

	public void setNumVerticalLines(int numVerticalLines) {
		this.numVerticalLines = numVerticalLines;
	}

	public int getNumPaths() {
		return numPaths;
	}

	public void setNumPaths(int numPaths) {
		this.numPaths = numPaths;
	}

	public int getCarsToWin() {
		return carsToWin;
	}

	public void setCarsToWin(int carsToWin) {
		this.carsToWin = carsToWin;
	}

	public int getAllowedAccidents() {
		return allowedAccidents;
	}

	public void setAllowedAccidents(int allowedAccidents) {
		this.allowedAccidents = allowedAccidents;
	}

	public String getNumCellsX() {
		return null;
	}

	public Pane getPane() {
		return pane;
	}

}