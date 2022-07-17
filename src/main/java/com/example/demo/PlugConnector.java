package com.example.demo;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;


import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarFile;

public class PlugConnector extends Application {
    static HashSet<PluginModel> plugins = new HashSet<>();

    @Override
    public void start(Stage myStage) throws Exception {
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
                plugin.initialize();
                loadedPluginModels.getChildren().add(new Label(plugin.name()));
            });
        }
        Rectangle2D screenbounds = Screen.getPrimary().getVisualBounds();
        Scene scene = new Scene(loadedPluginModels, screenbounds.getWidth() / 2, screenbounds.getHeight() / 2);
        myStage.setScene(scene);
        myStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


