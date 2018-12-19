package com.example.myqq.net.netbean;

public class LoginResultBean {

    /**
     * params : {"HeadImageNumber":"0","Token":"cQ7Ho7N2qvje/dXq3XiuNK1v1z9SPiVYEctKfLZtcJVX8l+ElAsP8yii0wHli0kEcaDpEC8snfSDtL3nsa9mbw==","Result":"success","Nickname":"Kalac"}
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
         * HeadImageNumber : 0
         * Token : cQ7Ho7N2qvje/dXq3XiuNK1v1z9SPiVYEctKfLZtcJVX8l+ElAsP8yii0wHli0kEcaDpEC8snfSDtL3nsa9mbw==
         * Result : success
         * Nickname : Kalac
         */

        private String Result;
        private String Nickname;

        public String getResult() {
            return Result;
        }

        public void setResult(String Result) {
            this.Result = Result;
        }

        public String getNickname() {
            return Nickname;
        }

        public void setNickname(String Nickname) {
            this.Nickname = Nickname;
        }
    }
}
