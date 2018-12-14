package edu.ycp.cs481.model;

public class ComplianceIssue {
    private String title;
    private int priority;
    private String email;

    public String getTitle() {
        return title;
    }

    public int getPriority() {
        return priority;
    }

    public String getEmail() {
        return email;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}