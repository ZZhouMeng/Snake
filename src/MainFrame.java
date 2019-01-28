import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainFrame extends Application {
	BorderPane pane=new BorderPane();
	VBox vbox=new VBox(20);
	
	@Override
	public void start(Stage Stage) throws Exception {
		Canvas canvas = new Canvas(Config.WIN_WIDTH, Config.WIN_HEIGHT);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		Driver driver = new Driver(gc);
		pane.setCenter(canvas);

		vbox.setAlignment(Pos.CENTER);
		vbox.setPadding(new Insets(30,30,30,30));
		Label lb1=new Label("P:��ͣ��Ϸ");
		Label lb2=new Label("R:���¿�ʼ");
		Label lb3=new Label("��:�����˶�");
		Label lb4=new Label("��:�����˶�");
		Label lb5=new Label("��:�����˶�");
		Label lb6=new Label("��:�����˶�");
		vbox.getChildren().addAll(lb1,lb2,lb3,lb4,lb5,lb6);
		pane.setRight(vbox);
		
		Scene scene = new Scene(pane,700,400);
		scene.setOnKeyPressed(driver);

		Stage.setScene(scene);
		Stage.setTitle("̰����");
		Stage.setOnCloseRequest(e -> {
			System.exit(0);
		});
		Stage.show();
		new Thread(driver).start();

	}

}
