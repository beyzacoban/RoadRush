import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.Animation;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

//This is our test class. The creation of the map, the construction and operation of the entire game are found in this class.
public class GameEntryScreen extends Application {
	private int numCarsToWin;
	private int numAllowedAccidents;
	private double time = 0;
	private Map<Rectangle, PathTransition> carTransitions = new HashMap<>();
	private List<Rectangle> crashedCars = new ArrayList<>();
	private boolean canSpawnCar = true;
	private List<Rectangle> cars = new ArrayList<>();
	private int crashCount;
	private boolean gameOver = false;
	private int carCount;

	private Pane pane = new Pane();
	private Path path = new Path();
	private boolean carMovementPaused = false;
	Stage primaryStage = new Stage();

	@Override
	public void start(Stage primaryStage) {

		// This is the part where we added the intro image and icon.
		Image image = new Image("entry.png");
		ImageView imageView = new ImageView(image);
		Image icon = new Image("icon.png");
		primaryStage.getIcons().add(icon);

		// Here we added level buttons and put them in a horizontal box.
		Button level1Button = createLevelButton("1", "level1.txt", primaryStage);
		Button level2Button = createLevelButton("2", "level2.txt", primaryStage);
		Button level3Button = createLevelButton("3", "level3.txt", primaryStage);
		Button level4Button = createLevelButton("4", "level4.txt", primaryStage);
		Button level5Button = createLevelButton("5", "level5.txt", primaryStage);
		HBox buttonBox1 = new HBox(10);

		buttonBox1.getChildren().addAll(level1Button, level2Button, level3Button, level4Button, level5Button);
		buttonBox1.setAlignment(Pos.CENTER);

		// There are our return and exit buttons. Return button takes you to the login
		// screen. Exit button exits the game.
		Button returnButton = new Button("Return");
		returnButton.setOnAction(e -> {
			path.getElements().clear();
			stopCarMovements();
			pane.getChildren().clear();

			start(primaryStage);

		});

		returnButton.setStyle("-fx-font-size: 20px; -fx-font-family: Arial; -fx-background-color: #FFD740;");
		returnButton.setLayoutX(720);
		returnButton.setLayoutY(750);
		pane.getChildren().add(returnButton);
		Button startButton = new Button("Levels");
		startButton.setStyle("-fx-font-size: 30px; -fx-font-family: Arial; -fx-background-color: #4CAF50;");

		Button exitButton = new Button("Exit");
		exitButton.setOnAction(e -> primaryStage.close());
		exitButton.setStyle("-fx-font-size: 30px; -fx-font-family: Arial; -fx-background-color: #f44336;");

		exitButton.setOnAction(e -> {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Confirmation");
			alert.setHeaderText("Exiting the game");
			alert.setContentText("Are you sure?");

			DialogPane dialogPane = alert.getDialogPane();
			dialogPane.setStyle("-fx-font-family: 'Arial';");

			Optional<ButtonType> result = alert.showAndWait();

			if (result.isPresent() && result.get() == ButtonType.OK) {
				primaryStage.close();
			}
		});

		// Here we put all buttons at the entry screen.
		VBox buttonBox = new VBox(25);
		buttonBox.getChildren().addAll(startButton, buttonBox1, exitButton);
		buttonBox.setAlignment(Pos.CENTER);

		StackPane layout = new StackPane();
		layout.getChildren().addAll(imageView, buttonBox);
		StackPane.setAlignment(buttonBox, Pos.CENTER);
		Scene scene = new Scene(layout, 800, 800);

		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.setTitle("ROAD RUSH");
		primaryStage.show();

	}

	// This method stops the car movement.
	public void stopCarMovements() {
		for (PathTransition transition : carTransitions.values()) {
			transition.pause();
		}
		canSpawnCar = false;
		carMovementPaused = true;
	}

