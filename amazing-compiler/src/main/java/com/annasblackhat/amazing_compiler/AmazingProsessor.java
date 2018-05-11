package com.annasblackhat.amazing_compiler;

import com.annasblackhat.amazing_annotation.AmazingCreate;
import com.google.auto.service.AutoService;
import com.google.common.collect.ImmutableList;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import java.io.IOException;
import java.lang.invoke.MethodType;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;

/**
 * Created by annasblackhat on 11/05/18
 */

@AutoService(Processor.class)
public class AmazingProsessor extends AbstractProcessor {

    private Messager messager;
    private Filer filer;


    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        messager = processingEnvironment.getMessager();
        filer = processingEnvironment.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        Collection<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(AmazingCreate.class);

        for(Element e: elements){
            writeMagic((TypeElement) e);
        }
        return false;
    }

    private void writeMagic(TypeElement e) {
        //memastikan library Log nye ke import
        ClassName log = ClassName.get("android.util", "Log");

        TypeVariableName className = TypeVariableName.get(e.getSimpleName().toString());

        MethodSpec methodSpec = MethodSpec.methodBuilder("Log")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addParameter(className, "log")
                .returns(void.class)
                .addStatement("$T.d($S, $L)", log, e.getSimpleName().toString(), createString(e))
                .build();

        TypeSpec typeSpec = TypeSpec.classBuilder(e.getSimpleName().toString()+"Log")
                .addMethod(methodSpec)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .build();

        JavaFile javaFile = JavaFile.builder(e.getEnclosingElement().toString(), typeSpec)
                .build();

        try {
            javaFile.writeTo(filer);
        } catch (IOException e1) {
            messager.printMessage(Diagnostic.Kind.ERROR, "Write to filer error "+e1);
        }
    }

    private String createString(TypeElement e) {
        List<VariableElement> fields = new ImmutableList.Builder<VariableElement>().addAll(ElementFilter.fieldsIn(e.getEnclosedElements())).build();
        String creator = "String.format(\"";

        for(VariableElement field: fields){
            creator += field.getSimpleName() + " - %s, ";
        }

        creator += "\"";
        for(VariableElement field: fields){
            creator += ", log."+field.getSimpleName();
        }

        creator += ")";
        return creator;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new HashSet<>();
        set.add(AmazingCreate.class.getCanonicalName());
        return set;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}

/*Book

class BookLog{
    public static void Log(Book log){
        Log.d("book, " + String.format("isbn - %s, title - %", log.isbn, log.title))
    }
}
* */
