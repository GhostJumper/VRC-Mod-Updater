package com.unreal.vrcmodupdater.github;

import org.kohsuke.github.GitHub;

import java.io.IOException;

public class NewRelease {

    public Boolean isThisGitHub(String link) {
        return link.startsWith("https://github.com/");
    }

    public Boolean isGitHubReachable() {

        try {
            GitHub.connect();
        } catch (IOException e) {
            return false;
        }

        return true;

    }

    public String githubLinkToRepo(String link) {
        String[] trimmed = link.split("/");
        return trimmed[trimmed.length - 2] + "/" + trimmed[trimmed.length - 1];
    }

    public Boolean isThisValidGitHubRepo(String gitRepo) {

        try {
            GitHub github = GitHub.connect();
            github.getRepository(gitRepo);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

}
