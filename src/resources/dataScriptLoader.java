package resources;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Constant.constant;

public class dataScriptLoader extends Thread{
	
	
	constant CONSTANT = new constant();
	
	public xmlParser xmlParser;
	
	public Map<String, Object> dataMap = new HashMap<>();
	
	@Override
	public void run() {
		
		synchronized(this) {
			
			try {
				Charset cs = StandardCharsets.UTF_8;
			
				Path messagePath = Paths.get(CONSTANT.DATA_FOLDER_PATH+CONSTANT.MESSAGE_PATH);
				Path npcPath = Paths.get(CONSTANT.DATA_FOLDER_PATH+CONSTANT.NPCNAME_PATH);
				Path generationPath = Paths.get(CONSTANT.DATA_FOLDER_PATH+CONSTANT.GENERATION_PATH);
				
				
				List<String> messageList = new ArrayList<>();
				List<String> npcList = new ArrayList<>();
				List<String> generationList = new ArrayList<>();
				
				// Constroctor Load
				xmlParser = new xmlParser();
			
				// resource Load
				System.out.println("data 파일을 읽어오는 중입니다...");
				
				messageList = Files.readAllLines(messagePath, cs);
				npcList = Files.readAllLines(npcPath , cs);
				generationList = Files.readAllLines(generationPath , cs);
				
				Map<String, String> messageMap = new HashMap<>();
				Map<String, String> npcMap = new HashMap<>();
				Map<String, String> generationMap = new HashMap<>();
				
				// message 
				for (String line : messageList) {
					if(line.indexOf(CONSTANT.DATA_KEY_VALUE_SEPARETOR) > -1) {
						String [] split = line.split(CONSTANT.DATA_KEY_VALUE_SEPARETOR);
						messageMap.put(split[0], split[1]);
					}
				}
				for(String line : npcList) {
					if(line.indexOf(CONSTANT.DATA_KEY_VALUE_SEPARETOR) > -1) {
						String [] split = line.split(CONSTANT.DATA_KEY_VALUE_SEPARETOR);
						npcMap.put(split[0], split[1]);
					}
				}
				for(String line : generationList) {
					if(line.indexOf(CONSTANT.DATA_KEY_VALUE_SEPARETOR) > -1) {
						String [] split = line.split(CONSTANT.DATA_KEY_VALUE_SEPARETOR);
						generationMap.put(split[0], 
								npcMap.get(split[1])+CONSTANT.NPC_NAME_KEY_SEPARATOR+split[2]);
					}
				}
				
				dataMap.put("message", messageMap);
				dataMap.put("account", xmlParser.accountMap);
				dataMap.put("npcName", npcMap);
				dataMap.put("generation", generationMap);
				
				
				System.out.println("로딩 완료");
				
				notify();
			
			}catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	public void SignUp(String[] account) {
	}
}
