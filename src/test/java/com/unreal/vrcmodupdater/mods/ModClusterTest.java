package com.unreal.vrcmodupdater.mods;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ModClusterTest {

    @Test
    public void addUrlToClusters_addToExistingCluster() {
        //Arrange
        ModCluster modCluster1 = new ModCluster("url1", "mod1", "mod2");
        ModCluster modCluster2 = new ModCluster("url2", "mod3", "mod4", "mod5");

        ArrayList<ModCluster> modClusters = new ArrayList<>(Arrays.asList(modCluster1, modCluster2));

        String urlForAdding = "url1";
        String modNameForAdding = "mod6";


        //Act
        modClusters = ModCluster.addUrlToClusters(modClusters, urlForAdding, modNameForAdding);

        //Assert
        assertThat(modClusters).isNotNull();
        assertThat(modClusters.size()).isEqualTo(2);
        assertThat(modClusters.get(0).getModNames().contains(modNameForAdding)).isTrue();
        assertThat(modClusters.get(1).getModNames().contains(modNameForAdding)).isFalse();
    }

    @Test
    public void addUrlToClusters_addToNewCluster() {
        //Arrange
        ModCluster modCluster1 = new ModCluster("url1", "mod1", "mod2");
        ModCluster modCluster2 = new ModCluster("url2", "mod3", "mod4", "mod5");

        ArrayList<ModCluster> modClusters = new ArrayList<>(Arrays.asList(modCluster1, modCluster2));

        String urlForAdding = "url3";
        String modNameForAdding = "mod6";


        //Act
        modClusters = ModCluster.addUrlToClusters(modClusters, urlForAdding, modNameForAdding);

        //Assert
        assertThat(modClusters).isNotNull();
        assertThat(modClusters.size()).isEqualTo(3);
        assertThat(modClusters.get(0).getModNames().contains(modNameForAdding)).isFalse();
        assertThat(modClusters.get(1).getModNames().contains(modNameForAdding)).isFalse();
        assertThat(modClusters.get(2).getModNames().contains(modNameForAdding));
    }


}
