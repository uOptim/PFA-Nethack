package bot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Parser{
	
	private BufferedReader input;
	private int mapHeight = -1;
	private int mapWidth = -1;
	
	public Parser(){
		input = new BufferedReader(new InputStreamReader(System.in));
	}
	
	public void parseNextTurn(Bot b) throws IOException{
		// Verify start of message
		if (!input.readLine().equals(Protocole.START_TOKEN))
			throw new RuntimeException("Invalid start");
		String line;
		while (!(line = input.readLine()).equals(Protocole.END_TOKEN)){
			// Multi-line message
			Information i = null;
			if (line.startsWith(Protocole.START_TOKEN))
				i = parseMultiLineVar(line);
			// Mono-line message
			else
				i = parseMonoLineVar(line);
			if (i != null)
				b.treatInformation(i);
		}
	}
	
	private Information parseMultiLineVar(String line) throws IOException{
		Variable v = Variable.tokenToVariables(line.split(" ")[1]);
		Object o = null;
		switch (v){
		case MAP :
				o = parseMap();
				break;
		default ://TODO should throw Exception
		}
		return new Information(v,o);
	}

	private Map parseMap() throws IOException{
		String line;
		char[][] map = new char[mapHeight][mapWidth];
		int lineNumber = 0;
		while (!(line = input.readLine()).equals(Protocole.END_TOKEN +
												 " " +
												 Variable.MAP.getToken())){
			for (int colNumber = 0; colNumber < mapWidth; colNumber++){
				if (colNumber >= line.length())
					map[lineNumber][colNumber] = ' ';//TODO notation en dur à éviter
				else
					map[lineNumber][colNumber] = line.charAt(colNumber);
			}
			lineNumber++;
		}
		return new Map(map);
	}

	private Information parseMonoLineVar(String line){
		String[] splitedLine = line.split(" ");
		Variable v = Variable.tokenToVariables(splitedLine[0]);
		Object o = null;
		switch (v){
		case MAP_HEIGHT: mapHeight = Integer.parseInt(splitedLine[1]);
		case MAP_WIDTH: mapWidth = Integer.parseInt(splitedLine[1]);
		case DUNGEON_LEVEL:
			o = Integer.parseInt(splitedLine[1]);
			break;
		default :
		}
		if (o == null)
			return null;
		return new Information(v,o);
	}
}