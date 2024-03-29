package electric.circuits;

import electric.circuits.component.ElectricComponent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author stavr
 */
public class SandboxComponent {

	private int gridX;
	private int gridY;

	private final ImageView imageView;

	private final SandboxPane pane;
	private final ElectricComponent component;

	private SandboxWire wireLeft, wireRight;

	public SandboxComponent(SandboxPane pane, ElectricComponent component) {
		this.pane = pane;
		this.component = component;
		this.imageView = new ImageView(component.getType().getImage());

		this.wireLeft = new SandboxWire(pane);
		this.wireRight = new SandboxWire(pane);

	}

	public void initialize() {
		// Update the position of the ImageView
		updatePosition();

		// Add the elements to the pane
		pane.getChildren().addAll(imageView);
		wireLeft.initialize(this, true);
		wireRight.initialize(this, false);
		Utils.connect(component, wireLeft.wire(), wireRight.wire());

		imageView.setPickOnBounds(true);
		imageView.setOnDragDetected(e -> {
			wireLeft.removeFromPane();
			wireRight.removeFromPane();
			pane.getChildren().removeAll(imageView);
			pane.components().remove(this);

			Utils.startDrag(pane, component.getType());
			pane.runSimulation();
		});

		imageView.setOnMouseClicked(e -> {
			pane.setSelectedObject(this);
			if (!e.isDragDetect()) {
				e.consume();
			}
		});
	}

	public void setImage(Image image) {
		imageView.setImage(image);
	}

	public void removeFromPane() {
		pane.getChildren().remove(imageView);
		wireLeft.removeFromPane();
		wireRight.removeFromPane();
	}

	public void move(int gridX, int gridY) {
		this.gridX = gridX;
		this.gridY = gridY;
		updatePosition();
	}

	private void updatePosition() {
		imageView.setX(gridX * SandboxPane.GRID_SIZE);
		imageView.setY(gridY * SandboxPane.GRID_SIZE);
	}

	public int getGridX() {
		return gridX;
	}

	public int getGridY() {
		return gridY;
	}

	public ImageView getImageView() {
		return imageView;
	}

	public ElectricComponent getComponent() {
		return component;
	}

}
