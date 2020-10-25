package resources;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.events.Characters;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import Constant.constant;
import VO.characterVO;

public class xmlParser {
	
	
	static DocumentBuilderFactory docBuildFact;
	static DocumentBuilder docBuild;
	static constant CONSTANT = new constant();
	
	// Source Field
	public Document accountDoc;
	
	public Map<String , String> accountMap = new HashMap<String, String>();
	
	static TransformerFactory transfac ;
	static Transformer trans;
	
	
	public xmlParser() {
		
		// 생성후 객체 유지차원
		try {
			docBuildFact = DocumentBuilderFactory.newInstance();
			docBuild = docBuildFact.newDocumentBuilder();
			transfac = TransformerFactory.newInstance();
			trans = transfac.newTransformer();
			
			trans.setOutputProperty("{http://xml.apache.org/xslt}indent-amount","4");
			trans.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			trans.setOutputProperty(OutputKeys.INDENT , "yes");
			
			// data Sources
			File accountFile = new File(CONSTANT.DATA_FOLDER_PATH+ "\\account.xml");
			
			// Source Load
			this.accountDoc = docBuild.parse(accountFile);
			
			
			// normalize
			this.accountDoc.getDocumentElement().normalize();
			
			NodeList accountList = accountDoc.getElementsByTagName("Account");
			
			for(int i = 0; i < accountList.getLength(); i++) {
				Node account = accountList.item(i);
				if(account.getNodeType() == Node.ELEMENT_NODE) {
					
					Element el = (Element) account;
					accountMap.put((el.getAttribute(CONSTANT.ACCOUNT_KEYS[0])+
							CONSTANT.ACCOUNT_SEPARETOR+el.getAttribute(CONSTANT.ACCOUNT_KEYS[1])),
							el.getAttribute(CONSTANT.ACCOUNT_KEYS[2]));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public String SignUp(String[] account) throws Exception {
		Element root = accountDoc.getDocumentElement();
		
		Element accountElement = accountDoc.createElement("Account");
		String sequence = String.valueOf(accountDoc.getElementsByTagName("Account").getLength()+1);
		accountElement.setAttribute(CONSTANT.ACCOUNT_KEYS[2] , sequence);
		accountElement.setAttribute(CONSTANT.ACCOUNT_KEYS[0], account[0]);
		accountElement.setAttribute(CONSTANT.ACCOUNT_KEYS[1], account[1]);
		root.appendChild(accountElement);
		
		StringWriter sw = new StringWriter();
		StreamResult result = new StreamResult(sw);
		DOMSource source = new DOMSource(accountDoc);
		
		trans.transform(source, result); 
		String xmlString = sw.toString();
		
		OutputStream f0;
		byte buf[] = xmlString.getBytes();
		f0 = new FileOutputStream(CONSTANT.DATA_FOLDER_PATH+"\\account.xml");
		for(int i = 0; i<buf.length; i++) { 
			f0.write(buf[i]);
		}
		f0.close();
		buf = null;
		
		createAccountFile(sequence);
		return sequence;
	}
	
	public static void createAccountFile (String sequence) throws Exception {
		Document character = docBuild.newDocument();
		character.setXmlStandalone(true);
		
		Element root = character.createElement("CHARACTERS");
		character.appendChild(root);
		
		DOMSource source = new DOMSource(character);
		StreamResult result = new StreamResult(new FileOutputStream(new File(CONSTANT.DATA_FOLDER_PATH+"\\userAccount\\"+sequence+".xml")));
		
		trans.transform(source, result);
	}
	
	public List<characterVO> getCharacters(String sequence) throws Exception {
		// data Sources
		File file = new File(CONSTANT.DATA_FOLDER_PATH+String.format(CONSTANT.USER_ACCOUNT_PATH, sequence));
					
		// Source Load
		this.accountDoc = docBuild.parse(file);
				
		// normalize
		this.accountDoc.getDocumentElement().normalize();
					
		NodeList characters = accountDoc.getElementsByTagName("CHARACTER");
		if(characters.getLength() < 1) {
			return null;
		}else {
			List<characterVO> resultList = new ArrayList<characterVO>();
			
			
			for(int i = 0; i < characters.getLength(); i++) {
				characterVO vo = new characterVO();
				vo = getCharacter(sequence, i);
				resultList.add(vo);
			}
			return resultList;
		}
	}
	
	public characterVO CreateCharacter(String sequence , String classNm) throws Exception {
		
		File file = new File(CONSTANT.DATA_FOLDER_PATH+String.format(CONSTANT.DEFAULT_CHARACTER_PATH, classNm));
		
		File accountFile = new File(CONSTANT.DATA_FOLDER_PATH+String.format(CONSTANT.USER_ACCOUNT_PATH, sequence));
		
		File commonFile = new File(CONSTANT.DATA_FOLDER_PATH+"\\character\\default.xml");
		
		try {
			// Default Character Status Load Script
			Document doc = docBuild.parse(file);
			
			Element statusRoot = (Element) doc.getElementsByTagName("CHARACTER").item(0);
			
			NodeList statusList = statusRoot.getChildNodes();
			
			String type = statusRoot.getAttribute("type");
			String growthcurve = statusRoot.getAttribute("growthcurve");
			
			ArrayList<String> defaultKeyValue = new ArrayList<>();
			
			Map<String,ArrayList<String>> ElementKeyValue = new HashMap<String, ArrayList<String>>();
			
			for(int i = 0; i < statusList.getLength(); i++) {
				if(statusList.item(i).getNodeType() == Node.ELEMENT_NODE) {
					Element el = (Element) statusList.item(i);
					defaultKeyValue.add(el.getNodeName()+CONSTANT.DATA_KEY_VALUE_SEPARETOR+
							el.getFirstChild().getTextContent());
					if(statusList.item(i).getChildNodes().getLength() > 1 ) {
						 NodeList list = statusList.item(i).getChildNodes();
						 ArrayList<String> elementlist = new ArrayList<String>();
						 for(int j=0;j<list.getLength();j++) {
							 if(list.item(j).getNodeType()==Node.ELEMENT_NODE) {
								 Element el2 = (Element) list.item(j);
								 elementlist.add(el2.getNodeName()+CONSTANT.DATA_KEY_VALUE_SEPARETOR+
										 el2.getFirstChild().getTextContent());
							 }
						 }
						 ElementKeyValue.put(el.getNodeName(), elementlist);
					}
				}
			}
			
			Document aDoc = docBuild.parse(accountFile);
			
			// STATUS
			
			
			Node root = aDoc.getFirstChild();
			
			Element _this = aDoc.createElement("CHARACTER");
			
			_this.setAttribute("type", type);
			_this.setAttribute("growthcurve", growthcurve);
			
			Element STATUS = aDoc.createElement("STATUS");
			
			for(int i=0;i<defaultKeyValue.size();i++) {
				String key_value[] = defaultKeyValue.get(i).split(CONSTANT.DATA_KEY_VALUE_SEPARETOR);
				Element el = aDoc.createElement(key_value[0]);
				el.setTextContent(key_value[1]);
				if(ElementKeyValue.get(key_value[0]) != null) {
					ArrayList<String> list = ElementKeyValue.get(key_value[0]);
					for(int j=0;j<list.size();j++) {
						String key_value2[] = list.get(j).split(CONSTANT.DATA_KEY_VALUE_SEPARETOR);
						Element el2 = aDoc.createElement(key_value2[0]);
						el2.setTextContent(key_value2[1]);
						el.appendChild(el2);
					}
				}
				
				STATUS.appendChild(el);
			}
			
			_this.appendChild(STATUS);
			
			root.appendChild(_this);
			
			
			Document commonDoc = docBuild.parse(commonFile);
			
			Element commonRoot = (Element) commonDoc.getElementsByTagName("DEFAULT").item(0);
			
			NodeList commonNodeList = commonRoot.getChildNodes();
			
			for(int i=0; i<commonNodeList.getLength();i++) {
				if(commonNodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
					
					Element getEl = (Element) commonNodeList.item(i);
					Element setEl = aDoc.createElement(getEl.getTagName());
					
					if(getEl.getAttributes().getLength() > 0) {
						for(int j=0;j<getEl.getAttributes().getLength();j++) {
							Node at = getEl.getAttributes().item(j);
							setEl.setAttribute(at.getNodeName(), at.getNodeValue());
						}
					}
					if(getEl.getChildNodes().getLength() > 0) {
						for(int j=0;j<getEl.getChildNodes().getLength();j++) {
							if(getEl.getChildNodes().item(j).getNodeType() == Node.ELEMENT_NODE) {
								Element getChildEl = (Element) getEl.getChildNodes().item(j);
								Element setChildEl = aDoc.createElement(getChildEl.getTagName());
								
								if(getChildEl.getAttributes().getLength() > 0) {
									for(int k=0;k<getChildEl.getAttributes().getLength();k++) {
										Node at = getChildEl.getAttributes().item(k);
										setChildEl.setAttribute(at.getNodeName(),at.getNodeValue());
									}
								}
								setEl.appendChild(setChildEl);
							}
						}
					}
					_this.appendChild(setEl);
				}
			}
			
			// File Update (Create Character)
			StringWriter sw = new StringWriter();
			StreamResult result = new StreamResult(sw);
			DOMSource source = new DOMSource(aDoc);
			trans.transform(source, result); 
			String xmlString = sw.toString();
			
			OutputStream f0;
			byte buf[] = xmlString.getBytes();
			f0 = new FileOutputStream(accountFile);
			for(int i = 0; i<buf.length; i++) { 
				f0.write(buf[i]);
			}
			f0.close();
			buf = null;
			
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return getCharacter(sequence,0);
	}
	
	public characterVO getCharacter(String sequence , int index) throws Exception {
		
		characterVO vo = new characterVO();
		
		File file = new File(CONSTANT.DATA_FOLDER_PATH+String.format(CONSTANT.USER_ACCOUNT_PATH, sequence));
		
		Document root = docBuild.parse(file);
		
		Node character = root.getElementsByTagName("CHARACTER").item(index);
		
		NodeList data = character.getChildNodes();
		
		for(int i=0;i<data.getLength();i++) {
			if(data.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element el = (Element) data.item(i);
				switch(el.getNodeName()) {
					case "EQUIP" :
						NodeList list = el.getChildNodes();
						Map<String, Object> equipMap = new HashMap<String,Object>();
						for(int j=0;j<list.getLength();j++) {
							if(list.item(j).getNodeType() == Node.ELEMENT_NODE) {
								Element el2 = (Element) list.item(j);
								equipMap.put(el2.getNodeName(), setAttributesMap(el2));
							}
						}
						vo.setEQUIP(equipMap);
					break;
					case "MAINSINARIO" :
						vo.setMAINSINARIO(el.getAttribute("ID"));
						break;
					case "QUESTS" :
						NodeList questNL = el.getChildNodes();
						List<Map<String,Object>> questList = new ArrayList<Map<String,Object>>();
						for(int j=0;j<questNL.getLength();j++) {
							if(questNL.item(j).getNodeType() == Node.ELEMENT_NODE) {
								Element el2 = (Element) questNL.item(j);
								questList.add(setAttributesMap(el2));
							}
						}
						break;
					case "ITEMS" :
						NodeList itemNL = el.getChildNodes();
						List<Map<String, Object>> itemList = new ArrayList<Map<String,Object>>();
						for(int j=0;j<itemNL.getLength();j++) {
							if(itemNL.item(j).getNodeType() == Node.ELEMENT_NODE) {
								Element el2 = (Element) itemNL.item(j);
								itemList.add(setAttributesMap(el2));
							}
						}
						vo.setITEM(itemList);
						break;
					case "STATUS" :
						NodeList statusNL = el.getChildNodes();
						Map<String,Object> statusMap = new HashMap<>();
						for(int j=0;j<statusNL.getLength();j++) {
							if(statusNL.item(j).getNodeType() == Node.ELEMENT_NODE) {
								Element el2 = (Element) statusNL.item(j);
								switch(el2.getNodeName()) {
									case "HP" :
										statusMap.put("CUR_HP", Integer.parseInt(el2.getTextContent()));
										statusMap.put(el2.getNodeName(), Integer.parseInt(el2.getTextContent()));
										break;
									case "MP" :
										statusMap.put("CUR_MP", Integer.parseInt(el2.getTextContent()));
										statusMap.put(el2.getNodeName(), Integer.parseInt(el2.getTextContent()));
										break;
									case "CLASSNAME" :
										statusMap.put(el2.getNodeName(), el2.getTextContent());
										break;
									case "CLASSCODE" :
										statusMap.put(el2.getNodeName(), el2.getTextContent());
										break;
									case "ELEMENT_DEF" :
										Map<String ,Integer> elementDefMap = new HashMap<>();
										NodeList elDefNL = el2.getChildNodes();
										for(int k=0;k<elDefNL.getLength();k++) {
											if(elDefNL.item(k).getNodeType() == Node.ELEMENT_NODE) {
												Element elDef = (Element) elDefNL.item(k);
												elementDefMap.put(elDef.getNodeName(), Integer.parseInt(elDef.getTextContent()));
											}
										}
										statusMap.put(el2.getNodeName(), elementDefMap);
										break;
									case "ELEMENT_ATK" :
										Map<String, Integer> elementAtkMap = new HashMap<>();
										NodeList elAtkNL = el2.getChildNodes();
										for(int k=0;k<elAtkNL.getLength();k++) {
											if(elAtkNL.item(k).getNodeType() == Node.ELEMENT_NODE) {
												Element elAtk = (Element) elAtkNL.item(k);
												elementAtkMap.put(elAtk.getNodeName(), Integer.parseInt(elAtk.getTextContent()));
											}
										}
										statusMap.put(el2.getNodeName(), elementAtkMap);
										break;
									default :
										statusMap.put(el2.getNodeName(), Integer.parseInt(el2.getTextContent()));
										break;
								}
							}
						}
						vo.setSTATUS(statusMap);
						break;
				}
				
			}
		}
		return vo;
	}
	
	public Map<String,Object> setAttributesMap(Element el) {
		Map<String, Object> resultMap = new HashMap<String,Object>();
		
		NamedNodeMap nl = el.getAttributes();
		for(int i=0;i<nl.getLength();i++) {
			Node node = nl.item(i);
			resultMap.put(node.getNodeName(), node.getNodeValue());
		}
		return resultMap;
	}
	
}
