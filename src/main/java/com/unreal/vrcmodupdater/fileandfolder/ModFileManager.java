package com.unreal.vrcmodupdater.fileandfolder;

import org.kohsuke.github.GHAsset;

import java.io.*;
import java.net.URL;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Optional;

public class ModFileManager {

    public Optional<ArrayList<String>> getAllModNames(File folder) {
        ArrayList<String> result = new ArrayList<>();
        if (folder.listFiles() != null)
            for (File file : folder.listFiles()) {
                if (file.getName().endsWith(".dll"))
                    result.add(file.getName().replace(".dll", ""));
            }

        return (result.size() > 0 ? Optional.of(result) : Optional.empty());
    }

    public Optional<String> getMD5Hash(File file) {

        try {

            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            FileInputStream fis = new FileInputStream(file);

            byte[] byteArray = new byte[1024];
            int bytesCount = 0;

            while ((bytesCount = fis.read(byteArray)) != -1) {
                messageDigest.update(byteArray, 0, bytesCount);
            }

            fis.close();

            byte[] bytes = messageDigest.digest();

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            return Optional.of(sb.toString());
        } catch (Exception e) {
            return Optional.empty();
        }

    }


    public boolean downloadFileFromAsset(GHAsset ghAsset, String path) {

        if (ghAsset == null) return false;

        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new URL(ghAsset.getBrowserDownloadUrl()).openStream());
            FileOutputStream fileOutputStream = new FileOutputStream(path + "/" + ghAsset.getName());

            byte data[] = new byte[1024];
            int byteContent;

            while ((byteContent = bufferedInputStream.read(data, 0, 1024)) != -1) {
                fileOutputStream.write(data, 0, byteContent);
            }
            fileOutputStream.close();
            bufferedInputStream.close();

            System.out.println("Downloaded: " + ghAsset.getName());

            return true;

        } catch (IOException e) {
            return false;
        }

    }
}
