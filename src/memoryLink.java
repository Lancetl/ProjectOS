


public class memoryLink {
	
	public int address;
	public int size;
	public int job;
	boolean terminated;
	
	public memoryLink next;
	
	public memoryLink(int address, int size, int job, boolean terminated){
		this.address = address;
		this.size = size;
		this.job = job;
		this.terminated = terminated;
	}
	
	
}
class memoryLinkList{
	public memoryLink firstLink; 
	
	memoryLinkList(){
		firstLink = null;
	}
	
	public boolean isEmpty(){
		return(firstLink == null);
	}
	
	public void createFirstLink(int address, int size, int job, boolean terminated){
		memoryLink newLink = new memoryLink(address, size, job, terminated);
		newLink.next = newLink;
	}
}
