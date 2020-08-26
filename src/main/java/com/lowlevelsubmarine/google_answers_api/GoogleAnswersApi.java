package com.lowlevelsubmarine.google_answers_api;

import com.lowlevelsubmarine.google_answers_api.actions.RestAction;

public class GoogleAnswersApi {

    public RestAction<AskRequest> ask(String question) {
        return new RestAction<>(() -> new AskRequest(question));
    }

}
