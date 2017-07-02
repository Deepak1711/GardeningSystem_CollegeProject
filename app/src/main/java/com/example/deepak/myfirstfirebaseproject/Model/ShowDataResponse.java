package com.example.deepak.myfirstfirebaseproject.Model;

import java.util.List;

/**
 * Created on 12/5/17.
 */


public class ShowDataResponse {

    /**
     * result : [{"date":"2017-05-12","light_intensity":"60","moisture_level":"50"},{"date":"2017-05-12","light_intensity":"60","moisture_level":"50"},{"date":"2017-05-12","light_intensity":"60","moisture_level":"50"},{"date":"2017-05-12","light_intensity":"60","moisture_level":"50"},{"date":"2017-05-12","light_intensity":"60","moisture_level":"50"},{"date":"2017-05-12","light_intensity":"300","moisture_level":"700"}]
     * STATUS : 700
     */

    private int STATUS;
    private List<ResultBean> result;

    public int getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(int STATUS) {
        this.STATUS = STATUS;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * date : 2017-05-12
         * light_intensity : 60
         * moisture_level : 50
         */

        private String date;
        private String light_intensity;
        private String moisture_level;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getLight_intensity() {
            return light_intensity;
        }

        public void setLight_intensity(String light_intensity) {
            this.light_intensity = light_intensity;
        }

        public String getMoisture_level() {
            return moisture_level;
        }

        public void setMoisture_level(String moisture_level) {
            this.moisture_level = moisture_level;
        }
    }
}
