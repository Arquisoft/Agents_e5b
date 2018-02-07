package uo.asw.parser.reader;



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

public class CSVKindsReader {
	
	private static HashMap<String,Integer> map = new HashMap<String, Integer>();
	private static final String rutaCsvFile = "src/main/resources/tipoagente.csv"; 

	public static void readCSV() {
		BufferedReader br = null;
		String line = "";
		//separador
		String cvsSplitBy = ",";
		
		try {
			br = new BufferedReader(new FileReader(rutaCsvFile));
			
			while ((line = br.readLine()) != null) {
				String[] value = line.split(cvsSplitBy);
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

		
	public static int getKindCodeByKind(String kind){
		readCSV();
		return map.get(kind);
	}
	
	public static Set<String> getKinds(){
		readCSV();
		return map.keySet();
	}
	
	
}
