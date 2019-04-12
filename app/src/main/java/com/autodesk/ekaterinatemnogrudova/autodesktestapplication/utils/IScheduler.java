package com.autodesk.ekaterinatemnogrudova.autodesktestapplication.utils;

import io.reactivex.Scheduler;

public interface IScheduler {

    Scheduler io();
    Scheduler ui();
    Scheduler computation();

}