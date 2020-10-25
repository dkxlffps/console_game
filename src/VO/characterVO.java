package VO;

import java.util.List;
import java.util.Map;

public class characterVO {
	
	
	private String growthCurve;
	private String growthType;
	
	
	// status
	private String classNm;
	private String classCd;
	private int CUR_EXP;
	private int EXP;
	private int LV;
	
	private int HP;
	private int CUR_HP;
	private int CUR_MP;
	private int MP;
	private int CRITICAL;
	private int PH_DAMAGE;
	private int AB_damage;
	
	private int PH_DEF;
	private int AB_DEF;
	
	private Map<String, Integer> ELEMENT_DEF;
	private Map<String, Integer> ELEMENT_ATK;

	private int HIT_RATE;
	private int EVASION_RATE;
	
	private int STR;
	private int INT;
	private int DEX;
	private int LUCK;
	private int BONUS_STAT;
	
	// use Status
	private Map<String,Object> STATUS;
	
	
	// end Status
	private String MAINSINARIO;
	
	private List<Map<String,Object>> QUEST;
	private Map<String, Object> EQUIP;
	private List<Object> SKILL;
	private List<Map<String,Object>> ITEM;
	
	
	private List<String> BUFFLIST;
	private List<String> CURSELIST;
	
	
	public Map<String, Object> getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(Map<String, Object> sTATUS) {
		STATUS = sTATUS;
	}
	public List<String> getBUFFLIST() {
		return BUFFLIST;
	}
	public void setBUFFLIST(List<String> bUFFLIST) {
		BUFFLIST = bUFFLIST;
	}
	public List<String> getCURSELIST() {
		return CURSELIST;
	}
	public void setCURSELIST(List<String> cURSELIST) {
		CURSELIST = cURSELIST;
	}
	public int getCUR_HP() {
		return CUR_HP;
	}
	public void setCUR_HP(int cUR_HP) {
		CUR_HP = cUR_HP;
	}
	public int getCUR_MP() {
		return CUR_MP;
	}
	public void setCUR_MP(int cUR_MP) {
		CUR_MP = cUR_MP;
	}
	public List<Map<String, Object>> getITEM() {
		return ITEM;
	}
	public void setITEM(List<Map<String, Object>> iTEM) {
		ITEM = iTEM;
	}
	public String getClassNm() {
		return classNm;
	}
	public void setClassNm(String classNm) {
		this.classNm = classNm;
	}
	public String getClassCd() {
		return classCd;
	}
	public void setClassCd(String classCd) {
		this.classCd = classCd;
	}
	public String getGrowthCurve() {
		return growthCurve;
	}
	public void setGrowthCurve(String growthCurve) {
		this.growthCurve = growthCurve;
	}
	public String getGrowthType() {
		return growthType;
	}
	public void setGrowthType(String growthType) {
		this.growthType = growthType;
	}
	public int getCUR_EXP() {
		return CUR_EXP;
	}
	public void setCUR_EXP(int cUR_EXP) {
		CUR_EXP = cUR_EXP;
	}
	public int getEXP() {
		return EXP;
	}
	public void setEXP(int eXP) {
		EXP = eXP;
	}
	public int getLV() {
		return LV;
	}
	public void setLV(int lV) {
		LV = lV;
	}
	public int getHP() {
		return HP;
	}
	public void setHP(int hP) {
		HP = hP;
	}
	public int getMP() {
		return MP;
	}
	public void setMP(int mP) {
		MP = mP;
	}
	public int getCRITICAL() {
		return CRITICAL;
	}
	public void setCRITICAL(int cRITICAL) {
		CRITICAL = cRITICAL;
	}
	public int getPH_DAMAGE() {
		return PH_DAMAGE;
	}
	public void setPH_DAMAGE(int pH_DAMAGE) {
		PH_DAMAGE = pH_DAMAGE;
	}
	public int getAB_damage() {
		return AB_damage;
	}
	public void setAB_damage(int aB_damage) {
		AB_damage = aB_damage;
	}
	public int getPH_DEF() {
		return PH_DEF;
	}
	public void setPH_DEF(int pH_DEF) {
		PH_DEF = pH_DEF;
	}
	public int getAB_DEF() {
		return AB_DEF;
	}
	public void setAB_DEF(int aB_DEF) {
		AB_DEF = aB_DEF;
	}
	public Map<String, Integer> getELEMENT_DEF() {
		return ELEMENT_DEF;
	}
	public void setELEMENT_DEF(Map<String, Integer> eLEMENT_DEF) {
		ELEMENT_DEF = eLEMENT_DEF;
	}
	public Map<String, Integer> getELEMENT_ATK() {
		return ELEMENT_ATK;
	}
	public void setELEMENT_ATK(Map<String, Integer> eLEMENT_ATK) {
		ELEMENT_ATK = eLEMENT_ATK;
	}
	public int getHIT_RATE() {
		return HIT_RATE;
	}
	public void setHIT_RATE(int hIT_RATE) {
		HIT_RATE = hIT_RATE;
	}
	public int getEVASION_RATE() {
		return EVASION_RATE;
	}
	public void setEVASION_RATE(int eVASION_RATE) {
		EVASION_RATE = eVASION_RATE;
	}
	public int getSTR() {
		return STR;
	}
	public void setSTR(int sTR) {
		STR = sTR;
	}
	public int getINT() {
		return INT;
	}
	public void setINT(int iNT) {
		INT = iNT;
	}
	public int getDEX() {
		return DEX;
	}
	public void setDEX(int dEX) {
		DEX = dEX;
	}
	public int getLUCK() {
		return LUCK;
	}
	public void setLUCK(int lUCK) {
		LUCK = lUCK;
	}
	public int getBONUS_STAT() {
		return BONUS_STAT;
	}
	public void setBONUS_STAT(int bONUS_STAT) {
		BONUS_STAT = bONUS_STAT;
	}
	public String getMAINSINARIO() {
		return MAINSINARIO;
	}
	public void setMAINSINARIO(String mAINSINARIO) {
		MAINSINARIO = mAINSINARIO;
	}
	public List<Map<String,Object>> getQUEST() {
		return QUEST;
	}
	public void setQUEST(List<Map<String,Object>> qUEST) {
		QUEST = qUEST;
	}
	public Map<String, Object> getEQUIP() {
		return EQUIP;
	}
	public void setEQUIP(Map<String, Object> eQUIP) {
		EQUIP = eQUIP;
	}
	public List<Object> getSKILL() {
		return SKILL;
	}
	public void setSKILL(List<Object> sKILL) {
		SKILL = sKILL;
	}
	
	
	
}
