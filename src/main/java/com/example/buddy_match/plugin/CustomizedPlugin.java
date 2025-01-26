package com.example.buddy_match.plugin;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.springframework.util.ResourceUtils;

import com.example.buddy_match.plugin.baseplugins.ApiResponsePlugin;
import com.example.buddy_match.plugin.baseplugins.BaseServicePlugin;
import com.example.buddy_match.plugin.baseplugins.ControllerPlugin;
import com.example.buddy_match.plugin.baseplugins.CustomSpecsPlugin;
import com.example.buddy_match.plugin.baseplugins.GlobalExceptionAdvicePlugin;
import com.example.buddy_match.plugin.baseplugins.ModelAssemblerPlugin;
import com.example.buddy_match.plugin.baseplugins.NotFoundAdvicePlugin;
import com.example.buddy_match.plugin.baseplugins.NotFoundExceptionPlugin;
import com.example.buddy_match.plugin.baseplugins.RepositoryPlugin;
import com.example.buddy_match.plugin.baseplugins.ServerExceptionPlugin;
import com.example.buddy_match.plugin.baseplugins.ServiceImplPlugin;
import com.example.buddy_match.plugin.baseplugins.ServicePlugin;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CustomizedPlugin extends PluginAdapter {
  RepositoryPlugin repositoryPlugin;
  NotFoundExceptionPlugin notFoundExceptionPlugin;
  NotFoundAdvicePlugin notFoundAdvicePlugin;
  ModelAssemblerPlugin modelAssemblerPlugin;
  BaseServicePlugin baseServicePlugin;
  ServicePlugin servicePlugin;
  ServiceImplPlugin serviceImplPlugin;
  ControllerPlugin controllerPlugin;
  CustomSpecsPlugin customSpecsPlugin;
  GlobalExceptionAdvicePlugin globalExceptionAdvicePlugin;
  ApiResponsePlugin apiResponsePlugin;
  ServerExceptionPlugin serverExceptionPlugin;

  public CustomizedPlugin() {
    super();
    repositoryPlugin = new RepositoryPlugin(properties);
    notFoundExceptionPlugin = new NotFoundExceptionPlugin(properties);
    notFoundAdvicePlugin = new NotFoundAdvicePlugin(properties);
    modelAssemblerPlugin = new ModelAssemblerPlugin(properties);
    baseServicePlugin = new BaseServicePlugin(properties);
    servicePlugin = new ServicePlugin(properties);
    serviceImplPlugin = new ServiceImplPlugin(properties);
    controllerPlugin = new ControllerPlugin(properties);
    customSpecsPlugin = new CustomSpecsPlugin(properties);
    globalExceptionAdvicePlugin = new GlobalExceptionAdvicePlugin(properties);
    apiResponsePlugin = new ApiResponsePlugin(properties);
    serverExceptionPlugin = new ServerExceptionPlugin(properties);
  }

  public boolean validate(List<String> warnings) {
    return repositoryPlugin.validate(warnings) &&
        notFoundExceptionPlugin.validate(warnings) &&
        notFoundAdvicePlugin.validate(warnings) &&
        modelAssemblerPlugin.validate(warnings) &&
        baseServicePlugin.validate(warnings) &&
        servicePlugin.validate(warnings) &&
        serviceImplPlugin.validate(warnings) &&
        controllerPlugin.validate(warnings) &&
        customSpecsPlugin.validate(warnings) &&
        globalExceptionAdvicePlugin.validate(warnings) &&
        apiResponsePlugin.validate(warnings) &&
        serverExceptionPlugin.validate(warnings);
  }

  @Override
  public void setProperties(Properties properties) {
    super.setProperties(properties);
  }

  @Override
  public boolean modelExampleClassGenerated(TopLevelClass topLevelClass,
      IntrospectedTable introspectedTable) {

    topLevelClass.addJavaDocLine("/** This is a custom model generated by MyBatis Generator */");

    return true;
  }

  @Override
  public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
    makeAnnotation(topLevelClass);
    for (IntrospectedColumn primaryKey : introspectedTable.getPrimaryKeyColumns()) {
      String javaProperty = primaryKey.getJavaProperty();
      for (Field field : topLevelClass.getFields()) {
        if (field.getName().equals(javaProperty)) {
          field.addAnnotation("@Id");
          topLevelClass.addImportedType("jakarta.persistence.Id");
          field.addAnnotation("@GeneratedValue(strategy = GenerationType.IDENTITY)");
          topLevelClass.addImportedType("jakarta.persistence.GeneratedValue");
        }
      }
    }
    return true;
  }

  @Override
  public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
    makeAnnotation(topLevelClass);
    return true;
  }

  @Override
  public boolean modelRecordWithBLOBsClassGenerated(TopLevelClass topLevelClass,
      IntrospectedTable introspectedTable) {
    makeAnnotation(topLevelClass);
    return true;
  }

  @Override
  public boolean clientGenerated(Interface interfaze, IntrospectedTable introspectedTable) {
    interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Mapper"));
    interfaze.addAnnotation("@Mapper");

    return true;
  }

  protected void makeAnnotation(TopLevelClass topLevelClass) {
    topLevelClass.addImportedType(new FullyQualifiedJavaType("lombok.Data"));
    topLevelClass.addImportedType(new FullyQualifiedJavaType("jakarta.persistence.Entity"));
    topLevelClass.addImportedType(new FullyQualifiedJavaType("jakarta.persistence.GeneratedValue"));
    topLevelClass.addImportedType(new FullyQualifiedJavaType("jakarta.persistence.GenerationType"));
    topLevelClass.addImportedType(new FullyQualifiedJavaType("org.hibernate.annotations.DynamicInsert"));
    topLevelClass.addAnnotation("@Data");
    topLevelClass.addAnnotation("@Entity");
    topLevelClass.addAnnotation("@DynamicInsert");

    // remove getter and setter.
    topLevelClass.getMethods().clear();
  }

  @Override
  public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
    // List to store additional generated files
    List<GeneratedJavaFile> additionalFiles = new ArrayList<>();

    List<GeneratedJavaFile> repositoryPluginFiles = repositoryPlugin
        .contextGenerateAdditionalJavaFiles(introspectedTable);
    additionalFiles.addAll(repositoryPluginFiles);

    List<GeneratedJavaFile> notFoundExceptionPluginFiles = notFoundExceptionPlugin
        .contextGenerateAdditionalJavaFiles(introspectedTable);
    additionalFiles.addAll(notFoundExceptionPluginFiles);

    List<GeneratedJavaFile> notFoundAdvicePluginFiles = notFoundAdvicePlugin
        .contextGenerateAdditionalJavaFiles(introspectedTable);
    additionalFiles.addAll(notFoundAdvicePluginFiles);

    List<GeneratedJavaFile> modelAssemblerPluginFiles = modelAssemblerPlugin
        .contextGenerateAdditionalJavaFiles(introspectedTable);
    additionalFiles.addAll(modelAssemblerPluginFiles);

    List<GeneratedJavaFile> baseServicePluginFiles = baseServicePlugin
        .contextGenerateAdditionalJavaFiles(introspectedTable);
    additionalFiles.addAll(baseServicePluginFiles);

    List<GeneratedJavaFile> servicePluginFiles = servicePlugin
        .contextGenerateAdditionalJavaFiles(introspectedTable);
    additionalFiles.addAll(servicePluginFiles);

    List<GeneratedJavaFile> serviceImplPluginFiles = serviceImplPlugin
        .contextGenerateAdditionalJavaFiles(introspectedTable);
    additionalFiles.addAll(serviceImplPluginFiles);

    List<GeneratedJavaFile> controllerPluginFiles = controllerPlugin
        .contextGenerateAdditionalJavaFiles(introspectedTable);
    additionalFiles.addAll(controllerPluginFiles);

    List<GeneratedJavaFile> customSpecsPluginFiles = customSpecsPlugin
        .contextGenerateAdditionalJavaFiles(introspectedTable);
    additionalFiles.addAll(customSpecsPluginFiles);

    List<GeneratedJavaFile> globalExceptionAdvicePluginFiles = globalExceptionAdvicePlugin
        .contextGenerateAdditionalJavaFiles(introspectedTable);
    additionalFiles.addAll(globalExceptionAdvicePluginFiles);

    List<GeneratedJavaFile> apiResponsePluginFiles = apiResponsePlugin
        .contextGenerateAdditionalJavaFiles(introspectedTable);
    additionalFiles.addAll(apiResponsePluginFiles);

    List<GeneratedJavaFile> serverExceptionPluginFiles = serverExceptionPlugin
        .contextGenerateAdditionalJavaFiles(introspectedTable);
    additionalFiles.addAll(serverExceptionPluginFiles);

    return additionalFiles;
  }

  public static void main(String[] args) {
    try {
      // 指定 generatorConfig.xml 配置文件的路径
      String classpath = ResourceUtils.getFile("classpath:").getAbsolutePath();
      String configFilePath = Paths.get(classpath, "generatorConfig.xml").toString();

      // 准备 MyBatis Generator 的配置
      List<String> warnings = new ArrayList<>();
      boolean overwrite = false;
      File configFile = new File(configFilePath);

      // System.out.println(configFilePath);

      ConfigurationParser cp = new ConfigurationParser(warnings);
      Configuration config = cp.parseConfiguration(configFile);
      DefaultShellCallback callback = new DefaultShellCallback(overwrite);

      // 执行 MyBatis Generator
      MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
      myBatisGenerator.generate(null);

      // 输出警告信息
      for (String warning : warnings) {
        System.out.println(warning);
      }

      System.out.println("MyBatis Generator sucesses !");

      System.exit(0); // 正常退出

    } catch (Exception e) {
      System.out.println("MyBatis Generator fails !");
      e.printStackTrace();
    }

  }

}
