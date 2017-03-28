package redNeuronal1;

import java.util.Random;

/**
 *
 * @author jesus-cruz
 */
public class PerceptronSimpleMejorado {
	

	
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
	
	int funcionSigno2(float f) {
		// Positiva
		if (f >= 0) {
			return 1;
		} else if (f < 0) { // negativa ó 0
			return 0;
		}
		// devolver error
		return -1000;
	}

	/**
	 * Calcular el potencial interno para k
	 * 
	 * @param peso1
	 * @param peso2
	 * @param x1
	 * @param x2
	 * @return
	 */
	float calcularPotencialInterno(float[][] datos, float[] pesos, float umbral, int k) {
		float potencialInterno = 0;
		for ( int i = 0 ; i < pesos.length; i++){
			potencialInterno = potencialInterno + datos[k][i] * pesos[i];
		}
		//return peso1 * x1 + peso2 * x2 + umbral;
		return potencialInterno + umbral;
	}

	boolean calcularRedNeuronal(int itMax, float[][] datos) {
		// Inicio aleaotrio de los pesos y el umbral
		Random rand = new Random();
		float[] pesos = new float[datos[0].length-1];

		float umbral = rand.nextFloat() * (1 - (-1)) + (-1);
		for ( int i = 0 ; i < pesos.length - 1 ; i++){
			pesos[i]= rand.nextFloat() * (1 - (-1)) + (-1);
		}
		/*float umbral = rand.nextFloat() * (1 - (0)) + (0);
		for ( int i = 0 ; i < pesos.length - 1 ; i++){
			pesos[i]= rand.nextFloat() * (1 - (0)) + (0);
		}*/
			
		
		// 
		float razonAprendizaje = 1;

		// El numero de muestras, en el caso de AND es 4
		float sMuestras = datos.length;

		float valorObtenido,valorDeseado;
			
		// el patron actual
		int k = 0;

		System.out.println("\nIniciando la red neuronal perceptrón simple");
		// TO-DO mostrar los pesos aleatorios iniciales
		System.out.println("===========================================");

		for (int i = 0; i <= itMax; i++) { // máximas iteraciones e iteración actual
			System.out.println("\nVamos por la iteración: " + i + "/" + itMax);

			// Comprobamos si y=d(x)
			valorObtenido = funcionSigno(calcularPotencialInterno(datos, pesos, umbral,k));
			valorDeseado = datos[k][pesos.length];

			if (valorDeseado != valorObtenido) { // y!=d(x)		
				// Calculamos Delta de los pesos y del umbral
				for ( int j = 0 ; j < pesos.length; j++){
					pesos[j] = pesos[j] + (razonAprendizaje * datos[k][pesos.length] * datos[k][j]);
					System.out.println("El peso " + j + " vale " + pesos[j]);
				}
			
				umbral = umbral + (razonAprendizaje * datos[k][pesos.length]);		
				
				k = 0;	// Si está mal clasificado volvemos a k=0
				
				// TO-DO mostramos los nuevos pesos
				// Calculamos el error
				calcularErrorEjercicio1(sMuestras, umbral,datos,pesos,k);				
				
			} else if (valorDeseado - valorDeseado == 0) { // y == d(x)
				System.out.println("La k" + k + " esta bien clasificada");
				System.out.println("-----------------------------------");
				k = k + 1; // incrementamos las muestras bien clasificadas
				
				// Comprobamos si ya están bien clasificadas todas las muestras
				if (k == sMuestras) {
					//imprimirResultado(peso1,peso2,umbral);
					 calcularErrorEjercicio1(sMuestras, umbral, datos, pesos, k-1) ;
					 for ( int l = 0 ; l < sMuestras; l++){
						 System.out.println(funcionSigno(calcularPotencialInterno(datos,pesos,umbral,l)));
					 }
					return true;
				}
			}
		}
		// hemos pasado el número máximo de iteraciones
		return false;
	}

	/**
	 * Calcula el error cometido por la red cada vez que se modifican los pesos
	 * @param sMuestras
	 * @param peso1
	 * @param peso2
	 * @param umbral
	 */
	private float calcularErrorEjercicio1(float sMuestras,float umbral, float[][] datos,
			float[] pesos, int k) {
		float error = 0, valorObtenido = 0,valorDeseado = 0;
		int falsosPositivos = 0,falsosNegativos = 0;
		
		// Calculamos el sumatorio de los kt y los falsos positivos y negativos
		for ( int j = 0; j < sMuestras; j++){
			valorObtenido = funcionSigno(calcularPotencialInterno(datos,pesos, umbral,k));
			valorDeseado = datos[j][pesos.length];
			if ( valorObtenido == 1 && valorDeseado == -1){
				falsosPositivos++;
			}
			if ( valorObtenido == -1 && valorDeseado == 1 ){
				falsosNegativos++;
			}
			
			// Error(kt) = |dt-yt|
			error = error + Math.abs(valorDeseado - valorObtenido);
		}
		
		// Error=(1/2s)suma(Error(kt),t,1,s)
		error = (float) (error*(1/(2*sMuestras)));
		
		System.out.println("Error cometido por la red: " + error + " \nfalsos positivos: " 
		+ falsosPositivos + " \nfalsos negativos: " + falsosNegativos);
		return error;
	}

}