package edu.ycp.cs481.model;

public class ComplianceIssue {
    //variables
    ComplianceIssue list = new ComplianceIssue();
    private String title;
    private int priority;
    private String address;


    public String getSOPTitle() {
        return title;
    }

    public int getPriority() {
        return priority;
    }

    public String getEmail() {
        return address;
    }

 

    public void setSOPTitle(String title) {
        this.title = title;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setEmail(String address) {
        this.address = address;
    }

   
}