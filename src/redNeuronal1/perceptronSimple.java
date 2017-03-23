package redNeuronal1;

import java.util.Random;

/**
 *
 * @author jesus-cruz
 */
public class perceptronSimple {
	
	// Datos para probar, más adelante vendrán de un archivo
	float[][] datos = { { -1, -1, -1 }, 
						{ -1, 1, -1 }, 
						{ 1, -1, -1 }, 
						{ 1, 1, 1 } };

	float[][] datosNand = { { -1, -1, 1 }, { -1, 1, 1 }, { 1, -1, 1 }, { 1, 1, -1 } };
	float[][] datosOr = { { -1, -1, -1 }, { -1, 1, 1 }, { 1, -1, 1 }, { 1, 1, 1 } };
	float[][] datosXor = { { -1, -1, -1 }, { -1, 1, 1 }, { 1, -1, 1 }, { 1, 1, -1 } };
	
	// La función signo
	int funcionSigno(float f) {
		// Positiva
		if (f > 0) {
			return 1;
		} else if (f <= 0) { // negativa ó 0
			return -1;
		}
		// devolver error
		return -1000;
	}

	/**
	 * Calcular el potencial interno para n = 2
	 * 
	 * @param peso1
	 * @param peso2
	 * @param x1
	 * @param x2
	 * @return
	 */
	float calcularPotencialInterno(float peso1, float peso2, float x1, float x2, float umbral) {
		return peso1 * x1 + peso2 * x2 + umbral;
	}

	void calcularRedNeuronal() {
		// Mas adelante habra que calcular n, que sera entrada - 1
		int n = 2;

		// Inicio aleaotrio de los pesos y el umbral
		Random rand = new Random();
		float peso1 = rand.nextFloat() * (1 - (-1)) + (-1);
		float peso2 = rand.nextFloat() * (1 - (-1)) + (-1);
		float umbral = rand.nextFloat() * (1 - (-1)) + (-1);

		float razonAprendizaje = 1;

		// El numero de muestras, en el caso de AND es 4
		float sMuestras = 4;

		float valorObtenido;
		float valorDeseado;
		
		// Error
		int falsosPositivos = 0;
		int falsosNegativos = 0;
		
		// Mas adelante las iteraciones maximas sera un parametro
		int itMax = 40;
		
		// el patron actual
		int k = 0;

		System.out.println("Iniciando la red neuronal perceptrón simple");
		System.out.println("El w1 inicial vale: " + peso1 + "\nEl w2 inicial vale: " + peso2 +
				"\numbral: " + umbral);
		System.out.println("========================================");

		for (int i = 0; i <= itMax; i++) { // máximas iteraciones e iteración actual
			System.out.println("\nVamos por la iteración: " + i + "/" + itMax);

			// Comprobamos si y=d(x)
			valorObtenido = funcionSigno(calcularPotencialInterno(peso1, peso2, datos[k][0],
					datos[k][1], umbral));
			valorDeseado = datos[k][2];

			if (valorDeseado != valorObtenido) { // y!=d(x)		
				// Calculamos Delta de los pesos y del umbral
				peso1 = peso1 + (razonAprendizaje * datos[k][2] * datos[k][0]);
				peso2 = peso2 + (razonAprendizaje * datos[k][2] * datos[k][1]);
				umbral = umbral + (razonAprendizaje * datos[k][2]);		
				
				k = 0;	// Si está mal clasificado volvemos a k=0
				System.out.println("Nuevos pesos-> \nw1: " + peso1 + "\nw2 " + peso2 + "\numbral: " + umbral);
				
				calcularErrorEjercicio1(sMuestras, peso1, peso2, umbral);
				
				
			} else if (valorDeseado - valorDeseado == 0) { // y == d(x)
				System.out.println("La k" + k + " esta bien clasificada");
				System.out.println("-----------------------------------");
				k = k + 1; // incrementamos las muestras bien clasificadas
				
				// Comprobamos si ya están bien clasificadas todas las muestras
				if (k == sMuestras) {
					imprimirResultado(peso1,peso2,umbral);
					return;
				}
			}
		}
	}

	/**
	 * Calcula el error cometido por la red cada vez que se modifican los pesos
	 * @param sMuestras
	 * @param peso1
	 * @param peso2
	 * @param umbral
	 */
	private void calcularErrorEjercicio1(float sMuestras, float peso1, float peso2,float umbral) {
		float error = 0;
		float valorObtenido = 0;
		float valorDeseado = 0;
		int falsosPositivos = 0;
		int falsosNegativos = 0;
		
		for ( int j = 0; j < sMuestras; j++){
			valorObtenido = funcionSigno(calcularPotencialInterno(peso1, peso2, datos[j][0],
					datos[j][1], umbral));
			valorDeseado = datos[j][2];
			if ( valorObtenido == 1 && valorDeseado == -1){
				falsosPositivos++;
			}
			if ( valorObtenido == -1 && valorDeseado == 1 ){
				falsosNegativos++;
			}
			error = error + (valorObtenido - valorDeseado);
		}
		error = (float) (error*(1/(2*sMuestras)));
		System.out.println("Error cometido por la red: " + error + " \nfalsos positivos: " 
		+ falsosPositivos + " \nfalsos negativos: " + falsosNegativos);
		
	}

	/**
	 * Imprime los resultados para una puerta lógica
	 * @param peso1
	 * @param peso2
	 * @param umbral
	 */
	private void imprimirResultado(float peso1, float peso2, float umbral) {
		// TODO Auto-generated method stub
		System.out.println("========================================");
		System.out.println("Por ejemplo para 0,0 daría "
				+ funcionSigno(calcularPotencialInterno(peso1, peso2, -1, -1, umbral)));
		System.out.println("Por ejemplo para 0,1 daría "
				+ funcionSigno(calcularPotencialInterno(peso1, peso2, -1, 1, umbral)));
		System.out.println("Por ejemplo para 1,0 daría "
				+ funcionSigno(calcularPotencialInterno(peso1, peso2, 1, -1, umbral)));
		System.out.println("Por ejemplo para 1,1 daría "
				+ funcionSigno(calcularPotencialInterno(peso1, peso2, 1, 1, umbral)));
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		// TODO code application logic here
		perceptronSimple red = new perceptronSimple();
		red.calcularRedNeuronal();

	}
}