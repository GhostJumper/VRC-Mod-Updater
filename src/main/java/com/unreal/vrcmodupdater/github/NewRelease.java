package com.unreal.vrcmodupdater.github;

import org.kohsuke.github.GHAsset;
import org.kohsuke.github.GHRelease;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import java.io.IOException;
import java.util.Optional;


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


    public Optional<GHAsset> getNewestReleaseByName(String modName, String repoName) {

        try {
            GitHub gitHub = GitHub.connect();
            GHRepository repository = gitHub.getRepository(repoName);

            for (GHRelease ghRelease : repository.listReleases()) {
                for (GHAsset ghAsset : ghRelease.listAssets()) {
                    if (ghAsset.getName().equals(modName)) {
                        System.out.println("Found " + modName + " in " + repository.getFullName() + " released on " + ghRelease.getCreatedAt());
                        return Optional.of(ghAsset);
                    }
                }
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Couldn't find: " + modName);
        return Optional.empty();
    }

}
