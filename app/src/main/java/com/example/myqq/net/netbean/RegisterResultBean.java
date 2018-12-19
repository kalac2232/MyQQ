package com.example.myqq.net.netbean;

public class RegisterResultBean {

    /**
     * params : {"RegisterNumber":"999999","Result":"success"}
     */

    private ParamsBean params;

    public ParamsBean getParams() {
        return params;
    }

    public void setParams(ParamsBean params) {
        this.params = params;
    }

    public static class ParamsBean {
        /**
         * RegisterNumber : 999999
         * Result : success
         */

        private String RegisterNumber;
        private String Result;

        public String getRegisterNumber() {
            return RegisterNumber;
        }

        public void setRegisterNumber(String RegisterNumber) {
            this.RegisterNumber = RegisterNumber;
        }

        public String getResult() {
            return Result;
        }

        public void setResult(String Result) {
            this.Result = Result;
        }
    }
}
