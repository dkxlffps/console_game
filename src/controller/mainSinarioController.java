package controller;

import java.util.Map;
import java.util.Scanner;

import Constant.constant;
import util.messageUtil;

public class mainSinarioController {
	
	private Map<String, Object> generationMap;
	private constant CONSTANT = new constant();
	private messageUtil message = new messageUtil();
	
	private Scanner sc = new Scanner(System.in);
	
	
	
	@SuppressWarnings("unchecked")
	public mainSinarioController(Map<String, Object> dataMap) {
		this.generationMap = (Map<String, Object>) dataMap.get("generation");
	}
	
	// hunter_intro_{n}
	public void g_1_intro() throws Exception {
		
		String sinarioSequenceKey = "hunter_intro_%d";
		int sinarioLength = 5;
		int currentLength = 0;
		while(sinarioLength > currentLength) {
			String sequence = String.format(sinarioSequenceKey, currentLength+1);
			message.run(CONSTANT.DATA_TYPE_SINARIO , sinarioFomatter(sequence));
			sc.nextLine();
			currentLength ++;
		}
		
	}
	
	public String[] sinarioFomatter(String key) {
		String result = (String) this.generationMap.get(key);
		String []result2 = result.split(CONSTANT.NPC_NAME_KEY_SEPARATOR);
		return result2;
	}
}
