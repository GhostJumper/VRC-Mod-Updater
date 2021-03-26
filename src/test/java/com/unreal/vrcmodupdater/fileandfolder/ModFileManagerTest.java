package com.unreal.vrcmodupdater.fileandfolder;

import com.unreal.vrcmodupdater.github.NewRelease;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.kohsuke.github.GHAsset;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ModFileManagerTest {

    ModFileManager modFileManager = new ModFileManager();

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
    public void getInstalledMods_modsPresent() {

        //Arrange
        File modFolder = new File("src/test/resources/fileandfolder/ModFileManager/mods");
        String[] expected = {"AvatarStatsShowAuthor", "ComfyVRMenu", "DownloadFix", "emmVRCLoader", "JoinNotifier", "RememberMe", "UserInfoExtentions"};

        //Act
        Optional<ArrayList<String>> result = modFileManager.getAllModNames(modFolder);

        //Assert
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().toArray()).containsExactlyInAnyOrder(expected);
    }

    @Test
    public void getInstalledMods_NoModsPresent() {

        //Arrange
        File modFolder = new File("src/test/resources/fileandfolder/ModFileManager/mods_empty");

        //Act
        Optional<ArrayList<String>> result = modFileManager.getAllModNames(modFolder);

        //Assert
        assertThat(result.isPresent()).isFalse();
    }

    @Test
    public void getMD5Hash_validFile() {
        //Arrange
        String expectedHash = "537a4af1ce66a9faa2830b39d04236fe";
        File file = new File("src/test/resources/fileandfolder/ModFileManager/download/old_state_used_to_reset/IKTweaks.dll");
        //Act
        Optional<String> result = modFileManager.getMD5Hash(file);

        //Assert
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(expectedHash);
    }

    @Test
    public void getMD5Hash_invalidFile() {

        File file = new File("src/test/resources/fileandfolder/ModFileManager/download/old_state_used_to_reset/invalid.dll");
        //Act
        Optional<String> result = modFileManager.getMD5Hash(file);

        //Assert
        assertThat(result).isNotPresent();
    }

    @Test
    public void downloadFileFromAsset_IKTweaks() {

        //Arrange
        clearModsFolder();

        String modName = "IKTweaks.dll";
        String repoName = "knah/VRCMods";
        Long releaseID = 40254840l;
        String expectedHash = "50e45c44bc48d2562bb2aa3312ee756b";

        String path = "src/test/resources/fileandfolder/ModFileManager/download/mod_folder";

        File file = new File(path + "/" + modName);

        Optional<GHAsset> ghAsset = newRelease.getAssetFromRepo(repoName, modName, releaseID);

        //Act
        Boolean result = modFileManager.downloadFileFromAsset(ghAsset.get(), path);

        //Assert
        assertThat(ghAsset).isPresent();
        assertThat(ghAsset.get()).isNotNull();
        assertThat(result).isTrue();
        assertThat(file).isFile();
        assertThat(modFileManager.getMD5Hash(file).get()).isEqualTo(expectedHash);

        //Cleanup
        clearModsFolder();

    }

    @Test
    public void downloadFileFromAsset_ComfyVRMenu() {

        //Arrange
        clearModsFolder();

        String modName = "ComfyVRMenu.dll";
        String repoName = "M-oons/VRChat-Mods";
        Long releaseID = 37263796l;
        String expectedHash = "a07243e7d89bd9dbd597c588efafd846";

        String path = "src/test/resources/fileandfolder/ModFileManager/download/mod_folder";

        File file = new File(path + "/" + modName);

        Optional<GHAsset> ghAsset = newRelease.getAssetFromRepo(repoName, modName, releaseID);

        //Act
        Boolean result = modFileManager.downloadFileFromAsset(ghAsset.get(), path);

        //Assert
        assertThat(ghAsset).isPresent();
        assertThat(ghAsset.get()).isNotNull();
        assertThat(result).isTrue();
        assertThat(file).isFile();
        assertThat(modFileManager.getMD5Hash(file).get()).isEqualTo(expectedHash);

        //Cleanup
        clearModsFolder();
    }

    @Test
    public void downloadIntegrityTest() {

        //Arrange
        copyTestModsInModFolder();

        String path = "src/test/resources/fileandfolder/ModFileManager/download/mod_folder";

        String modName1 = "ComfyVRMenu.dll";
        String repoName1 = "M-oons/VRChat-Mods";
        Long releaseID1 = 37263796l;

        String modName2 = "IKTweaks.dll";
        String repoName2 = "knah/VRCMods";
        Long releaseID2 = 40254840l;

        String oldHash1 = "658021876fe2f6ceecba6b741bff0da7";
        String oldHash2 = "537a4af1ce66a9faa2830b39d04236fe";

        String newHash1 = "a07243e7d89bd9dbd597c588efafd846";
        String newHash2 = "50e45c44bc48d2562bb2aa3312ee756b";

        File file1 = new File(path + "/" + modName1);
        File file2 = new File(path + "/" + modName2);

        assertThat(modFileManager.getMD5Hash(file1).get()).isEqualTo(oldHash1);
        assertThat(modFileManager.getMD5Hash(file2).get()).isEqualTo(oldHash2);

        Optional<GHAsset> ghAsset1 = newRelease.getAssetFromRepo(repoName1, modName1, releaseID1);
        Optional<GHAsset> ghAsset2 = newRelease.getAssetFromRepo(repoName2, modName2, releaseID2);

        //Act
        Boolean result1 = modFileManager.downloadFileFromAsset(ghAsset1.get(), path);
        Boolean result2 = modFileManager.downloadFileFromAsset(ghAsset2.get(), path);

        //Assert
        assertThat(result1).isTrue();
        assertThat(result2).isTrue();
        assertThat(modFileManager.getMD5Hash(file1).get()).isEqualTo(newHash1);
        assertThat(modFileManager.getMD5Hash(file2).get()).isEqualTo(newHash2);

        //Cleanup
        clearModsFolder();


    }


    private void copyTestModsInModFolder() {
        File[] originalFiles = {
                new File("src/test/resources/fileandfolder/ModFileManager/download/old_state_used_to_reset/ComfyVRMenu.dll"),
                new File("src/test/resources/fileandfolder/ModFileManager/download/old_state_used_to_reset/IKTweaks.dll")
        };

        File[] destinationFiles = {
                new File("src/test/resources/fileandfolder/ModFileManager/download/mod_folder/ComfyVRMenu.dll"),
                new File("src/test/resources/fileandfolder/ModFileManager/download/mod_folder/IKTweaks.dll")
        };


        try {

            for (int i = 0; i < originalFiles.length; i++) {
                Files.copy(originalFiles[i].toPath(), destinationFiles[i].toPath(), StandardCopyOption.REPLACE_EXISTING);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void clearModsFolder() {
        try {
            FileUtils.cleanDirectory(new File("src/test/resources/fileandfolder/ModFileManager/download/mod_folder/"));
            new File("src/test/resources/fileandfolder/ModFileManager/download/mod_folder/filler").createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}