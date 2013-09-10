package network;

import java.util.ArrayList;

/**
 * A synchronized FIFO queue which is used to store and distribute Jobs to several different threads.
 *
 * @author Carl Bjerggaard
 * @author Johan Hallberg
 */
public class JobQueue {

    private ArrayList<Job> jobList;

    /**
     * Constructor.
     */
    public JobQueue() {
        this.jobList = new ArrayList<Job>();
    }

    /**
     * A synchronized method which will place the Job in a FIFO queue and notify all threads waiting to fetch from the queue.
     *
     * @param job       The Job to be placed in the queue
     */
    public synchronized void put(Job job) {
        if (job != null) {
            jobList.add(job);
            notify();
        }
    }

    /**
     * A synchronized method which will fetch a Job from a FIFO queue if there is any available. If not it will wait for a Job to be put in the queue.
     * 
     * @return      The first available Job in the queue
     */
    public synchronized Job get() throws InterruptedException {
        while (jobList.isEmpty()) {
                wait();
        }
        Job j = jobList.remove(0);
        notify();
        return j;
    }

    /**
     * Returns the number of Jobs in the queue.
     *
     * @return      The number of jobs in the queue.
     */
    public int size() {
        return jobList.size();
    }
}
