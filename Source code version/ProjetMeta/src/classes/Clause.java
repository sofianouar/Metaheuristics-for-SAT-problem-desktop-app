package classes;

import java.util.Arrays;
public class Clause {
	private int[] varVect;
		//Constructor 
	
	public Clause(int size) {
		this.varVect =  new int[size]; ;
	}
	
		//   Getters and Setters
	
	public int[] getVarVect() {
		return varVect;
	}
	public void setVarVect(int[] varVect) {
		this.varVect = varVect;
	}
	
		//toString
	
	@Override
	public String toString() {
		return "clause [varVect=" + Arrays.toString(varVect) + "]";
	}
	
		//Methods
	
	public int isTrue(int inst){ 
			//comparaison / Dit si la clause est vrai pour cette  instance
		int x=0;
		for (int i = 0; i < varVect.length;i++) {
			if (varVect[i]==inst)  
				{ x=1;
					return x;
				}
			}
			return x;
		}
		
	}
