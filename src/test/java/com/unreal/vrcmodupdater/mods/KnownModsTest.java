package com.unreal.vrcmodupdater.mods;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class KnownModsTest {

    private final KnownMods knownMods = new KnownMods();
    private final String validDBPath = "src/test/resources/mods/mods_test.sqlite";
    private final String invalidDBPath = "src/test/resources/mods/mods_invalid.sqlite";


    @Test
    public void isDBValid_valid() {

        //Arrange
        KnownMods knownModsSpy = Mockito.spy(knownMods);
        Mockito.doReturn(this.validDBPath).when(knownModsSpy).getDBPath();

        //Act
        Boolean result = knownModsSpy.isDBValid();

        //Assert
        assertThat(result).isTrue();

    }

    @Test
    public void isDBValid_invalid() {

        //Arrange
        KnownMods knownModsSpy = Mockito.spy(knownMods);
        Mockito.doReturn(this.invalidDBPath).when(knownModsSpy).getDBPath();

        //Act
        Boolean result = knownModsSpy.isDBValid();

        //Assert
        assertThat(result).isFalse();

    }

    @Test
    public void getRepoUrlFromName_validModName() {

        //Arrange
        KnownMods knownModsSpy = Mockito.spy(knownMods);
        Mockito.doReturn(this.validDBPath).when(knownModsSpy).getDBPath();

        String modName = "JoinNotifier";
        String expectedPath = "knah/VRCMods/";

        //Act
        Optional<String> result = knownModsSpy.getRepoUrlFromName(modName);


        //Assert
        assertThat(result).isPresent();
        assertThat(result.get()).isNotNull();
        assertThat(result.get()).isEqualTo(expectedPath);

    }

    @Test
    public void getRepoUrlFromName_invalidModName_specialCharacters() {

        //Arrange
        KnownMods knownModsSpy = Mockito.spy(knownMods);
        Mockito.doReturn(this.validDBPath).when(knownModsSpy).getDBPath();

        String modName = "Join;:'Notifier/\\;";
        String expectedPath = "knah/VRCMods/";

        //Act
        Optional<String> result = knownModsSpy.getRepoUrlFromName(modName);


        //Assert
        assertThat(result).isPresent();
        assertThat(result.get()).isNotNull();
        assertThat(result.get()).isEqualTo(expectedPath);

    }

    @Test
    public void getRepoUrlFromName_invalidModName() {

        //Arrange
        KnownMods knownModsSpy = Mockito.spy(knownMods);
        Mockito.doReturn(this.validDBPath).when(knownModsSpy).getDBPath();

        String modName = "ThisDoesntExist";

        //Act
        Optional<String> result = knownModsSpy.getRepoUrlFromName(modName);


        //Assert
        assertThat(result).isNotPresent();

    }

    @Test
    public void getRepoUrlFromName_invalidDBPath() {

        //Arrange
        KnownMods knownModsSpy = Mockito.spy(knownMods);
        Mockito.doReturn(this.invalidDBPath).when(knownModsSpy).getDBPath();

        //Act
        Optional<String> result = knownModsSpy.getRepoUrlFromName("");


        //Assert
        assertThat(result).isNotPresent();

    }

    @Test
    public void groupModsByRepo_sameRepo() {

        //Arrange
        KnownMods knownModsSpy = Mockito.spy(knownMods);
        Mockito.doReturn(this.validDBPath).when(knownModsSpy).getDBPath();

        ArrayList<String> modNames = new ArrayList<>(Arrays.asList("FavCat-merged", "JoinNotifier", "EmojiPageButtons", "IKTweaks"));

        //Act
        ArrayList<ModCluster> result = knownModsSpy.groupModsByRepo(modNames);

        //Assert
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getModNames().toArray()).hasSize(4);
        assertThat(result.get(0).getModNames().toArray()).containsExactlyInAnyOrder(modNames.toArray());

    }

    @Test
    public void groupModsByRepo_differentRepos() {

        //Arrange
        KnownMods knownModsSpy = Mockito.spy(knownMods);
        Mockito.doReturn(this.validDBPath).when(knownModsSpy).getDBPath();

        ArrayList<String> modNames = new ArrayList<>(Arrays.asList("FavCat-merged", "JoinNotifier", "EmojiPageButtons", "AvatarStatsShowAuthor"));

        //Act
        ArrayList<ModCluster> result = knownModsSpy.groupModsByRepo(modNames);

        //Assert
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getModNames().toArray()).hasSize(3);
        assertThat(result.get(1).getModNames().toArray()).hasSize(1);
        assertThat(result.get(1).getModNames().toArray()).contains(modNames.get(3));

    }

    @Test
    public void groupModsByRepo_oneModNotFound() {

        //Arrange
        KnownMods knownModsSpy = Mockito.spy(knownMods);
        Mockito.doReturn(this.validDBPath).when(knownModsSpy).getDBPath();

        ArrayList<String> modNames = new ArrayList<>(Arrays.asList("FavCat-merged", "JoinNotifier", "EmojiPageButtons", "ThisDoesntExist"));

        //Act
        ArrayList<ModCluster> result = knownModsSpy.groupModsByRepo(modNames);

        //Assert
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getModNames().toArray()).hasSize(3);

    }

}