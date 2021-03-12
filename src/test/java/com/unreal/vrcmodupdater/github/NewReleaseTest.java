package com.unreal.vrcmodupdater.github;

import org.junit.jupiter.api.Test;

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

}