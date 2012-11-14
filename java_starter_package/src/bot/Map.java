package bot;

public class Map {

	private Square[][] content;

	public Map(char[][] map) {
		content = new Square[map.length][map[0].length];
		for (int line = 0; line < map.length ; line++)
			for (int column = 0; column < map[line].length; column++)
				content[line][column] = new Square(map[line][column]);
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		for (int line = 0; line < content.length ; line++){
			for (int column = 0; column < content[line].length; column++)
				sb.append(content[line][column].toString());
			sb.append('\n');
		}
		return sb.toString();
	}

}
