package util;

import Constant.constant;

public class messageUtil extends Thread{
	private static constant CONSTANT = new constant();
	
	private String line = "";
	private String lineClear = "";
	
	public messageUtil() {
		for(int i = 0; i < CONSTANT.MESSAGE_LINE_MAX_LENGTH; i++) {
			line += "-";
		}
		line += "\n";
		for(int i = 0 ; i < CONSTANT.MESSAGE_LINE_CLEAR; i++) {
			lineClear += "\n";
		}
	}
	
	public String run(String type , Object data) throws Exception {
		
		switch(type) {
		case "text"
		  : {
		      String message = (String) data;
		      TextMessage(message);
		      
		  }
		  break;
		case "sinario"
		  : {
			  
			  String []nameMessage = (String[]) data;
			  SinarioMessage(nameMessage);
		  }
		}
		return type;
	}
	
	public void TextMessage(String text) throws Exception {
		System.out.println(line);
		message(text);
		System.out.println("\n\n"+line);
	}
	
	public void SinarioMessage(String [] nameMessage) throws Exception {
		System.out.println(line);
		String name = String.format(CONSTANT.NPC_NAME_FORMAT, nameMessage[0]);
		for(int i=0; i<name.length();i++) {
			Thread.sleep(CONSTANT.MESSAGE_THREAD_PERSEC);
			System.out.print(name.charAt(i));
		}
		System.out.print("\n\n");
		message(nameMessage[1]);
		System.out.println(line);
	}
	
	public void message(String text) throws Exception{
		boolean ignore_next_line = text.indexOf(CONSTANT.NEXT_LINE_IGNORE_SEPARETOR) > -1;
		
		int currentLength = 0;
		String _this ="";
		for(int i = 0 ; i < text.length(); i++) {
			Thread.sleep(CONSTANT.MESSAGE_THREAD_PERSEC);
			
			if(!ignore_next_line && CONSTANT.MESSAGE_LINE_MAX_LENGTH == currentLength) {
				currentLength = 0;
				System.out.println();
			}
			_this = text.substring(i,i+1);
			if(CONSTANT.NEXT_LINE_MASTER_KEY.equals(_this)) {
				System.out.println();
				currentLength = 0;
			}
			else if(CONSTANT.NEXT_LINE_IGNORE_SEPARETOR.equals(_this)) {}
			else
				System.out.print(text.charAt(i));
			
			currentLength++;
		}
	}
	
	public void Clear() {
		System.out.println(lineClear);
	}
}
