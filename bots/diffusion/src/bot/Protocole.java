package bot;

public final class Protocole {
	
	public static String UNIX_SOCKET_NAME = "/tmp/mmsock";
	
	public static final char PLAYER_TOKEN = '@';
	
	
	// INPUT TOKENS
	
	public static final char START_TOKEN = 'S';
	
	public static final char END_TOKEN = 'E';
	
	public static final char SEED_TOKEN = 's';
	
	public static final char MAP_SIZE_TOKEN = 'm';
	
	public static final char GLYPH_TOKEN = 'g';

	public static final char CLEAR_TOKEN = 'C';
	
	// OUTPUT TOKENS
	
	//public static String MOVE_TOKEN = "MOVE";
	
	public static final char SEARCH_TOKEN = 's';
	
	public static final char OPEN_TOKEN = 'o';
	
	public static final char FORCE_TOKEN = '\4';
	
	public static final char DOWN_TOKEN = '>';

	public static final int DEFAULT_PORT = 4242;
}
