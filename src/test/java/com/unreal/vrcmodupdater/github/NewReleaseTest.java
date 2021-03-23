package com.unreal.vrcmodupdater.github;

import org.junit.jupiter.api.Test;
import org.kohsuke.github.GHAsset;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class NewReleaseTest {

    NewRelease newRelease = new NewRelease();

    @Test
    public void isThisGitHub_notGit() {

        //Arrange
        String notAGitLink = "https://thetrueyoshifan.com/downloads";

        //Act
        Boolean result = newRelease.isThisGitHub(notAGitLink);

        //Assert
        assertThat(result).isFalse();

    }

    @Test
    public void isThisGitHub_isGit() {

        //Arrange
        String notAGitLink = "https://github.com/knah/VRCMods/";

        //Act
        Boolean result = newRelease.isThisGitHub(notAGitLink);

        //Assert
        assertThat(result).isTrue();
    }

    @Test
    public void isGitHubReachable() {

        //Act
        Boolean result = newRelease.isGitHubReachable();

        //Assert
        assertThat(result).isTrue();
    }


    @Test
    public void githubLinkToRepo() {

        //Arrange
        String gitRepoLink = "https://github.com/GhostJumper/VRC-Mod-Updater/";

        //Act
        String result = newRelease.githubLinkToRepo(gitRepoLink);

        //Assert
        assertThat(result).isEqualTo("GhostJumper/VRC-Mod-Updater");

    }


    @Test
    public void isThisValidGitHubRepo_valid() {

        //Arrange
        String gitRepo = "GhostJumper/VRC-Mod-Updater";

        //Act
        Boolean result = newRelease.isThisValidGitHubRepo(gitRepo);

        //Assert
        assertThat(result).isTrue();

    }

    @Test
    public void isThisValidGitHubRepo_invalid() {

        //Arrange
        String gitRepo = "GhostJumper/InvalidStuff";

        //Act
        Boolean result = newRelease.isThisValidGitHubRepo(gitRepo);

        //Assert
        assertThat(result).isFalse();

    }

    @Test
    public void downloadNewestRelease_valid1() {
        //Arrange
        String modName = "FavCat-merged.dll";
        String repoName = "knah/VRCMods";

        //Act
        Optional<GHAsset> ghAsset = newRelease.getNewestReleaseByName(modName, repoName);

        //Assert
        assertThat(ghAsset).isPresent();
        assertThat(ghAsset.get()).isNotNull();
        assertThat(ghAsset.get().getName()).isEqualTo(modName);
    }

    @Test
    public void downloadNewestRelease_valid2() {
        //Arrange
        String modName = "AvatarStatsShowAuthor.dll";
        String repoName = "HerpDerpinstine/AvatarStatsShowAuthor";

        //Act
        Optional<GHAsset> ghAsset = newRelease.getNewestReleaseByName(modName, repoName);

        //Assert
        assertThat(ghAsset).isPresent();
        assertThat(ghAsset.get()).isNotNull();
        assertThat(ghAsset.get().getName()).isEqualTo(modName);
    }

    @Test
    public void downloadNewestRelease_invalid1() {
        //Arrange
        String modName = "AvatarStatsShowAuthor.dll";
        String repoName = "GhostJumper/VRC-Mod-Updater";

        //Act
        Optional<GHAsset> ghAsset = newRelease.getNewestReleaseByName(modName, repoName);

        //Assert
        assertThat(ghAsset).isNotPresent();
    }

    @Test
    public void downloadNewestRelease_invalid2() {
        //Arrange
        String modName = "AvatarStatsShowAuthor.dll";
        String repoName = "GhostJumper/Invalid-Repo-Name";

        //Act
        Optional<GHAsset> ghAsset = newRelease.getNewestReleaseByName(modName, repoName);

        //Assert
        assertThat(ghAsset).isNotPresent();
    }

}