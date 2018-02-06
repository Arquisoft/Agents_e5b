package uo.asw.parser.reader;



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class CSVKindsReader {
	
	static HashMap<String,Integer> map = new HashMap<String, Integer>();
	
//	public static void main(String[] args) {
//		CSVKindsReader2();
//		leerMap("Entity");
//	}
	
	

	public static void CSVKindsReader2() {
		//ruta del fichero csv
		String csvFile = "tipoagente.csv";
		BufferedReader br = null;
		String line = "";
		//separa por comas
		String cvsSplitBy = ",";
		

		
		

		try {
			br = new BufferedReader(new FileReader(csvFile));
			
			while ((line = br.readLine()) != null) {

				String[] value = line.split(cvsSplitBy);

				//System.out.println(" [value1= " + value[0] + " , value2="
				//		+ value[1] + "]");
				//System.out.println(value[0]);
				map.put(value[1],Integer.valueOf(value[0]));

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

		
	public static void leerMap(String key){
		System.out.println(map.get(key));
	}
	
	
	
	
}
