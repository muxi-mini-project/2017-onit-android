package com.example.kolibreath.onit.Beans;

/**
 * Created by kolibreath on 2017/3/29.
 */

public class UserCommentBean {

    /**
     * results : {"create_at":"g","cid":12,"text":" fuck"}
     * users : {"uid":1}
     */

    private ResultsBean results;
    private UsersBean users;

    public ResultsBean getResults() {
        return results;
    }

    public void setResults(ResultsBean results) {
        this.results = results;
    }

    public UsersBean getUsers() {
        return users;
    }

    public void setUsers(UsersBean users) {
        this.users = users;
    }

    public static class ResultsBean {
        /**
         * create_at : g
         * cid : 12
         * text :  fuck
         */

        private String create_at;
        private int cid;
        private String text;

        public String getCreate_at() {
            return create_at;
        }

        public void setCreate_at(String create_at) {
            this.create_at = create_at;
        }

        public int getCid() {
            return cid;
        }

        public void setCid(int cid) {
            this.cid = cid;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    public static class UsersBean {
        /**
         * uid : 1
         */

        private int uid;

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }
    }
}
