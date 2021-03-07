package com.unreal.vrcmodupdater.mods;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.FileNotFoundException;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class KnownModsTest {

    private final KnownMods knownMods = new KnownMods();
    private final String validDBPath = "src\\test\\resources\\mods\\mods_test.sqlite";
    private final String invalidDBPath = "src\\test\\resources\\mods\\mods_invalid.sqlite";


    @Test
    public void isDBValid_valid() {

        //Arrange
        KnownMods knownModsSpy = Mockito.spy(knownMods);
        Mockito.doReturn(validDBPath).when(knownModsSpy).getDBPath();

        //Act
        Boolean result = knownModsSpy.isDBValid();

        //Assert
        assertThat(result).isTrue();

    }

    @Test
    public void isDBValid_invalid() {

        //Arrange
        KnownMods knownModsSpy = Mockito.spy(knownMods);
        Mockito.doReturn(invalidDBPath).when(knownModsSpy).getDBPath();

        //Act
        Boolean result = knownModsSpy.isDBValid();

        //Assert
        assertThat(result).isFalse();

    }

    @Test
    public void getDownloadRepoFromName_validModName() {

        //Arrange
        KnownMods knownModsSpy = Mockito.spy(knownMods);
        Mockito.doReturn("src\\test\\resources\\mods\\mods_test.sqlite").when(knownModsSpy).getDBPath();
        Exception exception = null;

        String modName = "JoinNotifier";
        String expectedPath = "https://github.com/knah/VRCMods/";
        Optional<String> result = null;

        //Act
        try {
            result = knownModsSpy.getDownloadRepoFromName(modName);
        } catch (Exception e) {
            exception = e;
        }

        //Assert
        assertThat(exception).doesNotThrowAnyException();
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(expectedPath);

    }

    @Test
    public void getDownloadRepoFromName_invalidModName() {

        //Arrange
        KnownMods knownModsSpy = Mockito.spy(knownMods);
        Mockito.doReturn("src\\test\\resources\\mods\\mods_test.sqlite").when(knownModsSpy).getDBPath();
        Exception exception = null;

        String modName = "ThisDoesntExist";

        //Act
        Optional<String> result = null;
        try {
            result = knownModsSpy.getDownloadRepoFromName(modName);
        } catch (Exception e) {
            exception = e;
        }

        //Assert
        assertThat(exception).doesNotThrowAnyException();
        assertThat(result).isNotPresent();

    }

    @Test
    public void getDownloadRepoFromName_invalidDBPath() {

        //Arrange
        String invalidDBPath = "src\\test\\resources\\mods\\mods_invalid.sqlite";
        KnownMods knownModsSpy = Mockito.spy(knownMods);
        Mockito.doReturn(invalidDBPath).when(knownModsSpy).getDBPath();
        Exception exception = null;

        //Act
        try {
            knownModsSpy.getDownloadRepoFromName("");
        } catch (Exception e) {
            exception = e;
        }

        //Assert
        assertThat(exception).isInstanceOf(FileNotFoundException.class);
        assertThat(exception).hasMessage(invalidDBPath + " is not a valid DB path");

    }

}