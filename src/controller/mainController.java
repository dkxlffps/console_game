package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import Constant.constant;
import VO.characterVO;
import resources.dataScriptLoader;
import resources.xmlParser;
import util.choiceUtil;
import util.messageUtil;

public class mainController {
	private static messageUtil message = new messageUtil();
	private static choiceUtil choice = new choiceUtil();
	private static constant CONSTANT = new constant();
	
	public static Map<String , String> messageMap;
	public static Map<String , String> accountMap;
	public static Map<String , String> generationMap;
	public static Map<String , String> npcNameMap;
	
	
	public static Scanner sc = new Scanner(System.in);
	
	public static xmlParser xmlparser;
	
	public static String SEQUENCE = "";
	
	public static String userStatus = "login";
	public static characterVO user ;
	
	public static mainSinarioController sinario;
	
	public static void main(String[] args) throws Exception {
		LoadScript();
		Controller();
	}
	
	public static void Controller() throws Exception {
		boolean isEnd = false;
		while(!isEnd) {
			switch(userStatus) {
			case "login" :
				Login();
				userStatus = "select_character";
				break;
			case "select_character" :
				SelectCharacter();
				break;
			case "create_character" :
				CreateCharacter();
				userStatus = "select_character";
				break;
			case "enter_world" :
				if(user.getMAINSINARIO().equals("G_1_INTRO"))
					userStatus = "main_sinario";
				else 
					EnterWorld();
				break;
			case "main_sinario" :
				MainSinario();
				break;
			case "" :
				
				break;
			case "end" :
				isEnd = true;
				break;
			}
		}
	}
	public static void EnterWorld() throws Exception {
		
	}
	
	public static void MainSinario() throws Exception {
		String sequence = user.getMAINSINARIO();
		switch(sequence) {
			case "G_1_INTRO" :
				sinario.g_1_intro();
				userStatus = "";
			break;
		}
	}
	
	public static void Login() throws Exception {
		sc.nextLine();
		message.Clear();
		boolean isLogin = false;
		boolean try_sign_in = true;
		String login_choice_num = ChoiceMessage("login_choice", choice.getChoiceKeys(2) , false);
		if(login_choice_num.equals("2")) try_sign_in = false;
		while(isLogin == false) {
			if(try_sign_in) {
				TextMessage("sign_in");
				String account = sc.nextLine();
				String sequence = accountMap.get(account);
				if(sequence != null) {
					TextMessage("success_sign_in");
					isLogin = true;
					SEQUENCE = sequence;
				}
				else {
					TextMessage("fault_sign_in");
					String login_choice_result = ChoiceMessage("login_choice_2", choice.getChoiceKeys(2) , false);
					if(login_choice_result.equals("2")) {
						// 새로 생성
						try_sign_in = false;
					}
				}
			}else {
				TextMessage("sign_up");
				String result = sc.nextLine();
				String sign_up_account[] = result.split(CONSTANT.ACCOUNT_SEPARETOR);
				if(sign_up_account.length != 2) {
					TextMessage("sign_up_fault");
				}
				else {
					// Sign_up Script
					try {
						SEQUENCE = xmlparser.SignUp(sign_up_account);
						TextMessage("success_sign_up");
						accountMap.put(result, SEQUENCE);
						try_sign_in = true;
					}catch(Exception e) {
						TextMessage("fault_sign_up");
					}
				}
				
			}
		}
		Thread.sleep(300);
		message.Clear();
	}
	
	@SuppressWarnings("unchecked")
	public static void LoadScript() throws Exception {
		
		dataScriptLoader loaderThread = new dataScriptLoader();
		loaderThread.start();
		Map<String , Object> dataMap = new HashMap<String, Object>();
		
		synchronized(loaderThread) {
			loaderThread.wait();
			dataMap = loaderThread.dataMap;
			xmlparser = loaderThread.xmlParser;
		}
		
		
		messageMap = (Map<String, String>) dataMap.get("message");
		accountMap = (Map<String, String>) dataMap.get("account");
		generationMap = (Map<String, String>) dataMap.get("generation");
		npcNameMap = (Map<String,String>) dataMap.get("npcName");
		
		sinario = new mainSinarioController(dataMap);
		
		message.Clear();
		TextMessage("init_clear");
	}
	
