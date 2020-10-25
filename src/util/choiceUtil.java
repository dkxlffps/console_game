package util;

public class choiceUtil {
	
	private String choice[];
	
	public String[] getChoiceKeys(int length) {
		choice = new String[length];
		for(int i=0;i<length;i++) {choice[i]=(i+1)+"";}
		return choice;
	}
}
