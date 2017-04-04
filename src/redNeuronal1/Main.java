package redNeuronal1;

public class Main {
	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		// TODO code application logic here
		PerceptronSimpleMejorado red = new PerceptronSimpleMejorado();
		CSVReaderDataSet lectorCsv = new CSVReaderDataSet();
		
		// Los posibles datos a cargar
		lectorCsv.cargarArchivo("sources/appendicitisModificado.dat");
		
		double[][] datosAndNegativa = { { -1, -1, -1 }, 
							{ -1, 1, -1 }, 
							{ 1, -1, -1 }, 
							{ 1, 1, 1 } };
		double[][] datosAnd = { { 0, 0, 0 }, 
							{ 0, 1, 0 }, 
							{ 1, 0, 0 }, 
							{ 1, 1, 1 }};
		double[][] datosOr = { { -1, -1, -1 }, { -1, 1, 1 }, { 1, -1, 1 }, { 1, 1, 1 } };
		
		red.calcularRedNeuronal(3000,lectorCsv.cargarDatos(),0.1509433962264151  ,0);
		//red.calcularRedNeuronal(25, datos, (float) 0.1 ,0);
		//red.calcularRedNeuronal(25, datosOr, 0, -1);
	}
}
