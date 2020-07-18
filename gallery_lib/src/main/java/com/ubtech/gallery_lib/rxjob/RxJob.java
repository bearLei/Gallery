package com.ubtech.gallery_lib.rxjob;


public class RxJob {

    private static RxJob rxJob;
    private final JobManager jobManager;

    private RxJob() {
        jobManager = new JobManager();
    }

    public static RxJob getDefault() {
        if (rxJob == null) {
            synchronized (RxJob.class) {
                if (rxJob == null) {
                    rxJob = new RxJob();
                }
            }
        }
        return rxJob;
    }

    public void addJob(Job job) {
        jobManager.addJob(job);
    }

    public void clearJob() {
        jobManager.clear();
    }
}