	// This method restart the car movement.
	public void resumeCarMovements() {
		for (PathTransition transition : carTransitions.values()) {
			transition.play();
		}
		canSpawnCar = true;
		carMovementPaused = false;
	}

	public Button createLevelButton(String buttonText, String levelFileName, Stage primaryStage) {
		Button levelButton = new Button(buttonText);
		levelButton.setStyle("-fx-font-size: 30px; -fx-font-family: Arial; -fx-background-color: #FFD740;");
		levelButton.setOnAction(e -> startGame(primaryStage, levelFileName));
		return levelButton;
	}

	// Here is the game screen.
	public void startGame(Stage primaryStage, String levelFileName) {
		carCount = 0;
		crashCount = 0;
		carTransitions.clear();
		crashedCars.clear();
		cars.clear();
		List<TrafficLight> trafficLights = new ArrayList<>();

		// Pause button stops and restarts the car movement.
		Button pauseButton = new Button("Pause");
		pauseButton.setOnAction(e -> {
			if (!carMovementPaused) {
				stopCarMovements();
			} else {
				resumeCarMovements();
			}
		});

		pauseButton.setStyle("-fx-font-size: 20px; -fx-font-family: Arial; -fx-background-color: #40E0D0;");
		pauseButton.setLayoutX(720);
		pauseButton.setLayoutY(700);
		pane.getChildren().add(pauseButton);

		resumeCarMovements();

		/*
		 * In this try catch block, we read the values ​​from the file, create metadata,
		 * buildings, road tiles, traffic lights and paths and give our map its final
		 * shape. We create objects of our other classes in the switch case and add the
		 * objects to our map one by one.
		 */
		try {

			Scanner scanner = new Scanner(new File(levelFileName));
			double startX = 0;
			double startY = 0;
			double endX = 0;
			double endY = 0;

			String[] metadataTokens = scanner.nextLine().split(" ");
			double screenWidth = Double.parseDouble(metadataTokens[1]);
			double screenHeight = Double.parseDouble(metadataTokens[2]);
			int gridColumns = Integer.parseInt(metadataTokens[3]);
			int gridRows = Integer.parseInt(metadataTokens[4]);
			int numPaths = Integer.parseInt(metadataTokens[5]);
			numCarsToWin = Integer.parseInt(metadataTokens[6]);
			numAllowedAccidents = Integer.parseInt(metadataTokens[7]);

			Metadata metadata = new Metadata(screenWidth, screenHeight, gridRows, gridColumns, numPaths, numCarsToWin,
					numAllowedAccidents);
			metadata.getPane().setBackground(new javafx.scene.layout.Background(
					new javafx.scene.layout.BackgroundFill(Color.rgb(115, 181, 222, 0.22), null, null)));
			Text text = new Text(10, 20,
					"Score: " + carCount + "/" + numCarsToWin + "\nCrashes: " + crashCount + "/" + numAllowedAccidents);
			text.setFont(Font.font("Arial", 15));
			pane.getChildren().add(text);
			pane.setPrefSize(metadata.getWidth(), metadata.getHeight());
			pane.getChildren().addAll(metadata.getPane());

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] tokens = line.split(" ");

				switch (tokens[0]) {
				case "Building":
					int buildingType = Integer.parseInt(tokens[1]);
					int buildingRotation = Integer.parseInt(tokens[2]);
					int buildingColorIndex = Integer.parseInt(tokens[3]);
					int buildingGridX = Integer.parseInt(tokens[4]);
					int buildingGridY = Integer.parseInt(tokens[5]);
					Building building = new Building(buildingType, buildingRotation, buildingColorIndex, buildingGridX,
							buildingGridY);
					pane.getChildren().add(building.getPane());
					break;
				case "RoadTile":
					int type = Integer.parseInt(tokens[1]);
					int rotation = Integer.parseInt(tokens[2]);
					int xIndex = Integer.parseInt(tokens[3]);
					int yIndex = Integer.parseInt(tokens[4]);
					RoadTile roadTile = new RoadTile(type, rotation, xIndex, yIndex);
					pane.getChildren().add(roadTile.getPane());
					break;
				case "TrafficLight":
					startX = Double.parseDouble(tokens[1]);
					startY = Double.parseDouble(tokens[2]);
					endX = Double.parseDouble(tokens[3]);
					endY = Double.parseDouble(tokens[4]);
					TrafficLight trafficLight = new TrafficLight(startX, startY, endX, endY);
					pane.getChildren().add(trafficLight.getPane());
					trafficLights.add(trafficLight);
					break;
				case "Path":
					createTraffic(path, trafficLights);
					createPathAndTraffic(tokens);
					break;
				}
			}

