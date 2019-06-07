package net.fyrezz.me.landlords.tasks;

public abstract class MyTask {
	
	public MyTask() {
		init();
	}
	
	public abstract void init();
	
	public abstract void execute();

}
