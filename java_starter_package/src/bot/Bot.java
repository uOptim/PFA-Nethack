package bot;

import java.io.IOException;

public class Bot {
	
	Parser myParser;
	int dungeonLevel;
	Map map;
	
	public Bot(){
		dungeonLevel = 0;
		map = null;
		myParser = new Parser();
	}

	public void treatInformation(Information i) {
		switch (i.getVariable()){
		case DUNGEON_LEVEL: dungeonLevel = (Integer)i.getValue(); break;
		case MAP: map = (Map)i.getValue(); break;
		}
	}
	
	public void nextTurn(){
		try{
			myParser.parseNextTurn(this);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("dungeon_level : " + dungeonLevel + "\n");
		if (map != null)
			sb.append(map.toString() + "\n");
		return sb.toString();
	}
}
