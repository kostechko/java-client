package io.appium.java_client.android;

import java.util.HashMap;
import java.util.List;

/**
 * Created by dolf on 10.11.15
 */
public interface CustomCommands {
    void stopApp();
    void replaceApp(String path);
    List<String> listFiles(String dir);
    void removeFile(String path);
    void broadcastReferrer(String pkg, String receiver, HashMap<String, Object> keys);
}
