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
		
		//red.calcularRedNeuronal(10,lectorCsv.cargarDatos(), 0.1981132075471698 ,0);
		//red.calcularRedNeuronal(25, datos, (float) 0.1 ,0);
		red.calcularRedNeuronal(25, datosAnd, 0, 0);
	}
}