			Scene scene = new Scene(new Group(metadata.getPane(), pane));
			primaryStage.setScene(scene);
			primaryStage.setTitle("ROAD RUSH");
			primaryStage.show();

			scanner.close();
		} catch (FileNotFoundException e) {
			System.err.println("Dosya bulunamadı: " + levelFileName);
		}
	}

	// This is where we generate traffic. We take the path and traffic lights, start
	// the animation timer and call the update method.
	public void createTraffic(Path path, List<TrafficLight> trafficLights) {
		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				update(path, trafficLights);
			}
		};
		timer.start();
	}

	// The update method checks for collisions and score. Allows cars to stop at
	// traffic lights by leaving a certain distance between them.
	public void update(Path path, List<TrafficLight> trafficLights) {
		if (!gameOver) {
			time += 0.001;

			if (time > 5) {
				if (Math.random() < 0.5) {
					spawnCar();
				}
				time = 0;
			}

			checkCollisions();

			if (!gameOver && carCount == numCarsToWin) {
				showWinScreen(primaryStage);
				gameOver = true;
				stopCarMovements();

			}
			if (crashCount == numAllowedAccidents) {
				showGameOver(primaryStage);
				gameOver = true;
				stopCarMovements();
			}

			for (Map.Entry<Rectangle, PathTransition> entry : carTransitions.entrySet()) {
				Rectangle carRect = entry.getKey();
				PathTransition pathTransition = entry.getValue();
				boolean stopCar = false;

				for (TrafficLight trafficLight : trafficLights) {
					if (trafficLight.getLightColor().equals(Color.RED)
							&& isCarNearTrafficLight(carRect, trafficLight)) {
						stopCar = true;
						break;
					}
				}
				if (!stopCar && crashedCars.contains(carRect)) {
					playCar(carRect);
				} else {
					pauseCar(carRect, pathTransition);
				}

				if (stopCar || crashedCars.contains(carRect) || carMovementPaused) {
					pauseCar(carRect, pathTransition);
				} else {
					playCar(carRect);
				}
			}

			for (Map.Entry<Rectangle, PathTransition> entry1 : carTransitions.entrySet()) {
				Rectangle carRect1 = entry1.getKey();
				PathTransition car1Transition = entry1.getValue();

				for (Map.Entry<Rectangle, PathTransition> entry2 : carTransitions.entrySet()) {
					Rectangle carRect2 = entry2.getKey();
					PathTransition car2Transition = entry2.getValue();

					if (carRect1 != carRect2) {
						Bounds car1Bounds = carRect1.localToScene(carRect1.getBoundsInLocal());
						Bounds car2Bounds = carRect2.localToScene(carRect2.getBoundsInLocal());

						double distanceX = Math.abs(car1Bounds.getMinX() - car2Bounds.getMinX());
						double distanceY = Math.abs(car1Bounds.getMinY() - car2Bounds.getMinY());
						double distance = Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));

						double thresholdDistance = 25;
						if (distance <= thresholdDistance && car1Transition.getStatus() == Animation.Status.PAUSED) {
							car2Transition.pause();
						}
					}
				}
			}
		}
	}

	// This method pause the transition.
	public void pauseAllCars() {
		for (PathTransition transition : carTransitions.values()) {
			transition.pause();
		}
	}

	// This method checks whether the car intersects with the traffic light.
	private boolean isCarNearTrafficLight(Rectangle carRect, TrafficLight trafficLight) {
		return carRect.getBoundsInParent().intersects(trafficLight.getPane().getBoundsInParent())
				&& trafficLight.getLightColor().equals(Color.RED);
	}

	// This method pause the car.
	private void pauseCar(Rectangle carRect, PathTransition pathTransition) {
		if (pathTransition != null) {
			if (carRect.getUserData() == null) {
				carRect.setUserData(true);
				pathTransition.pause();
			}
		}

	}

	// This method sets cars in motion.
	private void playCar(Rectangle carRect) {
		if (carRect.getUserData() != null) {
			carRect.setUserData(null);
			PathTransition pathTransition = carTransitions.get(carRect);
			pathTransition.play();
		}
	}

	// This method reads path sections from the file and creates paths using move to
	// and line to.
	public void createPathAndTraffic(String[] tokens) {
		int pathType = Integer.parseInt(tokens[1]);
		double x = Double.parseDouble(tokens[3]);
		double y = Double.parseDouble(tokens[4]);

		if (tokens[2].equals("MoveTo")) {
			path.getElements().add(new MoveTo(x, y));
		} else if (tokens[2].equals("LineTo")) {
			path.getElements().add(new LineTo(x, y));

		}
	}

	/*
	 * The spawnCar method creates the cars, adjusts the movement speed of the cars
	 * by creating a path transition object, and ensures that the car that completes
	 * the path increases the score by one.
	 */
	public void spawnCar() {
		double carWidth = 20;
		double carHeight = 9;
		double startX = 0;
		double startY = 0;

		boolean carCollided = false;

		for (Rectangle car : carTransitions.keySet()) {
			if (car.getBoundsInParent().intersects(startX - carWidth / 2, startY - carHeight / 2, carWidth,
					carHeight)) {
				carCollided = true;
				break;
			}
		}
		if (!carCollided && canSpawnCar && !carMovementPaused) {
			Rectangle carRect = new Rectangle(startX - carWidth / 2, startY - carHeight / 2, carWidth, carHeight);
			carRect.setFill(Color.rgb(74, 74, 74));
			carRect.setArcWidth(5);
			carRect.setArcHeight(5);
			pane.getChildren().add(carRect);
			cars.add(carRect);
			PathTransition pathTransition = new PathTransition();
			pathTransition.setNode(carRect);
			pathTransition.setPath(path);
			pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
			pathTransition.setDuration(Duration.seconds(5));
			pathTransition.setCycleCount(1);
			double pathLength = path.getElements().size();
			double pathDurationSeconds = calculateDuration(pathLength);
			pathTransition.setDuration(Duration.seconds(pathDurationSeconds));
			pathTransition.setInterpolator(Interpolator.LINEAR);
			pathTransition.setAutoReverse(false);
			pathTransition.play();
			canSpawnCar = false;

			carTransitions.put(carRect, pathTransition);

			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					canSpawnCar = true;

				}

			}, 2000);

			pathTransition.setOnFinished(event -> {
				pane.getChildren().remove(carRect);
				cars.remove(carRect);
				carTransitions.remove(carRect);
				carCount++;

				Text scoreText = null;
				for (Node node : pane.getChildren()) {
					if (node instanceof Text) {
						scoreText = (Text) node;
						break;
					}
				}
				if (scoreText != null) {
					scoreText.setText("Score:   " + carCount + "/" + numCarsToWin + "\nCrashes: " + crashCount + "/"
							+ numAllowedAccidents);
				}
			});
		}
	}

	// This method eliminates speed differences caused by different path lengths.
	private double calculateDuration(double pathLength) {
		double durationPerUnit = 0.5;
		return pathLength * durationPerUnit;
	}

	// This method checks for collisions and when cars collide it increases the
	// number of collisions by one and removes the cars from the map.
	public void checkCollisions() {
		List<Rectangle> toRemove = new ArrayList<>();

		for (int i = 0; i < cars.size(); i++) {
			Rectangle carRect1 = cars.get(i);
			for (int j = i + 1; j < cars.size(); j++) {
				Rectangle carRect2 = cars.get(j);

				if (carRect1.getBoundsInParent().intersects(carRect2.getBoundsInParent())) {
					if (!crashedCars.contains(carRect1)) {
						toRemove.add(carRect1);
						cars.remove(carRect1);
					}
					if (!crashedCars.contains(carRect2)) {
						toRemove.add(carRect2);
					}
					crashedCars.add(carRect1);
					crashedCars.add(carRect2);
					crashCount++;
					System.out.println("Collision detected!");
					Text scoreText = null;
					for (Node node : pane.getChildren()) {
						if (node instanceof Text) {
							scoreText = (Text) node;
							break;
						}
					}

					if (scoreText != null) {
						scoreText.setText("Score:   " + carCount + "/" + numCarsToWin + "\nCrashes: " + crashCount + "/"
								+ numAllowedAccidents);

					}

				}
			}
		}
		if (!toRemove.isEmpty()) {
			removeCars(toRemove);
		}
	}

	// This method is called in the checkCollision method. Removes cars from the
	// map.
	private void removeCars(List<Rectangle> carsToRemove) {
		AnimationTimer removeTimer = new AnimationTimer() {
			private long startTime = 0;

			@Override
			public void handle(long now) {
				if (startTime == 0) {
					startTime = now;
				}
				long elapsedTime = now - startTime;
				if (elapsedTime >= 500_000_000) {
					for (Rectangle carRect : carsToRemove) {
						pane.getChildren().remove(carRect);
						cars.remove(carRect);
						crashedCars.remove(carRect);
					}
					stop();
				}
			}
		};
		removeTimer.start();
	}

	// This method provides the gameOver screen, which notifies you that the game is
	// lost when the maximum number of collisions is reached.
	public void showGameOver(Stage primaryStage) {
		Image image = new Image("gameover.jpg");
		ImageView imageView = new ImageView(image);
		VBox gameOverLayout = new VBox(20);
		gameOverLayout.setAlignment(Pos.CENTER);

		Button returnButton = new Button("Exit");
		returnButton.setStyle("-fx-font-size: 30px; -fx-font-family: Arial; -fx-background-color: #f44336;");
		returnButton.setOnAction(e -> {
			primaryStage.close();
		});

		StackPane layout = new StackPane();
		layout.getChildren().addAll(imageView, returnButton);
		StackPane.setAlignment(returnButton, Pos.CENTER);
		Scene gameOverScene = new Scene(layout, 800, 800);

		primaryStage.setScene(gameOverScene);
		primaryStage.setTitle("Game Over");
		primaryStage.show();
	}

	// This method provides the win screen informing you that the game has been won
	// when the target number of cars is reached.
	public void showWinScreen(Stage primaryStage) {
		Image image = new Image("youwon.jpg");
		ImageView imageView = new ImageView(image);
		VBox youWonLayout = new VBox(20);
		youWonLayout.setAlignment(Pos.CENTER);

		Button exitButton = new Button("Exit");
		exitButton.setStyle("-fx-font-size: 30px; -fx-font-family: Arial; -fx-background-color: #f44336;");
		exitButton.setOnAction(e -> {
			primaryStage.close();
		});

		StackPane layout = new StackPane();
		layout.getChildren().addAll(imageView, exitButton);
		exitButton.setTranslateX(0);
		exitButton.setTranslateY(50);

		Scene youWonScene = new Scene(layout, 800, 800);

		primaryStage.setScene(youWonScene);
		primaryStage.setTitle("You Won");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}