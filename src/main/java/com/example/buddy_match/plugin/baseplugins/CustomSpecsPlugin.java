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
import org.mybatis.generator.api.dom.java.InnerEnum;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.java.TypeParameter;

public class CustomSpecsPlugin extends PluginAdapter {

    private String targetPackage;

    private Properties properties;

    public CustomSpecsPlugin(Properties properties) {
        super();
        this.properties = properties;
    }

    public CustomSpecsPlugin() {
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
        // String interfaceName = introspectedTable.getFullyQualifiedTable().getDomainObjectName() + modelClassName;
        TopLevelClass topLevelClass = new TopLevelClass(packageName + "." + "CustomSpecs");
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);

        // Add class documentation
        topLevelClass.addJavaDocLine("/**");
        topLevelClass.addJavaDocLine(" * This is a generated BaseService for demonstration purposes.");
        topLevelClass.addJavaDocLine(" */");


        topLevelClass.addImportedType(new FullyQualifiedJavaType("lombok.Data"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.data.jpa.domain.Specification"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.beans.factory.annotation.Autowired"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("jakarta.persistence.criteria.CriteriaBuilder"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType(modelClassName));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.beans.factory.annotation.Autowired"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.lang.Nullable"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("jakarta.persistence.criteria.Root"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("jakarta.persistence.criteria.Predicate"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("java.util.List"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("java.util.Stack"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("java.util.Collection"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("jakarta.persistence.criteria.CriteriaQuery"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.data.jpa.domain.Specification"));

        topLevelClass.addTypeParameter(new TypeParameter("T"));

        topLevelClass.addAnnotation("@Data");

        InnerEnum  innerEnum = new InnerEnum("Operator");
        innerEnum.addField(new Field("EQUAL, LIKE, GREATER_THAN, LESS_THAN, IN, AND, OR", new FullyQualifiedJavaType("")));
        topLevelClass.addInnerEnum(innerEnum);

        // Add a private field
        FullyQualifiedJavaType _listInteger = new FullyQualifiedJavaType("java.lang.Stack");
        _listInteger.addTypeArgument(new FullyQualifiedJavaType("Operator"));
        var field7 = new Field ("boolOps", _listInteger);
        field7.setVisibility(JavaVisibility.PRIVATE);
        field7.addJavaDocLine("/** This is an example. */");
        topLevelClass.addField(field7);

        FullyQualifiedJavaType _listObject= new FullyQualifiedJavaType("java.lang.Stack");
        _listObject.addTypeArgument(new FullyQualifiedJavaType("java.lang.Object"));
        Field field = new Field ("valueList", _listObject);
        field.setVisibility(JavaVisibility.PRIVATE);
        field.addJavaDocLine("/** This is an example. */");
        topLevelClass.addField(field);

        _listObject= new FullyQualifiedJavaType("java.lang.Stack");
        _listObject.addTypeArgument(new FullyQualifiedJavaType("java.lang.String"));
        field = new Field ("fieldList", _listObject);
        field.setVisibility(JavaVisibility.PRIVATE);
        field.addJavaDocLine("/** This is an example. */");
        topLevelClass.addField(field);

        _listObject= new FullyQualifiedJavaType("java.lang.Stack");
        _listObject.addTypeArgument(new FullyQualifiedJavaType("Operator"));
        field = new Field ("compOpList", _listObject);
        field.setVisibility(JavaVisibility.PRIVATE);
        field.addJavaDocLine("/** This is an example. */");
        topLevelClass.addField(field);

        Method _defaultconstructor = new Method("CustomSpecs");
        _defaultconstructor.setConstructor(true);
        _defaultconstructor.setVisibility(JavaVisibility.PUBLIC);

        _defaultconstructor.addBodyLine("this.boolOps = new Stack<Operator>();");
        _defaultconstructor.addBodyLine("this.valueList = new Stack<Object>();");
        _defaultconstructor.addBodyLine("this.fieldList = new Stack<String>();");
        _defaultconstructor.addBodyLine("this.compOpList = new Stack<Operator>();");
        topLevelClass.addMethod(_defaultconstructor);

        Method _toJpaPredicate = new Method("_toPredicate");
        _toJpaPredicate.addAnnotation("@Nullable");
        _toJpaPredicate.setVisibility(JavaVisibility.PUBLIC);
        FullyQualifiedJavaType _fieldKeyType = new FullyQualifiedJavaType("java.lang.String");
        Parameter param1  = new Parameter(_fieldKeyType, "fieldKey");
        _toJpaPredicate.addParameter(param1);
        FullyQualifiedJavaType _fieldValueQuery = new FullyQualifiedJavaType("java.lang.Object");
        Parameter param2  = new Parameter(_fieldValueQuery, "fieldValue");
        _toJpaPredicate.addParameter(param2);
        FullyQualifiedJavaType _compOp = new FullyQualifiedJavaType("Operator");
        Parameter param3  = new Parameter(_compOp, "compOp");
        _toJpaPredicate.addParameter(param3);
        FullyQualifiedJavaType _ret_toJpaPredicate = new FullyQualifiedJavaType("org.springframework.data.jpa.domain.Specification");
        _ret_toJpaPredicate.addTypeArgument(new FullyQualifiedJavaType("T"));
        _toJpaPredicate.setReturnType(_ret_toJpaPredicate);
        String[] strParameter3 = {
            "return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {",
            "switch (compOp) {",
            "case EQUAL:",
                "return cb.equal(root.get(fieldKey), fieldValue);",
            "case LIKE:",
                "return cb.like(root.get(fieldKey), \"%\" + fieldValue + \"%\");",
            "case GREATER_THAN:",
                "return cb.greaterThan(root.get(fieldKey), (Comparable) fieldValue);",
            "case LESS_THAN:",
                "return cb.lessThan(root.get(fieldKey), (Comparable) fieldValue);",
            "case IN:",
                "CriteriaBuilder.In<Object> in = cb.in(root.get(fieldKey));",
                "if (fieldValue instanceof Collection) {",
                    "for (Object value : (Collection<?>) fieldValue) {",
                        "in.value(value);",
                    "}",
                "}",
                "return in;",
            "default:",
                "throw new UnsupportedOperationException(\"Unsupported operator: \" + compOp);",
            "}",
            "};"
        };
        List<String> stringList3 = Arrays.asList(strParameter3);
        Collection<String> stringCollection3 = stringList3;
        _toJpaPredicate.addBodyLines(stringCollection3);
        topLevelClass.addMethod(_toJpaPredicate);       
        
        
        Method _and = new Method("_and");
        _and.setVisibility(JavaVisibility.PUBLIC);
        String[] strParameter4 = {"assert(valueList.size() == boolOps.size());",
            "assert(fieldList.size() == valueList.size());",
            "assert(compOpList.size() == fieldList.size());",
            "if(!boolOps.empty()) {",
                "boolOps.pop();",
                "boolOps.push(Operator.AND);",
            "}",
            "return this;"};
        List<String> stringList4 = Arrays.asList(strParameter4);
        Collection<String> stringCollection4 = stringList4;
        _and.addBodyLines(stringCollection4);
        FullyQualifiedJavaType _retEqual = new FullyQualifiedJavaType("CustomSpecs");
        _retEqual.addTypeArgument(new FullyQualifiedJavaType("T"));
        _and.setReturnType(_retEqual);
        topLevelClass.addMethod(_and);

        Method _or = new Method("_or");
        _or.setVisibility(JavaVisibility.PUBLIC);
        String[] strParameter5 = {        "assert(valueList.size() == boolOps.size());",
            "assert(fieldList.size() == valueList.size());",
            "assert(compOpList.size() == fieldList.size());",
            "if(!boolOps.empty()) {",
                "boolOps.pop();",
               "boolOps.push(Operator.OR);",
            "}",
            "return this;"};
        List<String> stringList5 = Arrays.asList(strParameter5);
        Collection<String> stringCollection5 = stringList5;
        _or.addBodyLines(stringCollection5);
        _or.setReturnType(_retEqual);
        topLevelClass.addMethod(_or);


        Method _equal = new Method("_equal");
        _equal.setVisibility(JavaVisibility.PUBLIC);
        FullyQualifiedJavaType _filedKey = new FullyQualifiedJavaType("java.lang.String");
        //_filedKey.addTypeArgument(new FullyQualifiedJavaType("?"));
        Parameter param_fieldKey  = new Parameter(_filedKey, "fieldKey");
        FullyQualifiedJavaType _filedValue = new FullyQualifiedJavaType("java.lang.Object");
        //_filedKey.addTypeArgument(new FullyQualifiedJavaType("?"));
        Parameter param_filedValue  = new Parameter(_filedValue, "fieldValue");
        _equal.addParameter(param_fieldKey);
        _equal.addParameter(param_filedValue);
        String[] strParameter6 = {"if (fieldValue == null || fieldValue == null) return this;",
        "fieldList.push(fieldKey);",
        "valueList.push(fieldValue);",
        "compOpList.push(Operator.EQUAL);",
        "assert(fieldList.size() == valueList.size());",
        "assert(compOpList.size() == fieldList.size());",
        "if (boolOps.size() < fieldList.size()) ",
        "boolOps.push(Operator.AND);",
        "return this;"};
        List<String> stringList6 = Arrays.asList(strParameter6);
        Collection<String> stringCollection6 = stringList6;
        _equal.addBodyLines(stringCollection6);
        _equal.setReturnType(_retEqual);
        topLevelClass.addMethod(_equal);

        Method _like = new Method("_like");
        _like.setVisibility(JavaVisibility.PUBLIC);
        _like.addParameter(param_fieldKey);
        _like.addParameter(param_filedValue);
        String[] strParameter_like = { "if (fieldValue == null || fieldValue == null) return this;",
        "fieldList.push(fieldKey);",
        "valueList.push(fieldValue);",
        "compOpList.push(Operator.LIKE);",
        "assert(fieldList.size() == valueList.size());",
        "assert(compOpList.size() == fieldList.size());",
        "if (boolOps.size() < fieldList.size()) ",
        "boolOps.push(Operator.AND);",
        "return this;"};
        List<String> stringList_like = Arrays.asList(strParameter_like);
        Collection<String> stringCollection_like = stringList_like;
        _like.addBodyLines(stringCollection_like);
        _like.setReturnType(_retEqual);
        topLevelClass.addMethod(_like);

        Method _greaterThan = new Method("_greaterThan");
        _greaterThan.setVisibility(JavaVisibility.PUBLIC);
        _greaterThan.addParameter(param_fieldKey);
        _greaterThan.addParameter(param_filedValue);
        String[] strParameter_greaterThan = {"if (fieldValue == null || fieldValue == null) return this;",
        "fieldList.push(fieldKey);",
        "valueList.push(fieldValue);",
        "compOpList.push(Operator.GREATER_THAN);",
        "assert(fieldList.size() == valueList.size());",
        "assert(compOpList.size() == fieldList.size());",
        "if (boolOps.size() < fieldList.size()) ",
        "boolOps.push(Operator.AND);",
        "return this;"};
        List<String> stringList_greaterThan = Arrays.asList(strParameter_greaterThan);
        Collection<String> stringCollection_greaterThan = stringList_greaterThan;
        _greaterThan.addBodyLines(stringCollection_greaterThan);
        _greaterThan.setReturnType(_retEqual);
        topLevelClass.addMethod(_greaterThan);

        Method _lessThan = new Method("_lessThan");
        _lessThan.setVisibility(JavaVisibility.PUBLIC);
        _lessThan.addParameter(param_fieldKey);
        _lessThan.addParameter(param_filedValue);
        String[] strParameter_less = {"if (fieldValue == null || fieldValue == null) return this;",
        "fieldList.push(fieldKey);",
        "valueList.push(fieldValue);",
        "compOpList.push(Operator.LESS_THAN);",
        "assert(fieldList.size() == valueList.size());",
        "assert(compOpList.size() == fieldList.size());",
        "if (boolOps.size() < fieldList.size()) ",
        "boolOps.push(Operator.AND);",
        "return this;"};
        List<String> stringList_less = Arrays.asList(strParameter_less);
        Collection<String> stringCollection_less = stringList_less;
        _lessThan.addBodyLines(stringCollection_less);
        _lessThan.setReturnType(_retEqual);
        topLevelClass.addMethod(_lessThan);

        Method _in = new Method("_in");
        _in.setVisibility(JavaVisibility.PUBLIC);
        _in.addParameter(param_fieldKey);
        _in.addParameter(param_filedValue);
        String[] strParameter_in = {"if (fieldValue == null || fieldValue == null) return this;",
            "fieldList.push(fieldKey);",
            "valueList.push(fieldValue);",
            "compOpList.push(Operator.IN);",
            "assert(fieldList.size() == valueList.size());",
            "assert(compOpList.size() == fieldList.size());",
            "if (boolOps.size() < fieldList.size()) ",
            "boolOps.push(Operator.AND);",
            "return this;"};
        List<String> stringList_in = Arrays.asList(strParameter_in);
        Collection<String> stringCollection_in = stringList_in;
        _in.addBodyLines(stringCollection_in);
        _in.setReturnType(_retEqual);
        topLevelClass.addMethod(_in);

        Method _executeQuery= new Method("_generateSpecifications");
        _executeQuery.setVisibility(JavaVisibility.PUBLIC);
        String[] strParameter7 = { 
            "Specification<T> combinedSpec = Specification.where(null);",
            "assert(valueList.size() == boolOps.size());",
            "assert(fieldList.size() == valueList.size());",
            "assert(compOpList.size() == fieldList.size());",
            "while (!boolOps.isEmpty() && !valueList.isEmpty() && !fieldList.isEmpty()) {",
                "Operator boolOp = boolOps.pop();",
                "Object fieldValue = valueList.pop();",
                "String fieldKey = fieldList.pop();",
                "Operator compOp = compOpList.pop();",
                "Specification<T> _tmpSpec =  _toPredicate(fieldKey, fieldValue, compOp);",
                "if (_tmpSpec == null) ",
                    "throw new UnsupportedOperationException(\"Unsupported toPredicate\");",
                "switch (boolOp) {",
                "case OR:",
                    "combinedSpec = _tmpSpec.or(combinedSpec);",
                    "break;",
                "case AND:",
                    "default:",
                        "combinedSpec = _tmpSpec.and(combinedSpec);",
                        "break;",
                    "}",
                "}",
                "assert(valueList.size() == boolOps.size());",
                "assert(fieldList.size() == valueList.size());",
                "assert(compOpList.size() == fieldList.size());",
                "return combinedSpec;"};
        List<String> stringList7 = Arrays.asList(strParameter7);
        Collection<String> stringCollection7 = stringList7;
        _executeQuery.addBodyLines(stringCollection7);
        FullyQualifiedJavaType _retexecuteQuery = new FullyQualifiedJavaType("org.springframework.data.jpa.domain.Specification");
        _retexecuteQuery.addTypeArgument(new FullyQualifiedJavaType("T"));
        _executeQuery.setReturnType(_retexecuteQuery);
        _executeQuery.setReturnType(_retexecuteQuery);
        topLevelClass.addMethod(_executeQuery);


        // Use DefaultJavaFormatter to format the generated Java file
        DefaultJavaFormatter javaFormatter = new DefaultJavaFormatter();
        GeneratedJavaFile generatedJavaFile = new GeneratedJavaFile(topLevelClass, "src/main/java", javaFormatter);

        // Add the generated file to the list
        additionalFiles.add(generatedJavaFile);

        return additionalFiles;
    }
}
