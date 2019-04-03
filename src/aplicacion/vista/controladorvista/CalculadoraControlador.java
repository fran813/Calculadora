package aplicacion.vista.controladorvista;

import java.text.DecimalFormat;

import aplicacion.modelo.Operacion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class CalculadoraControlador {
	
	@FXML
	private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0;
	
	@FXML
	private Button btnSalir, btnPunto, btnSumar, btnRestar, btnMultiplicar, btnDividir, btnIgual, btnCe;
	
	@FXML
	private TextField tfPantalla;
	
	@FXML
	private Label lbOperador;
	
	private static final int MAX_DECIMALES = 12;
	
	private boolean nuevaOperacion = false, tieneComa = false, tieneSigno = false;
	private double resultado = 0;
	

	private String operador = "";
	private DecimalFormat decimalFormat;
	
	public CalculadoraControlador() {
		String formatoStr = "#.";
		for (int i = 0; i < MAX_DECIMALES; i++)
			formatoStr += "#";
		decimalFormat = new DecimalFormat(formatoStr);
	}

	@FXML
	private void salir() {
		System.exit(0);
	}
	
	@FXML
	private void anadirDigito(ActionEvent e) {
		anadeDigito(((Button)e.getSource()).getText());
	}
	
	@FXML
	private void teclaPulsada(KeyEvent e) {
		String caracter = e.getCharacter();
		if (caracter.matches("[+\\-\\*/]")) {
			e.consume();
			anadeOperacion(caracter);
		}
		else if (caracter.equals("=")) {
			e.consume();
			realizaOperacion();
		}
		else if (caracter.matches("[0-9,]?")) {
			e.consume();
			anadeDigito(caracter);
		}
		else if (!Character.isISOControl(caracter.charAt(0))) {
			e.consume();
		}
	}
	
	@FXML
	private void anadirComa() {
		if (!tieneComa) {
			tieneComa = true;
			anadeDigito(",");
		}
	}
	
	@FXML
	private void anadirOperacion(ActionEvent e) {
		String operacion = ((Button)e.getSource()).getText();
		anadeOperacion(operacion);
	}
	
	@FXML
	private void inicializa() {
		resultado = 0;
		mostrarEnPantalla("0");
		nuevaOperacion = false;
		tieneComa = false;
		tieneSigno = false;
		lbOperador.setVisible(false);
		operador = "";
	}
	
	@FXML
	private void borrarUltimo() {
		if (!nuevaOperacion) 
		{
			String numeroStr = getNumeroStrPantalla();
			String numeroRestante = numeroStr.substring(0, numeroStr.length() - 1);
			String digitoBorrado = numeroStr.substring(numeroStr.length() - 1, numeroStr.length());
			System.out.println(digitoBorrado);
			if (numeroStr.length() > 1) {
				mostrarEnPantalla(numeroRestante);
				if (digitoBorrado.equals(".")) {
					tieneComa = false;
				}
			}	
			else {
				if (digitoBorrado.equals("-")) {
					tieneSigno = false;
				}
				mostrarEnPantalla("0");
				nuevaOperacion = false;
			}	
		}
	}
	
	@FXML
	private void calculaResultado() {
		realizaOperacion();
	}
	
	private void anadeDigito(String digito) {
		String operando = getNumeroStrPantalla();
		operando = (operando.equals("0")) ? "" : operando;
		if (operando.equals(""))
			mostrarEnPantalla(digito);
		if (nuevaOperacion) {
			mostrarEnPantalla(digito);
			resultado = 0;
			nuevaOperacion = false;
		} else {
			mostrarEnPantalla(operando + digito);
		}
	}
	
	private void anadeOperacion(String operador) {
		if (operador.equals("")) 
			return;
		if (getNumeroStrPantalla().matches("[0-]")) {
			if (!tieneSigno) {
				mostrarEnPantalla("-");
			}
			if (this.operador.equals("")) {
				lbOperador.setText("-");
			}
			tieneSigno = true;
		} else {
			realizaOperacion();
			this.operador = operador;
			lbOperador.setText(operador);
			lbOperador.setVisible(true);
			if (resultado == 0) {
				resultado = getNumeroPantalla();
			}
			mostrarEnPantalla("0");
			nuevaOperacion = false;
			tieneComa = false;
		}
	}
	
	private void realizaOperacion() {
		if (!operador.equals("")) {
			double operando;
			operando = getNumeroPantalla();
			resultado = Operacion.getOperacion(operador).realizaOperacion(resultado, operando);
			mostrarEnPantalla(resultado);
			operador = "";
			nuevaOperacion = true;
			tieneSigno = false;
			tieneComa = false;
			lbOperador.setVisible(false);
		}
	}
	
	private void mostrarEnPantalla(double numero) {
		tfPantalla.setText(decimalFormat.format(numero));
	}

	private void mostrarEnPantalla(String digito) {
		if (digito.equals(",")) {
			digito = "0,";
		}
		tfPantalla.setText(digito.replace(".", ","));
	}

	private double getNumeroPantalla() {
		return new Double(getNumeroStrPantalla().equals("-") ? "0" : getNumeroStrPantalla());
	}
	
	private String getNumeroStrPantalla() {
		String cadenaPantalla = tfPantalla.getText();
		cadenaPantalla = cadenaPantalla.replace(",", ".");
		return cadenaPantalla;
	}
}
