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

public class NotFoundExceptionPlugin extends PluginAdapter {
    private String targetPackage;

    private Properties properties;

    public NotFoundExceptionPlugin(Properties properties) {
        super();
        this.properties = properties;
    }

    public NotFoundExceptionPlugin() {
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
        TopLevelClass topLevelClass = new TopLevelClass(packageName + "." + _modelName + "NotFoundException");
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);

        // Add class documentation
        topLevelClass.addJavaDocLine("/**");
        topLevelClass.addJavaDocLine(" * This is a generated NotFoundException for demonstration purposes.");
        topLevelClass.addJavaDocLine(" */");

        topLevelClass.addImportedType(new FullyQualifiedJavaType("java.lang.RuntimeException"));

        topLevelClass.setSuperClass(new FullyQualifiedJavaType("java.lang.RuntimeException"));

        Method _defaultconstructor = new Method(_modelName + "NotFoundException");
        _defaultconstructor.setConstructor(true);
        _defaultconstructor.setVisibility(JavaVisibility.PUBLIC);
        topLevelClass.addMethod(_defaultconstructor);

        Method _constructor = new Method(_modelName + "NotFoundException");
        _constructor.setConstructor(true);
        _constructor.setVisibility(JavaVisibility.PUBLIC);
        //String newModelName = "new" + _modelName;
        Parameter parameter  = new Parameter(new FullyQualifiedJavaType("java.lang.Long"), "id");
        _constructor.addParameter(parameter);
        _constructor.addBodyLine(String.format("super(\"Could not find %s \" + id);", _modelName));
        topLevelClass.addMethod(_constructor);


        // Use DefaultJavaFormatter to format the generated Java file
        DefaultJavaFormatter javaFormatter = new DefaultJavaFormatter();
        GeneratedJavaFile generatedJavaFile = new GeneratedJavaFile(topLevelClass, "src/main/java", javaFormatter);

        // Add the generated file to the list
        additionalFiles.add(generatedJavaFile);

        return additionalFiles;
    }
}
