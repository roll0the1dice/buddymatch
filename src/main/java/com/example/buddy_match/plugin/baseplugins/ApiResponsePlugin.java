package com.example.buddy_match.plugin.baseplugins;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.DefaultJavaFormatter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.java.TypeParameter;

public class ApiResponsePlugin extends PluginAdapter {

  private String targetPackage;

  private Properties properties;

  public ApiResponsePlugin(Properties properties) {
    super();
    this.properties = properties;
  }

  public ApiResponsePlugin() {
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
  public boolean clientGenerated(Interface interfaze, IntrospectedTable introspectedTable) {

    return true;
  }

  @Override
  public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
    // List to store additional generated files
    List<GeneratedJavaFile> additionalFiles = new ArrayList<>();

    // 设置接口类的包名
    String packageName = this.targetPackage;

    // 创建接口类（例如 UserMapper）
    String modelClassName = introspectedTable.getBaseRecordType();

    String[] strList = modelClassName.split("\\.");
    String _modelName = Arrays.asList(strList).get(strList.length - 1);
    System.out.println(packageName);
    System.out.println(_modelName);
    // String interfaceName =
    // introspectedTable.getFullyQualifiedTable().getDomainObjectName() +
    // modelClassName;
    TopLevelClass topLevelClass = new TopLevelClass(packageName + "." + "ApiResponse");
    topLevelClass.setVisibility(JavaVisibility.PUBLIC);

    // Add class documentation
    topLevelClass.addJavaDocLine("/**");
    topLevelClass.addJavaDocLine(" * This is a generated BaseService for demonstration purposes.");
    topLevelClass.addJavaDocLine(" */");

    topLevelClass.addImportedType(new FullyQualifiedJavaType("lombok.Data"));
    topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.http.ResponseEntity"));

    topLevelClass.addTypeParameter(new TypeParameter("T"));

    topLevelClass.addAnnotation("@Data");

    Field field = new Field("statusCodeValue", new FullyQualifiedJavaType("java.lang.Integer"));
    field.setVisibility(JavaVisibility.PRIVATE);
    field.addJavaDocLine("/** This is an example. */");
    topLevelClass.addField(field);

    field = new Field("statusCode", new FullyQualifiedJavaType("java.lang.String"));
    field.setVisibility(JavaVisibility.PRIVATE);
    field.addJavaDocLine("/** This is an example. */");
    topLevelClass.addField(field);

    field = new Field("data", new FullyQualifiedJavaType("T"));
    field.setVisibility(JavaVisibility.PRIVATE);
    field.addJavaDocLine("/** This is an example. */");
    topLevelClass.addField(field);

    Method _defaultconstructor = new Method("ApiResponse");
    _defaultconstructor.setConstructor(true);
    _defaultconstructor.setVisibility(JavaVisibility.PUBLIC);
    topLevelClass.addMethod(_defaultconstructor);

    Method _constructor = new Method("ApiResponse");
    _constructor.setConstructor(true);
    _constructor.setVisibility(JavaVisibility.PUBLIC);
    // _constructor.addAnnotation("@Autowired");
    _constructor.addParameter(new Parameter(new FullyQualifiedJavaType("java.lang.Integer"), "statusCodeValue"));
    _constructor.addParameter(new Parameter(new FullyQualifiedJavaType("java.lang.String"), "statusCode"));
    _constructor.addParameter(new Parameter(new FullyQualifiedJavaType("T"), "data"));
    _constructor.addBodyLine("this.statusCodeValue = statusCodeValue;");
    _constructor.addBodyLine("this.statusCode = statusCode;");
    _constructor.addBodyLine("this.data = data;");
    topLevelClass.addMethod(_constructor);

    Method _success = new Method("success");
    _success.addParameter(new Parameter(new FullyQualifiedJavaType("T"), "data"));
    _success.setVisibility(JavaVisibility.PUBLIC);
    _success.setStatic(true);
    FullyQualifiedJavaType _retType = new FullyQualifiedJavaType("org.springframework.http.ResponseEntity");
    _retType.addTypeArgument(new FullyQualifiedJavaType("ApiResponse<T>"));
    _success.addTypeParameter(new TypeParameter("T"));
    _success.setReturnType(_retType);
    _success.addBodyLine("return ResponseEntity.ok().body(new ApiResponse<>(200, \"success\", data));");
    topLevelClass.addMethod(_success);

    Method _fail = new Method("fail");
    _fail.addParameter(new Parameter(new FullyQualifiedJavaType("java.lang.Integer"), "statusCodeValue"));
    _fail.addParameter(new Parameter(new FullyQualifiedJavaType("java.lang.String"), "statusCode"));
    _fail.setVisibility(JavaVisibility.PUBLIC);
    _fail.setStatic(true);
    _retType = new FullyQualifiedJavaType("org.springframework.http.ResponseEntity");
    _retType.addTypeArgument(new FullyQualifiedJavaType("ApiResponse<T>"));
    _fail.addTypeParameter(new TypeParameter("T"));
    _fail.setReturnType(_retType);
    _fail.addBodyLine("return ResponseEntity.badRequest().body(new ApiResponse<>(statusCodeValue, statusCode, null));");
    topLevelClass.addMethod(_fail);

    // Use DefaultJavaFormatter to format the generated Java file
    DefaultJavaFormatter javaFormatter = new DefaultJavaFormatter();
    GeneratedJavaFile generatedJavaFile = new GeneratedJavaFile(topLevelClass, "src/main/java", javaFormatter);

    // Add the generated file to the list
    additionalFiles.add(generatedJavaFile);

    return additionalFiles;
  }
}
