package redNeuronal1;

import java.util.Random;

/**
 *
 * @author jesus-cruz
 */
public class PerceptronSimpleMejorado {
		

	/**
	 * La función signo funciona con -1,1 y con 0,1
	 * @param f
	 * @param tipo
	 * @return
	 */
	int funcionSigno(double f,int tipo) {
		if ( tipo == 0){
			// Positiva
			if (f >= 0) {		// positiva ó 0
				return 1;
			} else if (f < 0) { // negativa
				return 0;
			}
		} else if ( tipo == -1){
			if (f > 0) {		// positiva
				return 1;
			} else if (f <= 0) { // negativa ó 0
				return -1;
			}
		}	
		// devolver error
		return -1000;
	}

	/**
	 * Calcula el potencial interno de los datos,con los pesos y un umbral
	 * @param datos	la matriz de datos con las entradas y salidas
	 * @param pesos	los pesos asignados a cada coordenada
	 * @param umbral
	 * @param k	el tiempo actual
	 * @return
	 */
	double calcularPotencialInterno(double[][] datos, double[] pesos, double umbral, int k) {
		double potencialInterno = 0;
		for ( int i = 0 ; i < pesos.length; i++){
			potencialInterno = potencialInterno + datos[k][i] * pesos[i];
		}
		return potencialInterno + umbral;
	}
	
	/**
	 * Calcula la razón de aprendizaje 
	 * @param razon
	 * @param t
	 * @param itMax
	 * @return
	 */
	double calcularRazonAprendizaje(double razon,int t, int itMax ){		
		double alpha = 0.28;	// min 0.28 debido a la precisión de double
		double c = itMax*(8/9); // itMax/2 
		razon = 1 / ( 1 + (Math.exp(Math.pow(alpha, (-t + c )) )));	
		return razon;
	}
	
