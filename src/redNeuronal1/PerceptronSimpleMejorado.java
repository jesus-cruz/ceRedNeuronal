package redNeuronal1;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Random;

import org.nevec.rjm.BigDecimalMath;

/**
 *
 * @author jesus-cruz
 */
public class PerceptronSimpleMejorado {
	

	
	// La función signo
	int funcionSigno2(double f) {
		// Positiva
		if (f > 0) {
			return 1;
		} else if (f <= 0) { // negativa ó 0
			return -1;
		}
		// devolver error
		return -1000;
	}
	
	int funcionSigno(double f) {
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
	double calcularPotencialInterno(double[][] datos, double[] pesos, double umbral, int k) {
		double potencialInterno = 0;
		for ( int i = 0 ; i < pesos.length; i++){
			potencialInterno = potencialInterno + datos[k][i] * pesos[i];
		}
		//return peso1 * x1 + peso2 * x2 + umbral;
		return potencialInterno + umbral;
	}
	
	double calcularRazonAprendizaje(double razon,int t, int itMax ){
		/* Incrementar la precisión implica devolver una razon mas precisa y habría que usar BigDecimalMath
		también en el cálculo del incremento de los pesos */
		/*if ( razon == 0){
			return 0;
		}
		BigDecimal alpha = new BigDecimal(0.1);
		BigDecimal tBigNeg = new BigDecimal(-t);
		BigDecimal c = new BigDecimal(itMax/2);
		
		BigDecimal denominador = BigDecimalMath.pow(alpha, ( BigDecimalMath.addRound(tBigNeg, c )));
		
		denominador = BigDecimalMath.add(denominador, new BigInteger("1"));
		
		BigDecimal razonDecimal = BigDecimalMath.divideRound(new BigInteger("1"), denominador);
		
		razon = razonDecimal.doubleValue();*/
		
		double alpha = 0.28;
		double c = itMax/2;
		razon = 1 / ( 1 + (Math.exp(Math.pow(alpha, (-t + c )) )));
		
		return razon;
	}

	boolean calcularRedNeuronal(int itMax, double[][] datos, double errorAceptable, int tipo) {
		// Inicio aleaotrio de los pesos y el umbral
		Random rand = new Random();
		double[] pesos = new double[datos[0].length-1];
		double umbral = 0;

		// Pesos aleatorios iniciales
		if ( tipo == -1){			// -1,1
			umbral = rand.nextDouble() * (1 - (-1)) + (-1);
			for ( int i = 0 ; i < pesos.length - 1 ; i++){
				pesos[i]= rand.nextDouble() * (1 - (-1)) + (-1);
			}
		} else if ( tipo == 0) {	//  0,1
			umbral = rand.nextDouble() * (1 - (0)) + (0);
			for ( int i = 0 ; i < pesos.length - 1 ; i++){
				pesos[i]= rand.nextDouble() * (1 - (0)) + (0);
			}
		}
				
		// De casi 1 hasta una asíntota...
		double razonAprendizaje = 1;

		// El numero de muestras, en el caso de AND es 4
		double sMuestras = datos.length;

		double valorObtenido,valorDeseado;
			
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
						
				
				k = 0;	// Si está mal clasificado volvemos a k=0
				
				// TO-DO mostramos los nuevos pesos
				// Calculamos el error
				calcularErrorEjercicio1(sMuestras, umbral,datos,pesos,k,tipo);				
				
			} else if (valorDeseado - valorDeseado == 0) { // y == d(x)
				System.out.println("La k" + k + " esta bien clasificada");
				System.out.println("-----------------------------------");
				k = k + 1; // incrementamos las muestras bien clasificadas
				
				// Comprobamos si ya están bien clasificadas todas las muestras
				if (k == sMuestras) {
					//imprimirResultado(peso1,peso2,umbral);
					 calcularErrorEjercicio1(sMuestras, umbral, datos, pesos, k-1,tipo) ;
					 for ( int l = 0 ; l < sMuestras; l++){
						 System.out.println(funcionSigno(calcularPotencialInterno(datos,pesos,umbral,l)));
					 }
					return true;
				}
			}
			// Otro criterio de parada, el error aceptable
			if ( calcularErrorEjercicio1(sMuestras, umbral, datos, pesos, k,tipo) < errorAceptable){
				System.out.println("Hemos llegado al error aceptable");
				for ( int l = 0 ; l < sMuestras; l++){
					System.out.println(funcionSigno(calcularPotencialInterno(datos,pesos,umbral,l)));
				}
				return true;
			}
			razonAprendizaje = calcularRazonAprendizaje(razonAprendizaje, i, itMax);
			System.out.println("La nueva gamma vale " + razonAprendizaje);
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
	private double calcularErrorEjercicio1(double sMuestras,double umbral, double[][] datos,
			double[] pesos, int k,int tipo) {
		double error = 0, valorObtenido = 0,valorDeseado = 0;
		int falsosPositivos = 0,falsosNegativos = 0;
		
		if ( tipo == -1){
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
			error = (double) (error*(1/(2*sMuestras)));
		}  else if ( tipo == 0 ){
			// Calculamos el sumatorio de los kt y los falsos positivos y negativos
			for ( int j = 0; j < sMuestras; j++){
				valorObtenido = funcionSigno(calcularPotencialInterno(datos,pesos, umbral,k));
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


		System.out.println("Error cometido por la red: " + error + " \nfalsos positivos: " 
		+ falsosPositivos + " \nfalsos negativos: " + falsosNegativos);
		return error;
	}

}