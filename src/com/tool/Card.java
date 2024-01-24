package com.tool;

public class Card {

	private String term;
	private String def;
	private int num;

	public Card(String term, String def, int num) {
		this.term = term;
		this.def = def;
		this.num = num;
	}
	
	public String getTerm() {
        return term;
    }

    public String getDef() {
        return def;
    }

    public int getNum() {
        return num;
    }
    
    public void setTerm(String term) {
    	this.term = term;
    }
	
    public void setDef(String def) {
    	this.def = def;
    }
	public void createSet() {
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
