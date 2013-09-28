package network;

import java.util.ArrayList;


public class JobQueue {

    private ArrayList<Job> jobList;

    public JobQueue() {
        this.jobList = new ArrayList<Job>();
    }

    public synchronized void put(Job job) {
        if (job != null) {
            jobList.add(job);
            notify();
        }
    }

    
    public synchronized Job get() throws InterruptedException {
        while (jobList.isEmpty()) {
                wait();
        }
        Job j = jobList.remove(0);
        notify();
        return j;
    }


    public int size() {
        return jobList.size();
    }
}
