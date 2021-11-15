package classes;


public class NodeA implements Comparable<NodeA> {
    private SAT content;
    private int dpth;
    private int heur;

    public NodeA(SAT content, int dpth, int heur) {
        this.content=content;
        this.dpth=dpth;
        int i;
        for(i=0;i<this.content.getInstVect().length;i++){
            this.content.getInstVect()[i]=0;
        }
        this.heur = heur;
    }
	
	 //   Getters and Setters

	public SAT getContent() {
		return content;
	}
	public void setContent(SAT content) {
		this.content = content;
	}

	public int getDpth() {
		return dpth;
	}
	public void setDpth(int dpth) {
		this.dpth = dpth;
	}
	
	public int getHeur(){
        return this.heur;
    }
	public void setHeur(int heur) {
		this.heur = heur;
	}
	public void Affichage(){
        for (int j : this.content.getInstVect()) {
            System.out.print(j + "\t");
        }
    }

	


	public int compareTo(NodeA node2){
		return node2.getContent().getNbSat()-this.getContent().getNbSat();
	}


	
}	

