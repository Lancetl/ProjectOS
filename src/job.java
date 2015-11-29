public class job {
	
	private int jobID;
	private int prio;
	private int size;
	private int max;
	private int entered;
	
	//variables needed to memoryLink
	private int address;
	private boolean freeSpace;
	private boolean latched = false;
	
	public job(int jobID, int prio, int size, int max, int entered){
		this.jobID = jobID;
		this.prio = prio;
		this.size = size;
		this.max = max;
		this.entered = entered;
	}
	public job(int jobID, int size, int address, boolean freeSpace, boolean latched){
		this.jobID = jobID;
		this.size = size;
		this.address = address;
		this.freeSpace = freeSpace;
		this.latched = latched;
	}

	public int getJobID() {
		return jobID;
	}

	public void setJobID(int jobID) {
		this.jobID = jobID;
	}

	public int getPrio() {
		return prio;
	}

	public void setPrio(int prio) {
		this.prio = prio;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public int getEntered() {
		return entered;
	}

	public void setEntered(int entered) {
		this.entered = entered;
	}
	public int getAddress(){
		return address;
	}
	public void setAddress(int address){
		this.address = address;
	}
	public boolean getFreeSpace(){
		return freeSpace;
	}
	public void setFreeSpace(boolean freeSpace){
		this.freeSpace = freeSpace;
	}
	public boolean getLatched(){
		return latched;
	}
	public void setLatched(boolean latched){
		this.latched = latched;
	}
	public void subSize(int size){
		this.size -= size;
	}

}