public class job {
	
	private int jobID;
	private int prio;
	private int size;
	private int max;
	private int entered;
	
	public job(int jobID, int prio, int size, int max, int entered){
		this.jobID = jobID;
		this.prio = prio;
		this.size = size;
		this.max = max;
		this.entered = entered;
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


}