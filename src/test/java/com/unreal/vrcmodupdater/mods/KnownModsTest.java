package com.unreal.vrcmodupdater.mods;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.FileNotFoundException;
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
    public void getDownloadUrlFromName_validModName() {

        //Arrange
        KnownMods knownModsSpy = Mockito.spy(knownMods);
        Mockito.doReturn(this.validDBPath).when(knownModsSpy).getDBPath();
        Exception exception = null;

        String modName = "JoinNotifier";
        String expectedPath = "https://github.com/knah/VRCMods/";
        Optional<String> result = Optional.empty();

        //Act
        try {
            result = knownModsSpy.getDownloadUrlFromName(modName);
        } catch (Exception e) {
            exception = e;
        }

        //Assert
        assertThat(exception).doesNotThrowAnyException();
        assertThat(result).isPresent();
        assertThat(result.get()).isNotNull();
        assertThat(result.get()).isEqualTo(expectedPath);

    }

    @Test
    public void getDownloadUrlFromName_invalidModName() {

        //Arrange
        KnownMods knownModsSpy = Mockito.spy(knownMods);
        Mockito.doReturn(this.validDBPath).when(knownModsSpy).getDBPath();
        Exception exception = null;

        String modName = "ThisDoesntExist";

        //Act
        Optional<String> result = Optional.empty();
        try {
            result = knownModsSpy.getDownloadUrlFromName(modName);
        } catch (Exception e) {
            exception = e;
        }

        //Assert
        assertThat(exception).doesNotThrowAnyException();
        assertThat(result).isNotPresent();

    }

    @Test
    public void getDownloadUrlFromName_invalidDBPath() {

        //Arrange
        KnownMods knownModsSpy = Mockito.spy(knownMods);
        Mockito.doReturn(this.invalidDBPath).when(knownModsSpy).getDBPath();
        Exception exception = null;

        //Act
        try {
            knownModsSpy.getDownloadUrlFromName("");
        } catch (Exception e) {
            exception = e;
        }

        //Assert
        assertThat(exception).isInstanceOf(FileNotFoundException.class);
        assertThat(exception).hasMessage(invalidDBPath + " is not a valid DB path");

    }

}