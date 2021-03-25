package com.unreal.vrcmodupdater.github;

import org.kohsuke.github.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;


public class NewRelease {

    GitHub gitHub;

    public NewRelease() throws IOException {
        this.gitHub = GitHub.connectAnonymously();
        System.out.println("Logged in as: Anonymous");
    }

    public NewRelease(String o_auth_token) throws IOException {
        this.gitHub = new GitHubBuilder().withOAuthToken(o_auth_token).build();
        System.out.println("Logged in as: " + this.gitHub.getMyself().getName());
    }

    public NewRelease(String username, String password) throws IOException {
        this.gitHub = new GitHubBuilder().withPassword(username, password).build();
        System.out.println("Logged in as: " + this.gitHub.getMyself().getName());
    }

    public Boolean isThisGitHub(String link) {
        return link.startsWith("https://github.com/");
    }

    public String githubLinkToRepo(String link) {
        String[] trimmed = link.split("/");
        return trimmed[trimmed.length - 2] + "/" + trimmed[trimmed.length - 1];
    }

    public Boolean isThisValidGitHubRepo(String gitRepo) {

        try {
            this.gitHub.getRepository(gitRepo);
        } catch (IOException e) {
            return false;
        }
        return true;
    }


    public Optional<GHAsset> getNewestReleaseByName(String modName, String repoName) {

        try {
            GHRepository repository = this.gitHub.getRepository(repoName);

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

    public ArrayList<GHAsset> getNewestReleasesByNames(ArrayList<String> names, String repoName) {
        ArrayList<String> remainingNames = new ArrayList<>(names);
        ArrayList<GHAsset> results = new ArrayList<>();

        try {
            GHRepository repository = this.gitHub.getRepository(repoName);

            for (GHRelease ghRelease : repository.listReleases()) {
                for (GHAsset ghAsset : ghRelease.listAssets()) {
                    if (remainingNames.contains(ghAsset.getName())) {
                        System.out.println("Found " + ghAsset.getName() + " in " + repository.getFullName() + " released on " + ghRelease.getCreatedAt());
                        results.add(ghAsset);
                        remainingNames.remove(ghAsset.getName());

                        if (remainingNames.isEmpty()) return results;
                    }
                }
            }

            for (String name : remainingNames) System.out.println("Couldn't find: " + name);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


        return results;
    }

    public Optional<GHAsset> getAssetFromRepo(String repoName, String modName, Long releaseID) {
        try {
            GHRepository repository = this.gitHub.getRepository(repoName);
            GHRelease release = repository.getRelease(releaseID);

            for (GHAsset asset : release.listAssets()) {
                if (asset.getName().equals(modName)) return Optional.of(asset);
            }
        } catch (Exception e) {
            System.out.println("Error while getting: " + modName + " from Repo: " + repoName + " releaseID: " + releaseID + " Message: " + e.getMessage());
        }

        return Optional.empty();
    }

}
