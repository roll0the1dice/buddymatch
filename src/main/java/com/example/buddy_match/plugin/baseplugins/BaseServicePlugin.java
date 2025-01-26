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
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;

public class BaseServicePlugin extends PluginAdapter {
    private String targetPackage;

    private Properties localproperties;

    public BaseServicePlugin() {
        super();
    }

    public BaseServicePlugin(Properties properties) {
        super();
        this.localproperties = properties;
    }

    @Override
    public boolean validate(List<String> warnings) {
        // 在此验证插件的配置是否合法，返回true表示插件配置有效
        String myCustomParameter = localproperties.getProperty("targetPackage");
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
        // String interfaceName = introspectedTable.getFullyQualifiedTable().getDomainObjectName() + modelClassName;
        TopLevelClass topLevelClass = new TopLevelClass(packageName + "." + _modelName + "BaseService");
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);

        // Add class documentation
        topLevelClass.addJavaDocLine("/**");
        topLevelClass.addJavaDocLine(" * This is a generated BaseService for demonstration purposes.");
        topLevelClass.addJavaDocLine(" */");

        topLevelClass.addStaticImport("org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn");
        topLevelClass.addStaticImport("org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo");

        //topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.web.bind.annotation.DeleteMapping"));
        //topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.web.bind.annotation.GetMapping"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.web.bind.annotation.PathVariable"));
        //topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.web.bind.annotation.PostMapping"));
        //topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.web.bind.annotation.PutMapping"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.web.bind.annotation.RequestBody"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Service"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType(modelClassName));
        topLevelClass.addImportedType(new FullyQualifiedJavaType(packageName + "." + _modelName + "NotFoundException"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.beans.factory.annotation.Autowired"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.hateoas.EntityModel"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.hateoas.CollectionModel"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("java.util.List"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("java.util.stream.Collectors"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.http.ResponseEntity"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.hateoas.IanaLinkRelations"));

        topLevelClass.addAnnotation("@Service");

        // Add a private field
        var field = new Field ("repository", new FullyQualifiedJavaType(_modelName + "Repository"));
        field.setVisibility(JavaVisibility.PRIVATE);
        field.addJavaDocLine("/** This is an example repository. */");
        //field.addAnnotation("@Autowired");
        topLevelClass.addField(field);

        // Add a private field
        var field2 = new Field ("assembler", new FullyQualifiedJavaType(_modelName + "ModelAssembler"));
        field2.setVisibility(JavaVisibility.PRIVATE);
        field2.addJavaDocLine("/** This is an example modelAssembler. */");
        //field2.addAnnotation("@Autowired");
        topLevelClass.addField(field2);

        Method _defaultconstructor = new Method(_modelName + "BaseService");
        _defaultconstructor.setConstructor(true);
        _defaultconstructor.setVisibility(JavaVisibility.PUBLIC);
        topLevelClass.addMethod(_defaultconstructor);

        Method _constructor = new Method(_modelName + "BaseService");
        _constructor.setConstructor(true);
        _constructor.setVisibility(JavaVisibility.PUBLIC);
        _constructor.addAnnotation("@Autowired");
        //String newModelName = "new" + _modelName;
        Parameter parameter  = new Parameter(new FullyQualifiedJavaType(_modelName + "Repository"), "repository");
        _constructor.addParameter(parameter);
        _constructor.addParameter(new Parameter(new FullyQualifiedJavaType(_modelName + "ModelAssembler"), "assembler"));
        _constructor.addBodyLine("this.repository = repository;");
        _constructor.addBodyLine("this.assembler = assembler;");
        topLevelClass.addMethod(_constructor);

        Method _getAll = new Method("all");
        //_getAll.addAnnotation(String.format("@GetMapping(\"/all_%s\")", _modelName.toLowerCase()));
        _getAll.setVisibility(JavaVisibility.PUBLIC);
        FullyQualifiedJavaType _retTypeForOne = new FullyQualifiedJavaType("org.springframework.hateoas.CollectionModel");
        FullyQualifiedJavaType _entityType = new FullyQualifiedJavaType("org.springframework.hateoas.EntityModel");
        _entityType.addTypeArgument(new FullyQualifiedJavaType(modelClassName));
        _retTypeForOne.addTypeArgument(_entityType);
        _getAll.setReturnType(_retTypeForOne);
        String[] _tmpParameters = {String.format("List<%s> %s = repository.findAll().stream()", _entityType.getShortName(), _modelName.toLowerCase()), 
                                    ".map(assembler::toModel)",
                                    ".collect(Collectors.toList());",
                                    String.format("return CollectionModel.of(%s, linkTo(methodOn(%sController.class).all()).withSelfRel());", _modelName.toLowerCase(), _modelName)};
        // 将数组转换为 List<String>
        List<String> _tmpstringList = Arrays.asList(_tmpParameters);
        // 将 List<String> 赋值给 Collection<String>
        Collection<String> _tmpstringCollection = _tmpstringList;
        _getAll.addBodyLines(_tmpstringCollection);
        topLevelClass.addMethod(_getAll);
        
        Method _saveNew = new Method("create");
        //_saveNew.addAnnotation(String.format("@PostMapping(\"/%s\")", _modelName.toLowerCase()));
        _saveNew.setVisibility(JavaVisibility.PUBLIC);
        //String newModelName = "new" + _modelName;
        parameter  = new Parameter(new FullyQualifiedJavaType(modelClassName), "new" + _modelName);
        parameter.addAnnotation("@RequestBody");
        _saveNew.addParameter(parameter);
        _saveNew.setReturnType(new FullyQualifiedJavaType(modelClassName));
        _saveNew.addBodyLine(String.format("return repository.save(%s);", "new" + _modelName));
        topLevelClass.addMethod(_saveNew);

        Method _getOne = new Method("one");
        //_getOne.addAnnotation(String.format("@GetMapping(\"/%s/{id}\")", _modelName.toLowerCase()));
        _getOne.setVisibility(JavaVisibility.PUBLIC);
        parameter  = new Parameter(new FullyQualifiedJavaType("java.lang.Long"), "id");
        parameter.addAnnotation("@PathVariable");
        _getOne.addParameter(parameter);
        _retTypeForOne = new FullyQualifiedJavaType("org.springframework.hateoas.EntityModel");
        _retTypeForOne.addTypeArgument(new FullyQualifiedJavaType(modelClassName));
        _getOne.setReturnType(_retTypeForOne);
        String[] strParameter2 = {String.format("%s %s = repository.findById(id)", _modelName, _modelName.toLowerCase()), 
                                    String.format(".orElseThrow(() -> new %sNotFoundException(id));", _modelName),
                                String.format("return assembler.toModel(%s);", _modelName.toLowerCase())};
        // 将数组转换为 List<String>
        List<String> stringList = Arrays.asList(strParameter2);
        // 将 List<String> 赋值给 Collection<String>
        Collection<String> stringCollection = stringList;
        _getOne.addBodyLines(stringCollection);
        topLevelClass.addMethod(_getOne);        

        Method _replace = new Method("replace" + _modelName);
        //_replace.addAnnotation(String.format("@PutMapping(\"/%s/{id}\")", _modelName.toLowerCase()));
        _replace.setVisibility(JavaVisibility.PUBLIC);
        parameter  = new Parameter(new FullyQualifiedJavaType(modelClassName), "new" + _modelName);
        parameter.addAnnotation("@RequestBody");
        _replace.addParameter(parameter);
        parameter  = new Parameter(new FullyQualifiedJavaType("java.lang.Long"), "id");
        parameter.addAnnotation("@PathVariable");
        _replace.addParameter(parameter);
        _replace.setReturnType(new FullyQualifiedJavaType("ResponseEntity<?> "));
        String[] strParameter3 = {String.format("%s _updateModel = repository.findById(id)", _modelName, _modelName.toLowerCase()), 
                                    String.format(".map(%s -> {", "_new" + _modelName),
                                    String.format("return repository.save(%s);", "_new" + _modelName),
                                    "})",
                                    ".orElseGet(() -> {",
                                    String.format("return repository.save(%s);", "new" + _modelName),
                                    "});",
                                    String.format("%s entityModel = assembler.toModel(_updateModel);", _entityType.getShortName()),
                                    "return ResponseEntity",
                                    ".created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())",
                                    ".body(entityModel);"};
        // 将数组转换为 List<String>
        List<String> stringList3 = Arrays.asList(strParameter3);
        // 将 List<String> 赋值给 Collection<String>
        Collection<String> stringCollection3 = stringList3;
        _replace.addBodyLines(stringCollection3);
        topLevelClass.addMethod(_replace);              

        Method _delete = new Method("delete" + _modelName);
        //_delete.addAnnotation(String.format("@DeleteMapping(\"/%s/{id}\")", _modelName.toLowerCase()));
        _delete.setVisibility(JavaVisibility.PUBLIC);
        //String newModelName = "new" + _modelName;
        parameter  = new Parameter(new FullyQualifiedJavaType("java.lang.Long"), "id");
        parameter.addAnnotation("@PathVariable");
        _delete.addParameter(parameter);
        _delete.addBodyLine("repository.deleteById(id);");
        _delete.addBodyLine("return ResponseEntity.noContent().build();");
        _delete.setReturnType(new FullyQualifiedJavaType("ResponseEntity<?>"));
        topLevelClass.addMethod(_delete);

        // Use DefaultJavaFormatter to format the generated Java file
        DefaultJavaFormatter javaFormatter = new DefaultJavaFormatter();
        GeneratedJavaFile generatedJavaFile = new GeneratedJavaFile(topLevelClass, "src/main/java", javaFormatter);

        // Add the generated file to the list
        additionalFiles.add(generatedJavaFile);

        return additionalFiles;
    }
}
