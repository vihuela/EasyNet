package com.hadlink.easynetsample.datamanager.bean;

public class RequestInfo {
    public String method;
    public String ip;
    public String url;
    public String des;
    public String upload;
    public Author author;

    public class Author {
        public String name;
        public String fullname;
        public String github;
        public String address;
        public String qq;
        public String email;
        public String des;

    }
}
