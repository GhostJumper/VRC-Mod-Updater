package com.unreal.vrcmodupdater.mods;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class KnownModsTest {

    private final KnownMods knownMods = new KnownMods();

    HashMap<String, String> modMap = new HashMap<>();

    @BeforeEach
    public void beforeEach() {
        modMap.put("FavCat-merged", "knah/VRCMods/");
        modMap.put("JoinNotifier", "knah/VRCMods/");
        modMap.put("EmojiPageButtons", "knah/VRCMods/");
        modMap.put("IKTweaks", "knah/VRCMods/");
        modMap.put("AdvancedSafety", "knah/VRCMods/");
        modMap.put("DownloadFix", "gompocp/DownloadFix/");
        modMap.put("ComfyVRMenu", "M-oons/VRChat-Mods/");
        modMap.put("AntiUdonExploits", "M-oons/VRChat-Mods/");
        modMap.put("AvatarStatsShowAuthor", "HerpDerpinstine/AvatarStatsShowAuthor/");
    }


    @Test
    public void getRepoUrlFromName_validModName() {

        //Arrange
        KnownMods knownModsSpy = Mockito.spy(knownMods);
        Mockito.doReturn(this.modMap).when(knownModsSpy).getModMap();

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
        Mockito.doReturn(this.modMap).when(knownModsSpy).getModMap();

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
        Mockito.doReturn(this.modMap).when(knownModsSpy).getModMap();

        String modName = "ThisDoesntExist";

        //Act
        Optional<String> result = knownModsSpy.getRepoUrlFromName(modName);


        //Assert
        assertThat(result).isNotPresent();

    }

    @Test
    public void groupModsByRepo_sameRepo() {

        //Arrange
        KnownMods knownModsSpy = Mockito.spy(knownMods);
        Mockito.doReturn(this.modMap).when(knownModsSpy).getModMap();

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
        Mockito.doReturn(this.modMap).when(knownModsSpy).getModMap();

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
        Mockito.doReturn(this.modMap).when(knownModsSpy).getModMap();

        ArrayList<String> modNames = new ArrayList<>(Arrays.asList("FavCat-merged", "JoinNotifier", "EmojiPageButtons", "ThisDoesntExist"));

        //Act
        ArrayList<ModCluster> result = knownModsSpy.groupModsByRepo(modNames);

        //Assert
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getModNames().toArray()).hasSize(3);

    }

}