package com.unreal.vrcmodupdater.github;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.kohsuke.github.GHAsset;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class NewReleaseTest {

    static NewRelease newRelease = null;

    @BeforeAll
    public static void beforeAll() {
        try {
            newRelease = new NewRelease();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

        //Assert
        assertThat(newRelease).isNotNull();
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
    public void getNewestReleaseByName_valid1() {
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
    public void getNewestReleaseByName_valid2() {
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
    public void getNewestReleaseByName_invalid1() {
        //Arrange
        String modName = "AvatarStatsShowAuthor.dll";
        String repoName = "GhostJumper/VRC-Mod-Updater";

        //Act
        Optional<GHAsset> ghAsset = newRelease.getNewestReleaseByName(modName, repoName);

        //Assert
        assertThat(ghAsset).isNotPresent();
    }

    @Test
    public void getNewestReleaseByName_invalid2() {
        //Arrange
        String modName = "AvatarStatsShowAuthor.dll";
        String repoName = "GhostJumper/Invalid-Repo-Name";

        //Act
        Optional<GHAsset> ghAsset = newRelease.getNewestReleaseByName(modName, repoName);

        //Assert
        assertThat(ghAsset).isNotPresent();
    }

    @Test
    public void getNewestReleasesByNames_allPresent() {
        //Arrange
        ArrayList<String> names = new ArrayList<>(Arrays.asList("FavCat-merged.dll", "IKTweaks.dll", "AdvancedSafety.dll", "JoinNotifier.dll"));
        String repoName = "knah/VRCMods";

        //Act
        ArrayList<GHAsset> results = newRelease.getNewestReleasesByNames(names, repoName);

        //Assert
        assertThat(results).isNotNull();
        assertThat(results.size()).isEqualTo(4);

        results.forEach(result -> {
            assertThat(names.contains(result.getName())).isTrue();
        });
    }

    @Test
    public void getNewestReleasesByNames_notAllPresent() {
        //Arrange
        ArrayList<String> names = new ArrayList<>(Arrays.asList("FavCat-merged.dll", "thisDoesntExist.dll"));
        String repoName = "knah/VRCMods";

        //Act
        ArrayList<GHAsset> results = newRelease.getNewestReleasesByNames(names, repoName);

        //Assert
        assertThat(results).isNotNull();
        assertThat(results.size()).isEqualTo(1);

        assertThat(results.get(0).getName()).isEqualTo(names.get(0));
    }

    @Test
    public void getAssetFromRepo_valid() {
        //Arrange
        String modName = "IKTweaks.dll";
        String repoName = "knah/VRCMods";
        Long releaseID = 40254840l;

        //Act
        Optional<GHAsset> ghAsset = newRelease.getAssetFromRepo(repoName, modName, releaseID);

        //Assert
        assertThat(ghAsset).isPresent();
        assertThat(ghAsset.get().getName()).isEqualTo(modName);
    }

    @Test
    public void getAssetFromRepo_invalidReleaseID() {
        //Arrange
        String modName = "IKTweaks.dll";
        String repoName = "knah/VRCMods";
        Long releaseID = 4l;

        //Act
        Optional<GHAsset> ghAsset = newRelease.getAssetFromRepo(repoName, modName, releaseID);

        //Assert
        assertThat(ghAsset).isNotPresent();
    }

    @Test
    public void getAssetFromRepo_invalidRepoName() {
        //Arrange
        String modName = "IKTweaks.dll";
        String repoName = "knah/NonExistent";
        Long releaseID = 40254840l;

        //Act
        Optional<GHAsset> ghAsset = newRelease.getAssetFromRepo(repoName, modName, releaseID);

        //Assert
        assertThat(ghAsset).isNotPresent();
    }

    @Test
    public void getAssetFromRepo_invalidModName() {
        //Arrange
        String modName = "IKTweak.dll";
        String repoName = "knah/VRCMods";
        Long releaseID = 40254840l;

        //Act
        Optional<GHAsset> ghAsset = newRelease.getAssetFromRepo(repoName, modName, releaseID);

        //Assert
        assertThat(ghAsset).isNotPresent();
    }

}