package org.jush;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HelloWorldGenerator {
    private final Path directory;

    public HelloWorldGenerator(Path path) {
        this.directory = path;
    }

    public static void main(String[] args) throws IOException {
        new HelloWorldGenerator(Paths.get(args[0])).generate();
        System.out.println("Main called");
    }

    void generate() throws IOException {
        MethodSpec main = MethodSpec.methodBuilder("hello")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(void.class)
                .addStatement("$T.out.println($S)", System.class, "Hello, JavaPoet!")
                .build();

        TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(main)
                .build();

        JavaFile javaFile = JavaFile.builder("com.example.helloworld", helloWorld)
                .build();

        javaFile.writeTo(directory);
    }
}
