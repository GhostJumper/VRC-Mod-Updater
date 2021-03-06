package com.unreal.vrcmodupdater.fileandfolder;

import com.unreal.vrcmodupdater.exceptions.WrongFolderException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class WhereAmITest {


    private final WhereAmI whereAmI = new WhereAmI();
    private final String validPath = "D:\\Projects\\Coding\\Java\\vrc-mod-updater\\src\\main\\VRChat\\Mods";
    private final String invalidPath = "D:\\Projects\\Coding\\Java\\vrc-mod-updater\\src\\main\\VRChat\\Plugins";


    @Test
    public void getValidExecutionPath_valid() {

        // Arrange
        WhereAmI whereAmISpy = Mockito.spy(whereAmI);
        Mockito.doReturn(this.validPath).when(whereAmISpy).getPath();

        String result = null;


        // Act
        try {
            result = whereAmISpy.getValidExecutionPath();
        } catch (WrongFolderException e) {
            assertThat(e).doesNotThrowAnyException();
        }

        // Assert
        assertThat(result).isEqualTo(this.validPath);
    }

    @Test
    public void getValidExecutionPath_invalid() {

        // Arrange
        WhereAmI whereAmISpy = Mockito.spy(whereAmI);
        Mockito.doReturn(this.invalidPath).when(whereAmISpy).getPath();

        String result = null;


        // Act
        try {
            result = whereAmISpy.getValidExecutionPath();
        } catch (WrongFolderException e) {
            assertThat(e).isInstanceOf(WrongFolderException.class);
            assertThat(e).hasMessage(this.invalidPath + " is not a valid path");
        }

        // Assert
        assertThat(result).isNull();
    }

    @Test
    void getValidExecutionFolder_valid() {

        //Arrange
        WhereAmI whereAmISpy = Mockito.spy(whereAmI);
        Mockito.doReturn(this.validPath).when(whereAmISpy).getPath();

        File result = null;

        //Act
        try {
            result = whereAmISpy.getValidExecutionFolder();
        } catch (WrongFolderException e) {
            assertThat(e).doesNotThrowAnyException();
        }

        //Assert
        assertThat(result).isNotNull();
        assertThat(result.getAbsolutePath()).isEqualTo(this.validPath);
    }

    @Test
    void getValidExecutionFolder_invalid() {

        //Arrange
        WhereAmI whereAmISpy = Mockito.spy(whereAmI);
        Mockito.doReturn(this.invalidPath).when(whereAmISpy).getPath();

        File result = null;

        //Act
        try {
            result = whereAmISpy.getValidExecutionFolder();
        } catch (WrongFolderException e) {
            assertThat(e).isInstanceOf(WrongFolderException.class);
            assertThat(e).hasMessage(this.invalidPath + " is not a valid path");
        }

        //Assert
        assertThat(result).isNull();
    }
}