package net.fyrezz.me.landlords.tasks;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.scheduler.BukkitRunnable;

import net.fyrezz.me.landlords.P;

/**
 * Used to manage programmed tasks.
 * 
 * @author Fyrezz
 *
 */
public class Countdowner {

	private Map<MyTask, Long> programmedTasks = new HashMap<MyTask, Long>();

	public Countdowner() {
		start();
	}

	private void start() {
		new BukkitRunnable() {
			public void run() {
				for (MyTask task : programmedTasks.keySet()) {
					if (programmedTasks.get(task) < 1L) {
						removeTask(task);
						task.execute();
					}
				}
			}
		}.runTaskTimer(P.p, 0, 1L);
	}
	
	public void addTask(MyTask task, long time) {
		programmedTasks.put(task, time);
	}
	
	public void removeTask(MyTask task) {
		programmedTasks.remove(task);
	}

}
