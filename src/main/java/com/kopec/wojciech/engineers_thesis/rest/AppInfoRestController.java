package com.kopec.wojciech.engineers_thesis.rest;

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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/about")
public class AppInfoRestController {

    @Autowired
    private BuildProperties buildProperties;

    private static final Logger logger = LoggerFactory.getLogger(AppInfoRestController.class);

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> get() {
        String manifest = "Application Manifest\n\n";
        manifest += "Artifact: " + buildProperties.getArtifact();
        manifest += "\nGroup: " + buildProperties.getGroup();
        manifest += "\nName: " + buildProperties.getName();
        manifest += "\nVersion: " + buildProperties.getVersion();
        manifest += "\nBuild Time: " + parseDateTime(buildProperties.getTime().toString());
        manifest += "\n\n\nBy Wojciech Kopeć 2019";

        return new ResponseEntity<>(manifest, HttpStatus.OK);
    }

    private String parseDateTime(String dateTime) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, dtf);
        localDateTime = localDateTime.plusHours(2); //Timezone issue
        dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        dateTime = localDateTime.format(dtf);
        return dateTime;
    }
}



