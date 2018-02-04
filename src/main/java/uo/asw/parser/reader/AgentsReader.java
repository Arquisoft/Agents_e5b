package uo.asw.parser.reader;

import java.io.IOException;
import java.util.Map;

public interface AgentsReader {

	Map<String, Integer> readKinds(String filePath) throws IOException;

}