	public static void SelectCharacter() throws Exception {
		String result = ChoiceMessage("login_select_character", choice.getChoiceKeys(2), false);
		if(result.equals("2")) {
			userStatus = "create_character";
			return;
		}
		
		List<characterVO> characterList = xmlparser.getCharacters(SEQUENCE);
		
		if(characterList == null) {
			TextMessage("empty_character");
			characterList = new ArrayList<characterVO>();
			characterVO vo = CreateCharacter();
			characterList.add(vo);
		}
		
		String text ="";
		for(int i=0;i<characterList.size();i++) {
			characterVO vo = characterList.get(i);
			text += String.format(messageMap.get("select_character"), i+1, 
						vo.getSTATUS().get("CLASSNAME") , vo.getSTATUS().get("LV"));
		}
		while(true) {
			message.TextMessage(text);
			String result2 = sc.nextLine();
			try {
				int index = Integer.parseInt(result2);
				if(index == 0) {
					userStatus = "select_character";
					break;
				}
				user = characterList.get(index-1);
				userStatus = "enter_world";
				break;
			}catch (Exception e) {
				TextMessage("is_fault_insert");
				continue;
			}
		}
	}
	
	public static characterVO CreateCharacter() throws Exception {
		characterVO character = null;
		while(true) {
			String result = ChoiceMessage("character_choice", choice.getChoiceKeys(2) , true);
			String classNm = "";
			if(result.equals("1")) {
				TextMessage("introduce_gambler");
				classNm = "gambler";
			}else if(result.equals("2")){
				TextMessage("introduce_hunter");
				classNm = "shadowLoad";
			}else if(result.equals("0")) {
				userStatus = "select_character";
				break;
			}
			String back_result = ChoiceMessage("character_choice_request", choice.getChoiceKeys(2) , false);
			if(back_result.equals("1")) {
				character = xmlparser.CreateCharacter(SEQUENCE, classNm);
				break;
			}
		}
		return character;
	}
	
	public static void TextMessage(String keys) throws Exception {
		if(keys.indexOf(CONSTANT.TEXT_MESSAGE_SEPARETOR) > -1) {
			String keysClone = keys.replace(CONSTANT.TEXT_MESSAGE_SEPARETOR+"back_request", "");
			boolean isBack = false;
			if(!keys.equals(keysClone)) 
				isBack = true;
			String [] key =	keysClone.split(CONSTANT.TEXT_MESSAGE_SEPARETOR);
			for (int i=0;i<key.length;i++) {
				message.run(CONSTANT.DATA_TYPE_TEXT, messageMap.get(key[i])
						+(isBack && i+1==key.length?messageMap.get("back_request"):""));
			}
		}else {
			message.run(CONSTANT.DATA_TYPE_TEXT, messageMap.get(keys));
		}
	}
	
	public static String ChoiceMessage(String keys , String isNotEmpty[] , boolean isBack) throws Exception {
		String result ;
		if(isBack) 
			keys += CONSTANT.TEXT_MESSAGE_SEPARETOR+"back_request";
		while(true) {
			TextMessage(keys);
			result = sc.nextLine();
			// result Validation Check
			if(!result.isEmpty()) {
				boolean isAnswer = false;
				for (String notEmpty : isNotEmpty) {
					if(result.equals(notEmpty) || (isBack && result.equals("0"))) 
						isAnswer = true;
					if(isAnswer) break;
				}
				if(!isAnswer) 
					TextMessage("is_fault_insert");
				else break;
			}
		}
		return result;
	}
}
