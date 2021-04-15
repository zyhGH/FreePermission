package com.zyh.processor;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.zyh.annotation.Permission;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;


@SupportedAnnotationTypes({"com.zyh.annotation.Permission"})
@AutoService(Processor.class)
public class PermissionProcessor extends AbstractProcessor {

    private Elements mElementsUtil;
    private Map<TypeElement, Set<Element>> mElems;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mElementsUtil = processingEnv.getElementUtils();
        mElems = new HashMap<>();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new HashSet<>();
        types.add(Permission.class.getCanonicalName());
        return types;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        initBindElems(roundEnv.getElementsAnnotatedWith(Permission.class));
        generateJavaClass();
        return true;
    }

    private void generateJavaClass() {
        for (TypeElement enclosedElem : mElems.keySet()) {
            MethodSpec.Builder methodSpecBuilder = MethodSpec.methodBuilder("permissionClick")
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .addParameter(ClassName.get(enclosedElem.asType()), "activity")
                    .returns(TypeName.VOID);
            for (Element bindElem : mElems.get(enclosedElem)) {
                String statement = String.format("activity.findViewById(%d).setOnClickListener(new android.view.View.OnClickListener() {" +
                        "\n @Override" +
                        "\n public void onClick(android.view.View v) {" +
                        "\n PermissionUtils.INSTANCE.checkHavePermission(activity, %s, new PermissionUtils.PermissionListener() {" +
                        "\n     @Override" +
                        "\n     public void onSuccess() {" +
                        "\n         activity.onClick();" +
                        "\n     }" +
                        "\n     @Override" +
                        "\n     public void onFail() {" +
                        "\n     }" +
                        "\n });" +
                        "\n }" +
                        "\n })",
                        bindElem.getAnnotation(Permission.class).value(),
                        bindElem.getAnnotation(Permission.class).type());
                methodSpecBuilder.addStatement(statement);
            }
            TypeSpec typeSpec = TypeSpec.classBuilder(enclosedElem.getSimpleName() + "_Permission")
                    .addModifiers(Modifier.PUBLIC)
                    .addMethod(methodSpecBuilder.build())
                    .build();
            JavaFile file = JavaFile.builder(getPackageName(enclosedElem), typeSpec).build();
            try {
                file.writeTo(processingEnv.getFiler());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void initBindElems(Set<? extends Element> bindElems) {
        for (Element bindElem : bindElems) {
            TypeElement enclosedElem = (TypeElement) bindElem.getEnclosingElement();
            Set<Element> elems = mElems.get(enclosedElem);
            if (elems == null) {
                elems = new HashSet<>();
                mElems.put(enclosedElem, elems);
            }
            elems.add(bindElem);
        }
    }

    private String getPackageName(TypeElement type) {
        return mElementsUtil.getPackageOf(type).getQualifiedName().toString();
    }
}
