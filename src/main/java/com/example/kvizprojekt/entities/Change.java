package com.example.kvizprojekt.entities;

import java.io.Serial;
import java.io.Serializable;


public final class Change implements Serializable {

    @Serial
    private static final long serialVersionUID = 7412517655745930504L;
    private String username;
    private String dateTime;
    private String typeOfChange;
    private Question questionOldValue;
    private Question questionNewValue;

    private Change(Builder builder) {
        username = builder.username;
        dateTime = builder.dateTime;
        typeOfChange = builder.typeOfChange;
        questionOldValue = builder.questionOldValue;
        questionNewValue = builder.questionNewValue;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private String username;
        private String dateTime;
        private String typeOfChange;
        private Question questionOldValue;
        private Question questionNewValue;

        private Builder() {
        }

        public Builder withUsername(String val) {
            username = val;
            return this;
        }

        public Builder withDateTime(String val) {
            dateTime = val;
            return this;
        }

        public Builder withTypeOfChange(String val) {
            typeOfChange = val;
            return this;
        }

        public Builder withQuestionOldValue(Question val) {
            questionOldValue = val;
            return this;
        }

        public Builder withQuestionNewValue(Question val) {
            questionNewValue = val;
            return this;
        }

        public Change build() {
            return new Change(this);
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getTypeOfChange() {
        return typeOfChange;
    }

    public void setTypeOfChange(String typeOfChange) {
        this.typeOfChange = typeOfChange;
    }

    public Question getQuestionOldValue() {
        return questionOldValue;
    }

    public void setQuestionOldValue(Question questionOldValue) {
        this.questionOldValue = questionOldValue;
    }

    public Question getQuestionNewValue() {
        return questionNewValue;
    }

    public void setQuestionNewValue(Question questionNewValue) {
        this.questionNewValue = questionNewValue;
    }
}











