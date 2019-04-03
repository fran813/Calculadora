package aplicacion.modelo;

import java.util.HashMap;
import java.util.Map;

public enum Operacion {
	SUMAR {
		public double realizaOperacion(double operando1, double operando2) {
			return operando1 + operando2;
		}
	},
	RESTAR {
		public double realizaOperacion(double operando1, double operando2) {
			return operando1 - operando2;
		}
	},
	MULTIPLICAR {
		public double realizaOperacion(double operando1, double operando2) {
			return operando1 * operando2;
		}
	},
	DIVIDIR {
		public double realizaOperacion(double operando1, double operando2) {
			return operando1 / operando2;
		}
	};
	
	private static Map<String, Operacion> operadores = new HashMap<>();
	static {
		operadores.put("+", SUMAR);
		operadores.put("-", RESTAR);
		operadores.put("*", MULTIPLICAR);
		operadores.put("/", DIVIDIR);
	}
		
	public abstract double realizaOperacion(double operando1, double operando2);
	
	public static Operacion getOperacion(String operador) {
		return operadores.get(operador);
	}
}
