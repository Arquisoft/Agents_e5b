package uo.asw.parser.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelKindsReader implements AgentsReader {
	
	//ruta -> src/main/resources/kinds.xlsx
	@Override
	public Map<String, Integer> readKinds(String filePath) throws IOException {
		Map<String, Integer> tipos=new HashMap<String, Integer>();
		File file = new File(filePath);
		FileInputStream inputStream = new FileInputStream(file);

		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet sheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = sheet.iterator();
		//iterator.next(); // Para saltar la primera fila de titulos

		while (iterator.hasNext()) {
			Row nextRow = iterator.next();
			Iterator<Cell> cellIterator = nextRow.cellIterator();
			
			String tipo="";
			int tipoCode=0;
			
			while (cellIterator.hasNext()) {
				Cell nextCell = cellIterator.next();
				String cellValue=nextCell.getStringCellValue();
				
				String[] valuesSeparadosPorComa=cellValue.split(",");
				
				tipoCode=Integer.parseInt(valuesSeparadosPorComa[0]);
				tipo=valuesSeparadosPorComa[1];
				
			}
			
			tipos.put(tipo, tipoCode);
			
		 }

		workbook.close();
		inputStream.close();
		
		return tipos;
	}

}
