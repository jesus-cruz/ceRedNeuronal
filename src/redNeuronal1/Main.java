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
		
		lectorCsv.cargarArchivo("sources/appendicitisModificado.dat");
		float[][] datos = { { -1, -1, -1 }, 
				{ -1, 1, -1 }, 
				{ 1, -1, -1 }, 
				{ 1, 1, 1 } };
		
		//red.calcularRedNeuronal(40,lectorCsv.cargarDatos());
		red.calcularRedNeuronal(40, datos);
	}
}
