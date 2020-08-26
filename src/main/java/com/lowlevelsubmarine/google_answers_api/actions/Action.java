package com.lowlevelsubmarine.google_answers_api.actions;

public interface Action<T> {

    T run() throws Exception;

}
