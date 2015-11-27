import java.util.ArrayList;
import java.util.LinkedList;



public class memoryLink {
	public static LinkedList<job> memoryStorage = new LinkedList<job>();
	public static ArrayList<freeSpace> freeSpaceTable = new ArrayList<freeSpace>();
	
	public memoryLink(){
		job first = new job(-1,0,-1,false,true);
		job memory = new job(0,99,0,true, true);
		memoryStorage.addFirst(first);
		memoryStorage.add(memory);
	}
	
	public void freeSpaceTableBuilder(){
		freeSpaceTable.clear();
		
		for(job temp : memoryStorage){
			if(temp.getFreeSpace() == true){
				freeSpace job = new freeSpace(temp.getSize(), temp.getAddress(), temp.getJobID());
				freeSpaceTable.add(job);
			}
		}
	}
	
	public int addressFinder(int size){
		int closet =  99;
		int temp;
		int address = 0;
		boolean foundOne = false;
		for(freeSpace job: freeSpaceTable){
			temp = job.getSize() - size;
			if(temp <= closet && temp>=0){
				closet = temp;
				address = job.getAddress();
				foundOne = true;
			}
		}
		if(foundOne != true){
			closet = 99;
			for(job job: memoryStorage){
				if(job.getLatched() != true){
					temp = job.getSize() - size;  
					if(temp <= closet && temp >=0){
						closet = temp;
						address = job.getAddress();
					}
				}
			}
		}
		return address;
	}
	
	public void addTooMemory(int address, int size, int job){
		job cell = new job(address, size - 1, job, false, false);
		int index = 0;
		for(job search: memoryStorage)
			if(address == search.getAddress()){
				index = memoryStorage.indexOf(search);
				search.subSize(size);
				search.setAddress(address);
				System.out.println(search.getAddress() + " " + search.getSize());
			}
		memoryStorage.add(cell);
	}
	
	public void merge(){
		job test = memoryStorage.get(0);
		for(int search = 0; search < memoryStorage.size(); search++){
			if(search > 0){
				if(test.getFreeSpace() == true && memoryStorage.get(search).getFreeSpace() == true){
					memoryStorage.get(search).setAddress(test.getAddress());
					memoryStorage.get(search).setSize(memoryStorage.get(search).getSize() + test.getSize());
					memoryStorage.remove(test);
				}
				test = memoryStorage.get(search);
			}
		}
	}
}



class freeSpace {
	
	private final int size;
	private final int address;
	private final int job;
	
	freeSpace(int size, int address, int job){
			this.size = size;
			this.address = address;
			this.job = job;
	}
	public int  getSize(){
		return size;
	}
	public int getAddress(){
		return address;
	}
	public int getJob(){
		return job;
	}
}
