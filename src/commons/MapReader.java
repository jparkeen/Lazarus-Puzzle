package commons;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MapReader {

	public static String WALL = "WAL";
	
	public static String SPACE = "000";
	
	public static String LAZARUS = "LAZ";

    public static String STOP = "STP";

    public static String CARDBOARD_BOX = "CBX";

    public static String WOOD_BOX = "WBX";

    public static String STONE_BOX = "SBX";

    public static String METAL_BOX = "MBX";

	public static final Set<String> ALL_BOX_SET = new HashSet<String>(Arrays.asList(MapReader.CARDBOARD_BOX,
	MapReader.STONE_BOX, MapReader.WOOD_BOX, MapReader.METAL_BOX));;

	public static String[][] readMap(String mapFileName) throws IOException {
		String[][] array = new String[Globals.MAX_NUMBER_OF_BLOCKS][Globals.MAX_NUMBER_OF_BLOCKS];
		CommonAPIs.validateFileExists(mapFileName);
		BufferedReader br = new BufferedReader(new FileReader(mapFileName));
		String line;
		int row = 0, col = 0;
		while ((line = br.readLine()) != null) {
			col = 0;
			String[] tokens = line.split(",");
			for (String value : tokens) {
				array[row][col] = value;
				col += 1;
			}
			row += 1;
		}
		br.close();
		return array;
	}

}