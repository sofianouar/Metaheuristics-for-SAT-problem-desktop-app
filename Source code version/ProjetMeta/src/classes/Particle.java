package classes;

public class Particle implements Comparable<Particle> {
	
	private SAT currentPos;
	private SAT bestPos;
	private double velocity;

	
	//CONSTRUCTOR
	public Particle(SAT sol,SAT sol2,double v) {
		this.currentPos=sol;
		this.bestPos=sol2;
		this.velocity=v;
	}
	
	
	//GETTERS AND SETTERS
	public SAT getCurrentPos() {        return currentPos;}
    public void  setCurrentPos(SAT currentPos) {  this.currentPos = currentPos;}
    
	public SAT getBestPos() {        return bestPos;}
    public void  setBestPos(SAT bestPos) {  this.bestPos = bestPos;}
    
	public double getVelocity() {        return velocity;}
    public void  setVelocity(double velocity) {  this.velocity = velocity;}


	@Override
	public int compareTo(Particle o) {
		return this.getBestPos().compareTo(o.getBestPos());
		
	}
	    


}
