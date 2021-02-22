package com.chandrakumar.ms.api.person;

import com.chandrakumar.ms.api.PersonAPIApplication;
import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@ExtendWith(SpringExtension.class)
//@SpringBootTest(
//        classes = PersonAPIApplication.class,
//        webEnvironment = SpringBootTest.
//                WebEnvironment.DEFINED_PORT, value = "server.port=3000"
//)
//@AutoConfigureMockMvc
//@ActiveProfiles("test")
public class PersonAPIIT {

    private static final String JSON = "json";
    private static final String PATH_NAME = "target";
    private static final String PROJECT_NAME = "Person API";
    private static final String TAG_IGNORE = "~@ignore";

    @Test
    public void testPerson() {

        Results results = Runner.path("classpath:feature/person")
                .tags(TAG_IGNORE)
                .parallel(1);
        generateReport(results.getReportDir());
        assertEquals(0, results.getFailCount(), results.getErrorMessages());
    }

    public static void generateReport(String karateOutputPath) {

        Collection<File> jsonFiles = FileUtils.listFiles(
                new File(karateOutputPath), new String[]{JSON}, true);
        List<String> jsonPaths = new ArrayList<>(jsonFiles.size());
        jsonFiles.forEach(file -> jsonPaths.add(file.getAbsolutePath()));
        Configuration config = new Configuration(new File(PATH_NAME), PROJECT_NAME);
        ReportBuilder reportBuilder = new ReportBuilder(jsonPaths, config);
        reportBuilder.generateReports();
    }
}