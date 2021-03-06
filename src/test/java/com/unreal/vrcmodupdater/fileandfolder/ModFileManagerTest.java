package com.unreal.vrcmodupdater.fileandfolder;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ModFileManagerTest {

    ModFileManager modFileManager = new ModFileManager();

    @Test
    public void getInstalledMods_modsPresent() {

        //Arrange
        File modFolder = new File("src/test/resources/example_mods");
        String[] expected = {"AvatarStatsShowAuthor.dll", "ComfyVRMenu.dll", "DownloadFix.dll", "emmVRCLoader.dll", "JoinNotifier.dll", "RememberMe.dll", "UserInfoExtentions.dll"};

        //Act
        Optional<ArrayList<String>> result = modFileManager.getAllModNames(modFolder);

        //Assert
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().toArray()).containsExactly(expected);
    }

    @Test
    public void getInstalledMods_NoModsPresent() {

        //Arrange
        File modFolder = new File("src/test/resources/example_mods_empty");

        //Act
        Optional<ArrayList<String>> result = modFileManager.getAllModNames(modFolder);

        //Assert
        assertThat(result.isPresent()).isFalse();
    }

}