	/**
	 * Calcula el error del ejercicio1
	 * @param sMuestras
	 * @param umbral
	 * @param datos
	 * @param pesos
	 * @param k
	 * @param tipo
	 * @param i 
	 * @return
	 */
	private double calcularErrorEjercicio1(double sMuestras,double umbral, double[][] datos,
			double[] pesos, int k,int tipo, boolean imprimir) {
		double error = 0, valorObtenido = 0,valorDeseado = 0;
		int falsosPositivos = 0,falsosNegativos = 0;
		
		if ( tipo == -1){
			// Calculamos el sumatorio de los kt y los falsos positivos y negativos
			for ( int j = 0; j < sMuestras; j++){
				valorObtenido = funcionSigno(calcularPotencialInterno(datos,pesos, umbral,j),tipo);
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
			error = (double) (error*(1/(2*sMuestras)));			
		}  else if ( tipo == 0 ){
			// Calculamos el sumatorio de los kt y los falsos positivos y negativos
			for ( int j = 0; j < sMuestras; j++){
				valorObtenido = funcionSigno(calcularPotencialInterno(datos,pesos, umbral,j),tipo);
				valorDeseado = datos[j][pesos.length];
				if ( valorObtenido == 1 && valorDeseado == 0){
					falsosPositivos++;
				}
				if ( valorObtenido == 0 && valorDeseado == 1 ){
					falsosNegativos++;
				}

				// Error(kt) = |dt-yt|
				error = error + Math.abs(valorDeseado - valorObtenido);
			}
			// Error=(1/s)suma(Error(kt),t,1,s)
			error = (double) (error*(1/(sMuestras)));
		}
		if ( imprimir) {
			System.out.println("Error cometido por la red: " + error + " \nfalsos positivos: " 
					+ falsosPositivos + " \nfalsos negativos: " + falsosNegativos);
		}
		
		return error;
	}


	/**
	 * El método general de la red neuronal
	 * @param itMax el número máximo de iteraciones
	 * @param datos los datos a usar
	 * @param errorAceptable el error para parar
	 * @param tipo si es -1,1-> -1, si es 0,1->0
	 * @return
	 */
	boolean calcularRedNeuronal(int itMax, double[][] datos, double errorAceptable, int tipo) {
		// Inicio aleaotrio de los pesos y el umbral
		Random rand = new Random();
		double[] pesos = new double[datos[0].length-1];
		double[] pesosAntiguos = new double[datos[0].length-1];
		double umbral = 0;
				
		// De casi 1 hasta una asíntota...
		double razonAprendizaje = 1;

		// El numero de muestras, en el caso de AND es 4
		double sMuestras = datos.length;

		double valorObtenido,valorDeseado;
			
		// el patron actual
		int k = 0;

		System.out.println("\nIniciando la red neuronal perceptrón simple");
		System.out.println("===========================================");

		// Pesos aleatorios iniciales
		System.out.println("Los pesos iniciales valen:");
		if ( tipo == -1){			// -1,1
			umbral = rand.nextDouble() * (1 - (-1)) + (-1);
			for ( int i = 0 ; i < pesos.length ; i++){
				pesos[i]= pesosAntiguos[i] = rand.nextDouble() * (1 - (-1)) + (-1);
				System.out.println("El peso " + i + " vale " + pesos[i]);
			}
		} else if ( tipo == 0) {	//  0,1
			umbral = rand.nextDouble() * (1 - (0)) + (0);
			for ( int i = 0 ; i < pesos.length ; i++){
				pesos[i]= pesosAntiguos[i] = rand.nextDouble() * (1 - (0)) + (0);
				System.out.println("El peso " + i + " vale " + pesos[i]);
			}
		}

		// Empezamos a iterar hasta las iteraciones máximas
		for (int i = 0; i <= itMax; i++) { 
			System.out.println("\nVamos por la iteración: " + i + "/" + itMax);

			// Comprobamos si y=d(x)
			valorObtenido = funcionSigno(calcularPotencialInterno(datos, pesos, umbral,k),tipo);
			valorDeseado = datos[k][pesos.length];

			if (valorDeseado != valorObtenido) { // y!=d(x)		
				// Calculamos Delta de los pesos y del umbral
				if ( tipo == -1 ) {
					for ( int j = 0 ; j < pesos.length; j++){
						pesos[j] = pesos[j] + (razonAprendizaje * valorDeseado * datos[k][j]);
						System.out.println("El peso " + j + " vale " + pesos[j]);
					}
				} else if ( tipo == 0){
					for ( int j = 0 ; j < pesos.length; j++){
						pesos[j] = pesos[j] + (razonAprendizaje * ( valorDeseado -
								valorObtenido )* datos[k][j]);
						System.out.println("El peso " + j + " vale " + pesos[j]);
					}
				}
				
				if ( tipo == -1 ){
					umbral = umbral + (razonAprendizaje * valorDeseado);
				} else if ( tipo == 0 ){
					umbral = umbral + (razonAprendizaje * valorDeseado - valorObtenido);
				}
										
				k = 0;	// Si está mal clasificado volvemos a tener 0 muestras bien clasificadas
							
			} else if (valorDeseado - valorDeseado == 0) { // y == d(x)
				System.out.println("La k" + k + " esta bien clasificada");
				System.out.println("-----------------------------------\n");
				k = k + 1; // incrementamos las muestras bien clasificadas
				
				// Comprobamos si ya están bien clasificadas todas las muestras
				if (k == sMuestras) {
					 calcularErrorEjercicio1(sMuestras, umbral, datos, pesos, k-1,tipo,true) ;
					 for ( int l = 0 ; l < sMuestras; l++){
						 //System.out.println(funcionSigno(calcularPotencialInterno(datos,pesos,umbral,l),tipo));
					 }
					 System.out.println(" \nPesos iniciales");
					 for ( int i1 = 0 ; i1 < pesos.length ; i1++){
							System.out.println("El peso " + i1 + " vale " + pesosAntiguos[i1]);
					 }
					 
					 System.out.println("\nHemos clasificado todas las muestras" + "\nFIN");
					 return true;
				}
			}
			// Otro criterio de parada, el error aceptable
			if ( calcularErrorEjercicio1(sMuestras, umbral, datos, pesos, k,tipo, false) < errorAceptable){
				for ( int l = 0 ; l < sMuestras; l++){
					//System.out.println(funcionSigno(calcularPotencialInterno(datos,pesos,umbral,l),tipo));
				}
				calcularErrorEjercicio1(sMuestras, umbral,datos,pesos,k,tipo,true);	
				
				System.out.println("\nPesos iniciales");
				for ( int i1 = 0 ; i1 < pesos.length ; i1++){
					System.out.println("El peso " + i1 + " vale " + pesosAntiguos[i1]);
				}

				System.out.println("\nHemos llegado al error aceptable" + "\nFIN");
				return true;
			}
			// Calculamos el error
			calcularErrorEjercicio1(sMuestras, umbral,datos,pesos,k,tipo,true);		
			
			razonAprendizaje = calcularRazonAprendizaje(razonAprendizaje, i, itMax);
			System.out.println("La nueva gamma vale " + razonAprendizaje);
		}
		// hemos pasado el número máximo de iteraciones
		System.out.println("\nHemos sobrepasado el número máximo de iteraciones" + "\nFIN");
		return false;
	}
}

