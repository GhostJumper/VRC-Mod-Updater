package com.unreal.vrcmodupdater.fileandfolder;

import com.unreal.vrcmodupdater.exceptions.WrongFolderException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class WhereAmITest {


    private final WhereAmI whereAmI = new WhereAmI();


    @Test
    public void getValidExecutionPath_valid() {

        // Arrange
        WhereAmI whereAmISpy = Mockito.spy(whereAmI);
        String validPath = "D:\\Projects\\Coding\\Java\\vrc-mod-updater\\src\\main\\VRChat\\Mods";
        Mockito.doReturn(validPath).when(whereAmISpy).getPath();

        String result = null;


        // Act
        try {
            result = whereAmISpy.getValidExecutionPath();
        } catch (WrongFolderException e) {
            assertThat(e).doesNotThrowAnyException();
        }

        // Assert
        assertThat(result).isEqualTo(validPath);
    }

    @Test
    public void getValidExecutionPath_invalid() {

        // Arrange
        WhereAmI whereAmISpy = Mockito.spy(whereAmI);
        String invalidPath = "D:\\Projects\\Coding\\Java\\vrc-mod-updater\\src\\main\\VRChat\\Plugins";
        Mockito.doReturn(invalidPath).when(whereAmISpy).getPath();

        String result = null;


        // Act
        try {
            result = whereAmISpy.getValidExecutionPath();
        } catch (WrongFolderException e) {
            assertThat(e).isInstanceOf(WrongFolderException.class);
            assertThat(e).hasMessage("D:\\Projects\\Coding\\Java\\vrc-mod-updater\\src\\main\\VRChat\\Plugins is not a valid path");
        }

        // Assert
        assertThat(result).isNull();
    }

}