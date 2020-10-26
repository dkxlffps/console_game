package Constant;

public class constant {
	
	// DATA FOLDER PATH
	public String DATA_FOLDER_PATH = "C:\\gitClone\\console_game\\src\\data";
	public String USER_ACCOUNT_PATH ="\\userAccount\\%s.xml";
	public String DEFAULT_CHARACTER_PATH = "\\character\\%s.xml";
	public String MESSAGE_PATH = "\\text\\message.txt";
	public String NPCNAME_PATH = "\\npc\\npcName.txt";
	public String GENERATION_PATH = "\\text\\hunter.txt";
	
	// SEPARETOR
	public String TEXT_MESSAGE_SEPARETOR = "-";
	public String DATA_KEY_VALUE_SEPARETOR = ":";
	public String ACCOUNT_SEPARETOR = "/";
	public String NEXT_LINE_IGNORE_SEPARETOR = "*";
	public String NEXT_LINE_MASTER_KEY = "_";
	public String NPC_NAME_KEY_SEPARATOR = "#";
	
	// DATA RESOURCE
	public int MESSAGE_LINE_MAX_LENGTH = 30;
	public int MESSAGE_LINE_CLEAR = 50;
	public int MESSAGE_THREAD_PERSEC = 30;
	
	// DATA TYPE
	public String DATA_TYPE_TEXT = "text";
	public String DATA_TYPE_SINARIO = "sinario";
	
	// DATA Key Value
	public String[] ACCOUNT_KEYS = {"id" , "pwd" ,"sequence"};
	
	// TEXT FOMMAT
	public String NPC_NAME_FORMAT = "[%S]";
	
}
