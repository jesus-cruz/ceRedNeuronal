package redNeuronal1;

import java.util.Random;

/**
 *
 * @author jesus-cruz
 */
public class perceptronSimple {
/*
 * Perceptron simple    (-1,-1) -1
 *                      (-1,+1) -1
 *                      (+1,-1) -1
 *                      (+1,+1) +1
 */
	// Datos para probar, más adelante vendrán de un archivo
    float[][] datosAnd= {	{-1f,-1f,-1f},
    					{-1f, 1f,-1f},
    					{ 1f,-1f,-1f},
    					{ 1f, 1f ,1f}};
    
    float[][] datosNand= {{-1,-1,1},{-1,1,1},{1,-1,1},{1,1,-1}};
    float[][] datos= {{-1,-1,-1},{-1,1,1},{1,-1,1},{1,1,1}};
    
    // La función signo
    int funcionSigno (float f){
    	// Positiva
        if ( f > 0){
        	return 1;
        } else if ( f <= 0 ){	// negativa o 0
        	return -1;
        }
        // Si no devolvemos 0
        return -1;
    }
    
    boolean patronMalClasificado(int itMax,int itActual, float peso1, float peso2, int n){
        // Comprobariamos que todos están bien clasificados
        // TO-DO
        // Si hemos pasado el numero maximo de iteraciones salimos
        if ( itActual > itMax){
            return false;
        }
        return true;
    }
    
    /**
     * Calcular el potencial interno para n = 2
     * @param peso1
     * @param peso2
     * @param x1
     * @param x2
     * @return 
     */
    float calcularPotencialInterno(float peso1, float peso2, float x1, float x2,float umbral){
        return peso1*x1 + peso2*x2 + umbral;
    }
    
    void calcularRedNeuronal(){
        // Mas adelante habra que calcular n, que sera entrada - 1 
        int n = 2;
        
        // Inicio aleaotrio de los pesos y el umbral
        Random rand = new Random();
        float peso1 = rand.nextFloat() * (1 - (-1)) + (-1);       
        float peso2 = rand.nextFloat() * (1 - (-1)) + (-1);  
        float umbral = rand.nextFloat() * (1 - (-1)) + (-1);  
        
        float razonAprendizaje = 1;
        
        // El numero de muestras, en el caso de AND es 4
        int sMuestras = 4;
        
        // 
        float valorObtenido = 0;
        float valorDeseado = 1;
        
        // Mas adelante las iteraciones maximas sera un parametro
        int itMax = 5;
       
        int contadorBienClasificadas = 0;
        
        peso1 = 1;
        peso2 = -1;
        umbral = 1;
        System.out.println("El w1 inicial vale: " + peso1 + "\nEl w2 inicial vale: " + peso2 +
                "\nEl umbral inidical vale: " + umbral);
        System.out.println("====================================");
        int itActual = 0;
        int k = 0;
        while(patronMalClasificado(itMax, itActual, peso1, peso2, n)) {  // Mientras patron mal clasificado
            itActual++;
            for ( int i = 0; i < sMuestras; i++){      // Para t= 1 hasta s
            System.out.println("Vamos por el dato " + k);
            valorObtenido = funcionSigno(calcularPotencialInterno(peso1, peso2, datos[k][0], datos[k][1], umbral));
            valorDeseado = datos[k][2];
            if ( valorDeseado != valorObtenido ) {  // Si kt mal clasificado
            	
                // Calculamos Delta de los pesos y del umbral
                peso1 = peso1 + ( razonAprendizaje * datos[k][2] * datos[k][0] );		// razon aprendizaje * salida esperada * coordenada
                peso2 = peso2 + ( razonAprendizaje * datos[k][2] * datos[k][1] );
                umbral = umbral + ( razonAprendizaje * datos[k][2] );
                
                System.out.println("El w1 vale: " + peso1 + "\nEl w2 vale: " + peso2 +
                "\nEl umbral vale: " + umbral);   
                
            } else if ( valorDeseado - valorDeseado == 0){
                System.out.println("La k" + k + " esta bien clasificada");
                contadorBienClasificadas++;
                k++;
                if ( k == 4 ){
                	System.out.println("Por ejemplo para 0,0 daría " + 
                    		funcionSigno(calcularPotencialInterno(peso1, peso2, -1, -1, umbral)));
                    System.out.println("Por ejemplo para 0,1 daría " + 
                    		funcionSigno(calcularPotencialInterno(peso1, peso2, -1, 1, umbral)));
                    System.out.println("Por ejemplo para 1,0 daría " + 
                    		funcionSigno(calcularPotencialInterno(peso1, peso2, 1, -1, umbral)));
                    System.out.println("Por ejemplo para 1,1 daría " + 
                    		funcionSigno(calcularPotencialInterno(peso1, peso2, 1, 1, umbral)));
                    return;
                }
                
                if ( contadorBienClasificadas == sMuestras ){
                    System.out.println("s bien clasificadas");
                    break;
                }
                
            }
            }                       
        }  
        System.out.println("Resultado de la red neuronal");
        System.out.println("w1 vale: " + peso1 + " \nw2 vale: " + peso2 +
                " \nel umbral vale: " + umbral );
        
        System.out.println("Por ejemplo para 0,0 daría " + 
        		funcionSigno(calcularPotencialInterno(peso1, peso2, -1, -1, umbral)));
        System.out.println("Por ejemplo para 0,1 daría " + 
        		funcionSigno(calcularPotencialInterno(peso1, peso2, -1, 1, umbral)));
        System.out.println("Por ejemplo para 1,0 daría " + 
        		funcionSigno(calcularPotencialInterno(peso1, peso2, 1, -1, umbral)));
        System.out.println("Por ejemplo para 1,1 daría " + 
        		funcionSigno(calcularPotencialInterno(peso1, peso2, 1, 1, umbral)));
    }
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        perceptronSimple red = new perceptronSimple();
        red.calcularRedNeuronal();
        
    }
}