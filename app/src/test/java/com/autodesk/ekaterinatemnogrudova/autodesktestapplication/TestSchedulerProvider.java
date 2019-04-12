package com.autodesk.ekaterinatemnogrudova.autodesktestapplication;

import com.autodesk.ekaterinatemnogrudova.autodesktestapplication.utils.IScheduler;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.TestScheduler;


public class TestSchedulerProvider implements IScheduler {
    private static TestScheduler testScheduler;

    // Prevent direct instantiation.
    public TestSchedulerProvider(TestScheduler testScheduler) {
        this.testScheduler = testScheduler;
    }


    public Scheduler computation() {
        return testScheduler;
    }

    public Scheduler io() {
        return testScheduler;
    }

    public Scheduler ui() {
        return testScheduler;

    }
}