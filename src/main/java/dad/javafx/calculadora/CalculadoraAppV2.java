package dad.javafx.calculadora;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class CalculadoraAppV2 extends Application {
	
	// modelo
	
	private DoubleProperty operandoA = new SimpleDoubleProperty();
	private DoubleProperty operandoB = new SimpleDoubleProperty();
	private DoubleProperty operandoC = new SimpleDoubleProperty();
	private DoubleProperty operandoD = new SimpleDoubleProperty();
	private DoubleProperty resultadoA = new SimpleDoubleProperty();
	private DoubleProperty resultadoB = new SimpleDoubleProperty();
	private StringProperty operador = new SimpleStringProperty();
	
	private Complejo operando1=new Complejo();
	private Complejo operando2=new Complejo();
	private Complejo resultado=new Complejo();
	
	
	// vista
	
	private TextField operandoAText;
	private TextField operandoBText;
	private TextField operandoCText;
	private TextField operandoDText;
	
	private ComboBox<String> operadorCombo;
	
	
	private TextField resultadoAText;
	private TextField resultadoBText;
	
	private Separator separador;
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		operandoAText = new TextField();
		operandoAText.setPrefColumnCount(4);

		operandoBText = new TextField();
		operandoBText.setPrefColumnCount(4);
		
		operandoCText = new TextField();
		operandoCText.setPrefColumnCount(4);
		
		operandoDText = new TextField();
		operandoDText.setPrefColumnCount(4);
		
		
		resultadoAText = new TextField();
		resultadoAText.setPrefColumnCount(4);
		resultadoAText.setDisable(true);
		
		resultadoBText = new TextField();
		resultadoBText.setPrefColumnCount(4);
		resultadoBText.setDisable(true);
		
		
		separador=new Separator();
		
		
		operadorCombo = new ComboBox<String>();
		operadorCombo.getItems().addAll("+", "-", "*", "/");
		
		HBox root1 = new HBox(5, operandoAText,new Label("+"), operandoBText, new Label("i"));
		root1.setAlignment(Pos.CENTER);
		
		HBox root2= new HBox(5, operandoCText,new Label("+"), operandoDText, new Label("i"));
		root2.setAlignment(Pos.CENTER);
		
		HBox root3 = new HBox(5, resultadoAText,new Label("+"), resultadoBText, new Label("i"));
		root3.setAlignment(Pos.CENTER);
		//root3.disableProperty().set(true);
		
		
		
		VBox rootV1=new VBox(5, operadorCombo);
		rootV1.setAlignment(Pos.CENTER);
		
		separador=new Separator();
		
		VBox rootV2=new VBox(5, root1,root2,separador,root3);
		rootV2.setAlignment(Pos.CENTER); 
		
		
		
		HBox root=new HBox(5,rootV1,rootV2);
		root.setAlignment(Pos.CENTER); 
		Scene scene = new Scene(root, 320, 200);
		
		primaryStage.setTitle("Calculadora Compleja");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		// bindeos
		
		
		Bindings.bindBidirectional(operandoAText.textProperty(), operandoA, new NumberStringConverter());
		Bindings.bindBidirectional(operandoBText.textProperty(), operandoB, new NumberStringConverter());
		Bindings.bindBidirectional(operandoCText.textProperty(), operandoC, new NumberStringConverter());
		Bindings.bindBidirectional(operandoDText.textProperty(), operandoD, new NumberStringConverter());
		Bindings.bindBidirectional(resultadoAText.textProperty(), resultadoA, new NumberStringConverter());
		Bindings.bindBidirectional(resultadoBText.textProperty(), resultadoB, new NumberStringConverter());


		operador.bind(operadorCombo.getSelectionModel().selectedItemProperty());
		
		// listeners
		
		operador.addListener((o, ov, nv) -> onOperadorChanged(nv));
		operadorCombo.getSelectionModel().selectFirst();
		
		
	}

	private void onOperadorChanged(String nv) {

		switch(nv) {
		case "+":
			resultadoA.bind(operandoA.add(operandoC));
			resultadoA.bind( 
			resultadoB.bind(operandoB.add(operandoD));
			break;
		case "-":
			resultadoA.bind(operandoA.subtract(operandoC));
			resultadoB.bind(operandoB.subtract(operandoD));
			break;
		case "*": 
			resultadoA.bind((operandoA.multiply(operandoC)).subtract(operandoB.multiply(operandoD)));
			resultadoB.bind((operandoA.multiply(operandoD)).add(operandoB.multiply(operandoC)));
			break;
		case "/":
			resultadoA.bind(((operandoA.multiply(operandoC)).add(operandoB.multiply(operandoD))).divide
					((operandoC.multiply(operandoC)).add((operandoD.multiply(operandoD)))));
			resultadoB.bind(((operandoB.multiply(operandoC)).subtract(operandoA.multiply(operandoD))).divide
					((operandoC.multiply(operandoC)).add((operandoD.multiply(operandoD)))));
			break;
		}
		
	}

	public static void main(String[] args) {
		launch(args);
	}

}
