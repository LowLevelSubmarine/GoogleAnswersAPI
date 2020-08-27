package com.lowlevelsubmarine.google_answers_api.actions;

public class RestAction<T> {

    private final Action<T> action;

    public RestAction(Action<T> action) {
        this.action = action;
    }

    public T complete() throws Exception {
        return action.run();
    }

    public void queue(CompleteHook<T> completeHook) {
        new Runner<>(this.action, completeHook);
    }

    public void queue(CompleteHook<T> completeHook, ExceptionHook exceptionHook) {
        new Runner<>(this.action, completeHook, exceptionHook);
    }

    private static class Runner<T> extends Thread {

        private final Action<T> action;
        private final CompleteHook<T> completeHook;
        private final ExceptionHook exceptionHook;

        public Runner(Action<T> action, CompleteHook<T> completeHook) {
            this(action, completeHook, null);
        }

        public Runner(Action<T> action, CompleteHook<T> completeHook, ExceptionHook exceptionHook) {
            this.action = action;
            this.completeHook = completeHook;
            this.exceptionHook = exceptionHook;
            this.start();
        }

        @Override
        public void run() {
            try {
                this.completeHook.onComplete(action.run());
            } catch (Exception e) {
                if (this.exceptionHook != null) {
                    this.exceptionHook.onException(e);
                } else {
                    e.printStackTrace();
                }
            }
        }

    }

    public interface CompleteHook<T> {
        void onComplete(T t);
    }

    public interface ExceptionHook {
        void onException(Exception e);
    }

}
