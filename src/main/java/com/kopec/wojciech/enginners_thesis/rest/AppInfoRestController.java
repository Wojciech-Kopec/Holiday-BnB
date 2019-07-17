package com.kopec.wojciech.enginners_thesis.rest;

import com.google.common.base.Strings;
import com.kopec.wojciech.enginners_thesis.EngineersThesisApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

@RestController
@RequestMapping("/about")
public class AppInfoRestController {

    @Autowired
    private BuildProperties buildProperties;

    private static final Logger logger = LoggerFactory.getLogger(AppInfoRestController.class);

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> get() {
//        Manifest appManifest = getAppManifest();
//        String manifest;
//        if (appManifest != null)
//            manifest = parseManifest(appManifest);
//        else
//            manifest = "Manifest not found!";

        String manifest = "";
        manifest += "\n" + buildProperties.getArtifact();
        manifest += "\n" + buildProperties.getGroup();
        manifest += "\n" + buildProperties.getName();
        manifest += "\n" + buildProperties.getTime();
        manifest += "\n" + buildProperties.getVersion();
        manifest += "\n" + buildProperties.get("build.time");

        return new ResponseEntity<>(manifest, HttpStatus.OK);
    }

    public static String parseManifest(Manifest manifest) {
        Set<Map.Entry<Object, Object>> entries = manifest.getMainAttributes().entrySet();
        StringBuilder sb = new StringBuilder();
        entries.forEach(entry -> sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n"));
        return sb.toString();
    }

    private static Manifest getAppManifest() {
        logger.info("Finding proper Manifest file . . .");
        Manifest manifest = null;

        try {
            Enumeration<URL> resources = EngineersThesisApplication.class.getClassLoader()
                    .getResources("META-INF/MANIFEST.MF");
            while (resources.hasMoreElements()) {
                URL url = resources.nextElement();
                logger.info("Cheking file at path: " + url.getPath());
                Manifest aManifest = new Manifest(url.openStream());

                Attributes mainAttributes = aManifest.getMainAttributes();
                String attr = "Implementation-Title";
                String valueCheck = Strings.nullToEmpty(mainAttributes.getValue(attr));
                logger.info("Manifest " + attr + " attr.: " + valueCheck);
                if (valueCheck.contains("engineers_thesis")) {
                    logger.info("Manifest found!");
                    manifest = aManifest;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (manifest == null) {
            logger.info("Manifest NOT found!");

        }
        return manifest;
    }
}



