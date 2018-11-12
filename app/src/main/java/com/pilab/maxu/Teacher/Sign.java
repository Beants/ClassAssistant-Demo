package com.pilab.maxu.Teacher;

public class Sign {
    public Sign() {
    }

    public Sign(String classname, String bili, String num,String state) {
        this.classname = classname;
        this.bili = bili;
        this.state=state;
        this.num = num;
    }

    private String classname;
    private String bili;
    private String num;
    private String state;


    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }



    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getBili() {
        return bili;
    }

    public void setBili(String bili) {
        this.bili = bili;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
