package com.example.creativejsontask.model;


public class ListItem
{
    private String repoName;
    private String description;
    private String userName;
    private boolean fork;
    private String repo_url;
    private String owner_url;

    public ListItem(String repoName, String description, String userName, boolean fork, String repo_url, String owner_url) {
        this.repoName = repoName;
        this.description = description;
        this.userName = userName;
        this.fork = fork;
        this.repo_url = repo_url;
        this.owner_url = owner_url;
    }

    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isFork() {
        return fork;
    }

    public void setFork(boolean fork) {
        this.fork = fork;
    }

    public String getRepo_url() {
        return repo_url;
    }

    public void setRepo_url(String repo_url) {
        this.repo_url = repo_url;
    }

    public String getOwner_url() {
        return owner_url;
    }

    public void setOwner_url(String owner_url) {
        this.owner_url = owner_url;
    }
}
