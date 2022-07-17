package com.example.demo;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarFile;

public class PlugConnector {
    public static ArrayList<URLClassLoader> classLoader = new ArrayList<>();
    public static boolean isLaunched = false ;
    static HashSet<PluginModel> plugins = new HashSet<>();

    public static void start() throws Exception {
        String  logs = "Starting plugConnector : ";
        String myPluginPath = "C:/Users/Moustapha Diarra/Documents/Ecole/3AL1/Projet ANNUEL/ClientLourdJVFX/manWorker/src/main/java/com/example/demo/plugins" ;
        File pluginDirectory = new File(myPluginPath);
        if (!pluginDirectory.exists()) pluginDirectory.mkdir();
        File[] files = pluginDirectory.listFiles((dir, name) -> name.endsWith(".jar"));
        VBox loadedPluginModels = new VBox(6);
        loadedPluginModels.setAlignment(Pos.CENTER);
        if (files != null && files.length > 0) {
            ArrayList<String> classes = new ArrayList<>();
            ArrayList<URL> urls = new ArrayList<>(files.length);
            for (File file : files) {
                JarFile jar = new JarFile(file);
                jar.stream().forEach(jarEntry -> {
                    if (jarEntry.getName().endsWith(".class")) {
                        classes.add(jarEntry.getName());
                    }
                });
                URL url = file.toURI().toURL();
                urls.add(url);
            }
            URLClassLoader urlClassLoader = new URLClassLoader(urls.toArray(new URL[urls.size()]));
            classes.remove(4);
            classLoader.add(urlClassLoader) ;
            //classes.remove(4);
            //System.out.println("Ensemble des classes"+classes+"\n\n");

            classes.forEach(className -> {
                //System.out.println(className);
                try {
                    Class cls = urlClassLoader.loadClass(className.replaceAll("/", ".").replace(".class", ""));
                    Class[] interfaces = cls.getInterfaces();
                    for (Class intface : interfaces) {
                        if (intface.equals(PluginModel.class)) {
                            PluginModel plugin = (PluginModel) cls.newInstance();
                            plugins.add(plugin);
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            if (!plugins.isEmpty()) loadedPluginModels.getChildren().add(new Label("Loaded plugins:"));
            plugins.forEach(plugin -> {
                try {
                    plugin.initialize();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                loadedPluginModels.getChildren().add(new Label(plugin.name()));
            });
            isLaunched = true ;
        }
        if(isLaunched){
            logs = logs + "success";
            Logs.writeLogs(logs);
        }else{
            logs = logs + "failed due to unloaded plugin" ;
            Logs.writeLogs(logs);
        }
    }
    public static void close() {
        String logs = "Closing pluginConnector : ";
        try{
            if(isLaunched){
                for(URLClassLoader url : classLoader){
                    url.close() ;
                }
                isLaunched = false ;
            }
            logs = logs + "success";
            Logs.writeLogs(logs);
        }catch (Exception e){
                e.printStackTrace();
            logs = logs + "failed " + e;
            Logs.writeLogs(logs);
        }
    }

}


