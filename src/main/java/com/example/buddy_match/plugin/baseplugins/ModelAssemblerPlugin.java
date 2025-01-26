package com.example.buddy_match.plugin.baseplugins;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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

public class ModelAssemblerPlugin extends PluginAdapter {
    private String targetPackage;

    private Properties properties;

    public ModelAssemblerPlugin(Properties properties) {
        super();
        this.properties = properties;
    }

    public ModelAssemblerPlugin() {
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
        // String interfaceName =
        // introspectedTable.getFullyQualifiedTable().getDomainObjectName() +
        // modelClassName;
        // System.out.println(packageName);
        // System.out.println(modelClassName);
        TopLevelClass topLevelClass = new TopLevelClass(packageName + "." + _modelName + "ModelAssembler");
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        FullyQualifiedJavaType modelAssemblerType = new FullyQualifiedJavaType(
                "org.springframework.hateoas.server.RepresentationModelAssembler");
        FullyQualifiedJavaType entityModelType2 = new FullyQualifiedJavaType("org.springframework.hateoas.EntityModel");
        entityModelType2.addTypeArgument(new FullyQualifiedJavaType(modelClassName));
        modelAssemblerType.addTypeArgument(new FullyQualifiedJavaType(modelClassName));
        modelAssemblerType.addTypeArgument(entityModelType2);
        topLevelClass.addSuperInterface(modelAssemblerType);

        topLevelClass.addImportedType(new FullyQualifiedJavaType("lombok.NonNull"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.hateoas.EntityModel"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Component"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType(modelClassName));
        topLevelClass.addStaticImport("org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo");
        topLevelClass.addStaticImport("org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn");
        topLevelClass.addImportedType(
                new FullyQualifiedJavaType("org.springframework.hateoas.server.RepresentationModelAssembler"));

        // Add class documentation
        topLevelClass.addJavaDocLine("/**");
        topLevelClass.addJavaDocLine(" * This is a generated NotFoundException for demonstration purposes.");
        topLevelClass.addJavaDocLine(" */");

        topLevelClass.addAnnotation("@Component");

        Method _toModel = new Method("toModel");
        _toModel.setVisibility(JavaVisibility.PUBLIC);
        Parameter parameter = new Parameter(new FullyQualifiedJavaType(modelClassName), _modelName.toLowerCase());
        parameter.addAnnotation("@NonNull");
        _toModel.addParameter(parameter);
        _toModel.setReturnType(new FullyQualifiedJavaType("java.lang.String"));
        String[] strParameter3 = { String.format("return EntityModel.of(%s,", _modelName.toLowerCase()),
                String.format("linkTo(methodOn(%sController.class).one(%s.getId())).withSelfRel(),", _modelName,
                        _modelName.toLowerCase()),
                String.format("linkTo(methodOn(%sController.class).all()).withRel(\"%s\"));", _modelName,
                        _modelName.toLowerCase())
        };
        // 将数组转换为 List<String>
        List<String> stringList3 = Arrays.asList(strParameter3);
        // 将 List<String> 赋值给 Collection<String>
        Collection<String> stringCollection3 = stringList3;
        _toModel.addBodyLines(stringCollection3);
        FullyQualifiedJavaType entityModelType = new FullyQualifiedJavaType("org.springframework.hateoas.EntityModel");
        entityModelType.addTypeArgument(new FullyQualifiedJavaType(modelClassName));
        _toModel.setReturnType(entityModelType);
        _toModel.addAnnotation("@Override");
        topLevelClass.addMethod(_toModel);

        // Use DefaultJavaFormatter to format the generated Java file
        DefaultJavaFormatter javaFormatter = new DefaultJavaFormatter();
        GeneratedJavaFile generatedJavaFile = new GeneratedJavaFile(topLevelClass, "src/main/java", javaFormatter);

        // Add the generated file to the list
        additionalFiles.add(generatedJavaFile);

        return additionalFiles;
    }
}
