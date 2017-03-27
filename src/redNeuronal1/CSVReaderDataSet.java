package redNeuronal1;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;

public class CSVReaderDataSet {

	String rutaArchivo = "0";

	public void cargarArchivo(String ruta) {
		rutaArchivo = ruta;
	}

	public float[][] cargarDatos(){
		// Deberiamos leer las variables para saber su número
		float[][] datos = new float[106][8];

		CSVReader reader = null;
		try {
			reader = new CSVReader(new FileReader(rutaArchivo));
			
			String[] line;
			int fila=0;
			while ((line = reader.readNext()) != null) {
				for ( int i = 0; i < 8 ; i++){
					datos[fila][i]= Float.parseFloat(line[i]);

				}
				fila++;
			}  

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return datos;

	}



}