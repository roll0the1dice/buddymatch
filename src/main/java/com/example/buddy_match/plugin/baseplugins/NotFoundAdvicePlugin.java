package com.example.buddy_match.plugin.baseplugins;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.DefaultJavaFormatter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;

public class NotFoundAdvicePlugin extends PluginAdapter {
    private String targetPackage;

    private Properties properties;

    public NotFoundAdvicePlugin(Properties properties) {
        super();
        this.properties = properties;
    }

    public NotFoundAdvicePlugin() {
        super();
    }

    @Override
    public boolean validate(List<String> warnings) {
        // 在此验证插件的配置是否合法，返回true表示插件配置有效
        String myCustomParameter = properties.getProperty("targetPackage");
        if (myCustomParameter != null) 
            this.targetPackage = myCustomParameter;
        else 
            return false;
        return true;
    }


    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        // List to store additional generated files
        List<GeneratedJavaFile> additionalFiles = new ArrayList<>();

        // 设置接口类的包名
        String packageName = this.targetPackage;

        // 创建接口类（例如 UserMapper）
        // 创建接口类（例如 UserMapper）
        String modelClassName = introspectedTable.getBaseRecordType();

        String[] strList = modelClassName.split("\\.");
        String _modelName = Arrays.asList(strList).get(strList.length - 1);
        // String interfaceName = introspectedTable.getFullyQualifiedTable().getDomainObjectName() + modelClassName;
        // System.out.println(packageName);
        // System.out.println(modelClassName);
        TopLevelClass topLevelClass = new TopLevelClass(packageName + "." + _modelName + "NotFoundAdvice");
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);

        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.http.HttpStatus"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.web.bind.annotation.ExceptionHandler"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.web.bind.annotation.ResponseStatus"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.web.bind.annotation.RestControllerAdvice"));


        // Add class documentation
        topLevelClass.addJavaDocLine("/**");
        topLevelClass.addJavaDocLine(" * This is a generated NotFoundException for demonstration purposes.");
        topLevelClass.addJavaDocLine(" */");

        topLevelClass.addAnnotation("@RestControllerAdvice");

        Method _notFoundHandler = new Method(_modelName + "NotFoundHandler");
        _notFoundHandler.addAnnotation(String.format("@ExceptionHandler(%s.class)", _modelName+"NotFoundException"));
        _notFoundHandler.addAnnotation("@ResponseStatus(HttpStatus.NOT_FOUND)");
        _notFoundHandler.setVisibility(JavaVisibility.PUBLIC);
        Parameter parameter  = new Parameter(new FullyQualifiedJavaType(_modelName+"NotFoundException"), "ex");
        _notFoundHandler.addParameter(parameter);
        _notFoundHandler.setReturnType(new FullyQualifiedJavaType("java.lang.String"));
        _notFoundHandler.addBodyLine("return ex.getMessage();");
        topLevelClass.addMethod(_notFoundHandler);


        // Use DefaultJavaFormatter to format the generated Java file
        DefaultJavaFormatter javaFormatter = new DefaultJavaFormatter();
        GeneratedJavaFile generatedJavaFile = new GeneratedJavaFile(topLevelClass, "src/main/java", javaFormatter);

        // Add the generated file to the list
        additionalFiles.add(generatedJavaFile);

        return additionalFiles;
    }
}
