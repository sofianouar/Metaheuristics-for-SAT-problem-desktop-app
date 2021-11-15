package classes;

public class Node {
		private SAT content;
		private Node FG;
		private Node FD;
		//Constructor 
		
		public Node(SAT content, Node fG, Node fD) {
			this.content = content;
			FG = fG;
			FD = fD;
		}

	    //  Getters and Setters
		
		public SAT getContent() {
			return content;
		}

		public void setContent(SAT content) {
			this.content = content;
		}

		public Node getFG() {
			return FG;
		}

		public void setFG(Node fG) {
			FG = fG;
		}

		public Node getFD() {
			return FD;
		}

		public void setFD(Node fD) {
			FD = fD;
		}
		
}
