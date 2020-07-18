package com.ubtech.gallery_lib.rxjob;


import androidx.annotation.NonNull;


public interface Job {
    Result onRunJob();

    enum Result {

        SUCCESS(), FAILURE();

        private Object data;

        Result() {
        }

        public Object getResultData() {
            return data;
        }

        public void setResultData(Object data) {
            this.data = data;
        }
    }

    class JobParams {
        private final Object data;
        private final String tag;

        public JobParams(@NonNull String tag, Object requestData) {
            this.tag = tag;
            this.data = requestData;
        }

        public String getTag() {
            return tag;
        }

        public Object getRequestData() {
            return data;
        }
    }
}
