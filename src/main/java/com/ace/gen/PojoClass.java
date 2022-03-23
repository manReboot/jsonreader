package com.ace.gen;

import com.ace.service.Start;
import com.sun.codemodel.JCodeModel;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsonschema2pojo.*;
import org.jsonschema2pojo.rules.RuleFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Supplier;

public class PojoClass {
    private static final Logger LOGGER = LogManager.getLogger(PojoClass.class);

    public static void main(String[] args) {
        try {
            URL sampleJson = ClassLoader.getSystemResource("Sample.json");
            File output = new File("E:\\Java Development\\ace\\src\\main\\java\\");
            new PojoClass().convertJsonToJavaClass(sampleJson, output, "com.ace.model", "Skeleton");
        } catch (Exception ex) {
           LOGGER.error(ex);
        }

    }

    public void convertJsonToJavaClass(URL inputJsonUrl, File outputJavaClassDirectory, String packageName, String javaClassName)
            throws IOException {
        JCodeModel jcodeModel = new JCodeModel();

        GenerationConfig config = new DefaultGenerationConfig() {
            @Override
            public boolean isGenerateBuilders() {
                return true;
            }

            @Override
            public SourceType getSourceType() {
                return SourceType.JSON;
            }
        };

        SchemaMapper mapper = new SchemaMapper(new RuleFactory(config, new Jackson2Annotator(config), new SchemaStore()), new SchemaGenerator());
        mapper.generate(jcodeModel, javaClassName, packageName, inputJsonUrl);

        jcodeModel.build(outputJavaClassDirectory);

    }
}
