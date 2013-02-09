package bot;

import java.io.IOException;
import java.net.UnknownHostException;

import util.InvalidMessageException;
import util.Logger;
import util.ScoredList;
import util.Scoring;

public class Bot {
	
	private InputOutputUnit myParser;
	private int dungeonLevel;
	public Map map;
	private int turn;
	private int nbMoves;
	private int nbSearches;
	private int nbOpen;
	private Square expectedLocation;
	
	public Bot(){
		dungeonLevel = 0;
		map = new Map();
		myParser = new InputOutputUnit();
		expectedLocation = null;
	}
	
	public Bot(String unixSocketName)
			throws UnknownHostException, IOException{
		this();		
		myParser = new InputOutputUnit(unixSocketName);
	}

	public Bot(String hostname, int port)
			throws UnknownHostException, IOException{
		this();
		myParser = new InputOutputUnit(hostname, port);
	}

	public void treatInformation(Information i) {
		switch (i.getVariable()){
		case DUNGEON_LEVEL: dungeonLevel = (Integer)i.getValue(); break;
		case MAP: map = (Map)i.getValue(); break;
		}
	}

	public void start(){
		try{
			while(true){
				try{
					Logger.println("READING FROM SOCKET");
					nextTurn();
					Logger.println("DOING TURN");
					doTurn();
				}catch(UnknownPositionException e){
					System.out.println("The player location has not been found, Skipping turn");
				}
			}

		}catch(IOException e){
			String message = "Connection with the server has been lost";
			System.out.println(message);
		}catch(InvalidMessageException e){
			System.out.println(e.getMessage());
		}
	}
	
	public void doTurn(){
		if (expectedLocation != null &&
		    map.actualSquare() != expectedLocation)
			expectedLocation.setType(SquareType.HORIZONTAL_WALL, map);
		Logger.println("Starting Turn : " + turn);
		Logger.println(nbMoves + " moves until now");
		Logger.println(nbSearches + " search until now");
		Logger.println(nbOpen + " open until now");		
		map.actualSquare().addVisit(map);
		map.updateScores();
		Logger.println(map.toString());
		Logger.println(map.searchMapAsString());
		/*try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
		bestAction();
		turn++;
	}
	
	private ScoredList<Action> getPossibleActions(){
		ScoredList<Action> l = new ScoredList<Action>();
		// Search is always available
		double searchScore = map.actualSquare().getSearchScore();
		l.add(new Action(ActionType.SEARCH, null, searchScore));
		for (Direction dir : Direction.values()){
			Action toAdd = null;
			Square dest = map.getDest(dir);
			if (dest == null)
				continue;
			if (map.isAllowedMove(dir))
				toAdd = new Action(ActionType.MOVE,
							       dir,
							       Scoring.afterMoveScore(dest.getScore()));
			if (map.isAllowedOpen(dir)){
				toAdd = new Action(ActionType.OPEN,
							       dir,
							       dest.getScore());
			}
			if (toAdd != null)
				l.add(toAdd);
		}
		Logger.println("Nb valid choices : " + l.nbElements());
		return l;
	}
	
	public void bestAction(){
		ScoredList<Action> l = getPossibleActions();
		Action choice = l.getBestItem();
		Logger.println("Choice : " + choice);
		applyAction(choice);
	}
	
	public void randomAction(){
		ScoredList<Action> l = getPossibleActions();
		Action choice = l.getRandomItem();
		Logger.println("Choice : " + choice);
		applyAction(choice);
	}
	
	public void applyAction(Action a){
		switch(a.getType()){
		case SEARCH:
			for (Square neighbor : map.actualSquare().getNeighbors())
				neighbor.addSearch(map);
			myParser.broadcastSearch();
			nbSearches++;
			return;
		case OPEN:
			map.getDest(a.getDirection()).addOpenTry();
			myParser.broadcastOpeningDoor(a.getDirection());
			nbOpen++;
			return;
		case MOVE:
			myParser.broadcastMove(a.getDirection());
			expectedLocation = map.getDest(a.getDirection());
			nbMoves++;
			return;
		}
	}
	
	public void nextTurn() throws IOException, UnknownPositionException, InvalidMessageException{
		myParser.parseNextTurn(this);
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
