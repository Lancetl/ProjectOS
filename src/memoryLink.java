public class memoryLink {
	
	public int address;
	public int size;
	public int job;
	boolean freeSpace;
	boolean busy;
	
	public memoryLink(int address, int size, int job, boolean freeSpace, boolean busy){
		this.address = address;
		this.size = size;
		this.job = job;
		this.freeSpace = freeSpace;
		this.busy = busy;
	}
	public void freeSpace(boolean freeSpace){
		this.freeSpace = freeSpace;
	}
	public void setBusy(boolean busy){
		this.busy = busy;
	}
}