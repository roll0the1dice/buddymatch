package com.example.buddy_match.plugin.baseplugins;

import java.util.ArrayList;
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

public class GlobalExceptionAdvicePlugin extends PluginAdapter {
    private String targetPackage;

    private Properties properties;

    public GlobalExceptionAdvicePlugin(Properties properties) {
        super();
        this.properties = properties;
    }

    public GlobalExceptionAdvicePlugin() {
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
        // String modelClassName = introspectedTable.getBaseRecordType();

        // String[] strList = modelClassName.split("\\.");
        // String _modelName = Arrays.asList(strList).get(strList.length - 1);
        // String interfaceName = introspectedTable.getFullyQualifiedTable().getDomainObjectName() + modelClassName;
        // System.out.println(packageName);
        // System.out.println(modelClassName);
        TopLevelClass topLevelClass = new TopLevelClass(packageName + "."  + "GlobalExceptionAdvice");
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);

        topLevelClass.addImportedType(new FullyQualifiedJavaType("java.lang.String"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.http.HttpStatus"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.web.bind.annotation.ExceptionHandler"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.web.bind.annotation.ResponseStatus"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.web.bind.annotation.RestControllerAdvice"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.http.ResponseEntity"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.http.converter.HttpMessageNotReadableException"));

        // Add class documentation
        topLevelClass.addJavaDocLine("/**");
        topLevelClass.addJavaDocLine(" * This is a generated NotFoundException for demonstration purposes.");
        topLevelClass.addJavaDocLine(" */");

        topLevelClass.addAnnotation("@RestControllerAdvice");

        Method _notFoundHandler = new Method("GlobalExceptionAdvice");
        FullyQualifiedJavaType _internalServerType =  new FullyQualifiedJavaType(packageName + "." + "InternalServerException");
        _notFoundHandler.addAnnotation(String.format("@ExceptionHandler(%s.class)", _internalServerType.getFullyQualifiedName()));
        _notFoundHandler.addAnnotation("@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)");
        _notFoundHandler.setVisibility(JavaVisibility.PUBLIC);
        Parameter parameter  = new Parameter(_internalServerType, "ex");
        _notFoundHandler.addParameter(parameter);
        FullyQualifiedJavaType _retType = new FullyQualifiedJavaType("org.springframework.http.ResponseEntity");
        _retType.addTypeArgument(new FullyQualifiedJavaType("?"));
        _notFoundHandler.setReturnType(_retType);
        _notFoundHandler.addBodyLine("return ResponseEntity.status(500).body(\"Internal Server Error: \" + ex.getMessage());");
        topLevelClass.addMethod(_notFoundHandler);

        Method _invalidInputHandler = new Method("GlobalExceptionAdvice");
        FullyQualifiedJavaType _invalidInputType =  new FullyQualifiedJavaType("org.springframework.http.converter.HttpMessageNotReadableException");
        _invalidInputHandler.addAnnotation(String.format("@ExceptionHandler(%s.class)", _invalidInputType.getFullyQualifiedName()));
        _invalidInputHandler.addAnnotation("@ResponseStatus(HttpStatus.BAD_REQUEST)");
        _invalidInputHandler.setVisibility(JavaVisibility.PUBLIC);
        parameter  = new Parameter(_invalidInputType, "ex");
        _invalidInputHandler.addParameter(parameter);
        _invalidInputHandler.setReturnType(_retType);
        _invalidInputHandler.addBodyLine("return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(\"Invalid Input Error: \" + ex.getMessage());");
        topLevelClass.addMethod(_invalidInputHandler);

        // Use DefaultJavaFormatter to format the generated Java file
        DefaultJavaFormatter javaFormatter = new DefaultJavaFormatter();
        GeneratedJavaFile generatedJavaFile = new GeneratedJavaFile(topLevelClass, "src/main/java", javaFormatter);

        // Add the generated file to the list
        additionalFiles.add(generatedJavaFile);

        return additionalFiles;
    }
}
