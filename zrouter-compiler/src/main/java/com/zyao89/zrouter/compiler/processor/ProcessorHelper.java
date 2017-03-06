package com.zyao89.zrouter.compiler.processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.WildcardTypeName;
import com.zyao89.zrouter.annotation.ZRouter;
import com.zyao89.zrouter.compiler.utils.ZLog;
import com.zyao89.zrouter.compiler.utils.ZUtils;
import com.zyao89.zrouter.model.RouterProperty;
import com.zyao89.zrouter.model.RouterType;

import java.io.IOException;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import static com.zyao89.zrouter.compiler.utils.Constant.ACTIVITY;
import static com.zyao89.zrouter.compiler.utils.Constant.AUTO_CREATE_FILE_PACKAGE;
import static com.zyao89.zrouter.compiler.utils.Constant.AUTO_CREATE_INJECT_CLASS_NAME_OF_GROUP;
import static com.zyao89.zrouter.compiler.utils.Constant.AUTO_CREATE_INJECT_CLASS_NAME_OF_ROOT;
import static com.zyao89.zrouter.compiler.utils.Constant.AUTO_CREATE_INJECT_GROUP_NAME_PARAM;
import static com.zyao89.zrouter.compiler.utils.Constant.AUTO_CREATE_INJECT_NAME_METHOD;
import static com.zyao89.zrouter.compiler.utils.Constant.AUTO_CREATE_INJECT_ROUTER_NAME_PARAM;
import static com.zyao89.zrouter.compiler.utils.Constant.DEFAULT_GROUP;
import static com.zyao89.zrouter.compiler.utils.Constant.GRADLE_KEY_MODULE_NAME;
import static com.zyao89.zrouter.compiler.utils.Constant.I_ZROUTER_GROUP;
import static com.zyao89.zrouter.compiler.utils.Constant.SEPARATOR;
import static com.zyao89.zrouter.compiler.utils.Constant.WARNING_TIPS;

/**
 * Created by zyao89 on 2017/3/4.
 * Contact me at 305161066@qq.com or zyao89@gmail.com
 * For more projects: https://github.com/zyao89
 */
public class ProcessorHelper
{
    private final ProcessingEnvironment mProcessingEnv;
    private final ZLog                  mZLog;
    private final Filer                 mFiler;       // File util, write class file into disk.
    private final Types                 mTypeUtil;
    private final Elements              mElementUtil;
    private final Map<String, String>              mRootMap    = new TreeMap<>();
    private final Map<String, Set<RouterProperty>> mGroupMap   = new TreeMap<>();
    private       String                           mModuleName = null;
    private       Helper                           mHelper     = null;

    /* package */ ProcessorHelper(ProcessingEnvironment processingEnv)
    {
        mProcessingEnv = processingEnv;
        mFiler = processingEnv.getFiler();                  // Generate class.
        mTypeUtil = processingEnv.getTypeUtils();            // Get type utils.
        mElementUtil = processingEnv.getElementUtils();      // Get class meta.
        mZLog = new ZLog(processingEnv.getMessager());   // Package the log utils.

        mZLog.info("ZRouterProcessor is running...");
    }

    /* package */ void initModuleName()
    {
        Map<String, String> options = mProcessingEnv.getOptions();
        if (ZUtils.isNotEmpty(options))
        {
            mModuleName = options.get(GRADLE_KEY_MODULE_NAME);
        }

        if (ZUtils.isNotEmpty(mModuleName))
        {
            mModuleName = mModuleName.replaceAll("[^0-9a-zA-Z_]+", "");
        }
        else
        {
            mModuleName = "Zyao89";
        }
        mZLog.info("The user has configuration the module name, it was [" + mModuleName + "]");
    }

    /* package */ void parseZRouters(Set<? extends Element> routerElements)
    {
        mZLog.info("Found ZRouter, start... ");

        if (ZUtils.isNotEmpty(routerElements))
        {
            mZLog.info("Parse ZRouter, running... ");
            parse(routerElements);
        }
    }

    /**
     * Parsing logic
     *
     * @param routerElements
     */
    private void parse(Set<? extends Element> routerElements)
    {
        mZLog.info("Found routes, size is " + routerElements.size());
        mRootMap.clear();

        mHelper = Helper.builder(mElementUtil);

        // Generate Java File
        try
        {
            generateJavaFile(routerElements);
        }
        catch (IOException e)
        {
            mZLog.error(e);
        }
    }

    /**
     * Generate Java files
     *
     * @param routerElements
     */
    private void generateJavaFile(Set<? extends Element> routerElements) throws IOException
    {
        for (Element element : routerElements)
        {
            Name elementSimpleName = element.getSimpleName();
            TypeMirror typeMirror = element.asType();//com.zyao89.zrouter.test.MainActivity
            ZRouter zRouter = element.getAnnotation(ZRouter.class);

            RouterProperty routerProperty = null;
            if (mTypeUtil.isSubtype(typeMirror, mHelper.type_Activity.asType()))  //Activity
            {
                mZLog.info("Found Activity Router: " + typeMirror.toString());
                for (Element field : element.getEnclosedElements())
                {
                    mZLog.info("Found " + elementSimpleName.toString() + " field: " + field.toString());
                }
                routerProperty = new RouterProperty(RouterType.ACTIVITY, element, zRouter, mModuleName);
            }

            if (!archivedGroup(routerProperty))
            {//error
                return;
            }
        }

        // build JAVA file
        for (Map.Entry<String, Set<RouterProperty>> entry : mGroupMap.entrySet())
        {
            String groupName = entry.getKey();
            Set<RouterProperty> propertySet = entry.getValue();

            MethodSpec.Builder builder_injectMethod_groups = mHelper.createBuilder_InjectMethod_Groups();
            // Make map body for paramsType
            for (RouterProperty property : propertySet)
            {
                // groupMap.put("name", RouterProperty.build(RouterType routerType, Class<?> targetClz, String name, String group, String description, String path));
                builder_injectMethod_groups.addStatement(
                        AUTO_CREATE_INJECT_GROUP_NAME_PARAM +
                                ".put($S, $T.build($T.$N, $T.class, $S, $S, $S, $S, $S))",
                        property.getName(),
                        mHelper.claN_routerProperty,
                        mHelper.claN_routerType,
                        property.getRouterType().name(),
                        ClassName.get(property.getElement().asType()),
                        property.getName(),
                        property.getGroup().toLowerCase(),
                        property.getDescription(),
                        property.getPath().toLowerCase(),
                        property.getModuleName());
            }

            // Create Group File
            String groupFileName = AUTO_CREATE_INJECT_CLASS_NAME_OF_GROUP + groupName + SEPARATOR + mModuleName;
            JavaFile.builder(AUTO_CREATE_FILE_PACKAGE, TypeSpec.classBuilder(groupFileName)
                    .addJavadoc(WARNING_TIPS)
                    .addSuperinterface(ClassName.get(mHelper.type_IZRouterGroup))
                    .addModifiers(Modifier.PUBLIC)
                    .addMethod(builder_injectMethod_groups.build())
                    .build()
            ).build().writeTo(mFiler);

            mZLog.info("Create [Group = " + groupName + "] File ( fileName: " + groupFileName + " ) is Finish... ");
            mRootMap.put(groupName, groupFileName);
        }

        createRootFile();
    }

    private void createRootFile() throws IOException
    {
        MethodSpec.Builder builder_injectMethod_routers = mHelper.createBuilder_InjectMethod_Routers();

        if (ZUtils.isNotEmpty(mRootMap))
        {
            // Create a file through the group name.
            for (Map.Entry<String, String> entry : mRootMap.entrySet())
            {
                builder_injectMethod_routers.addStatement(
                        AUTO_CREATE_INJECT_ROUTER_NAME_PARAM +
                                ".put($S, $T.class)",
                        entry.getKey() + SEPARATOR + mModuleName,
                        ClassName.get(AUTO_CREATE_FILE_PACKAGE, entry.getValue()));
            }
        }

        // create
        String rootFileName = AUTO_CREATE_INJECT_CLASS_NAME_OF_ROOT + mModuleName;
        JavaFile.builder(AUTO_CREATE_FILE_PACKAGE,
                TypeSpec.classBuilder(rootFileName)
                        .addJavadoc(WARNING_TIPS)
                        .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                        .addMethod(builder_injectMethod_routers.build())
                        .build())
                .build().writeTo(mFiler);

        mZLog.info("Create [ Root File = " + rootFileName + " ] is Finish...");
    }

    /**
     * According to the type of grouping
     *
     * @param routerProperty
     */
    private boolean archivedGroup(RouterProperty routerProperty)
    {
        if (verifyParameter(routerProperty))
        {// success
            mZLog.info("Verify ZRouter's Property is Success...");
            String group = routerProperty.getGroup();
            String name = routerProperty.getName();
            String path = routerProperty.getPath();
            mZLog.info("Began to classification, 【group = " + group + "】, 【name = " + name + "】, 【path = " + path + "】");
            Set<RouterProperty> routerPropertySet = mGroupMap.get(routerProperty.getGroup());
            if (ZUtils.isEmpty(routerPropertySet))
            {
                Set<RouterProperty> set = new TreeSet<>(new Comparator<RouterProperty>()
                {
                    @Override
                    public int compare(RouterProperty property1, RouterProperty property2)
                    {
                        return property1.getName().compareTo(property2.getName());
                    }
                });
                set.add(routerProperty);
                mGroupMap.put(group, set);
            }
            else if (isNotSameName(name, routerPropertySet))
            {
                routerPropertySet.add(routerProperty);
            }
            else
            {
                mZLog.error("【 ERROR 】, Has the same name...");
                return false;
            }
            return true;
        }
        return false;
    }

    private boolean isNotSameName(String name, Set<RouterProperty> routerPropertySet)
    {
        for (RouterProperty property : routerPropertySet)
        {
            if (property.getName().equals(name) && mModuleName.equals(property.getModuleName()))
            {
                return false;
            }
        }
        return true;
    }

    private boolean verifyParameter(RouterProperty property)
    {
        if (property != null)
        {
            String name = property.getName(); //Verify Name
            if (ZUtils.isEmpty(name))
            {
                mZLog.error("Verify ZRouter, [name] must not be empty");
                return false;
            }
            String group = property.getGroup(); //Verify Name
            if (ZUtils.isEmpty(group))
            {
                property.setGroup(DEFAULT_GROUP);
            }
            property.setPath(group + "/" + name);
            return true;
        }
        mZLog.error("Verify ZRouter, [routerProperty] is NULL...");
        return false;
    }

    private static final class Helper
    {
        final TypeElement type_Activity;
        final ClassName   claN_routerProperty;
        final ClassName   claN_routerType;
        final TypeElement type_IZRouterGroup;

        private Helper(Elements elements)
        {
            type_Activity = elements.getTypeElement(ACTIVITY);

            type_IZRouterGroup = elements.getTypeElement(I_ZROUTER_GROUP);

            claN_routerProperty = ClassName.get(RouterProperty.class);
            claN_routerType = ClassName.get(RouterType.class);

        }

        static Helper builder(Elements elements)
        {
            return new Helper(elements);
        }

        public MethodSpec.Builder createBuilder_InjectMethod_Groups()
        {
            /**
             * ```Map<String, RouterProperty>```  (name, RouterProperty)
             */
            ParameterizedTypeName inputMapTypeOfGroup = ParameterizedTypeName.get(
                    ClassName.get(Map.class),
                    ClassName.get(String.class),
                    ClassName.get(RouterProperty.class)
            );

            // Build param
            ParameterSpec parameterSpec_Groups = ParameterSpec.builder(inputMapTypeOfGroup, AUTO_CREATE_INJECT_GROUP_NAME_PARAM).build();

            // Build method
            return MethodSpec.methodBuilder(AUTO_CREATE_INJECT_NAME_METHOD)
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .addParameter(parameterSpec_Groups);
        }

        public MethodSpec.Builder createBuilder_InjectMethod_Routers()
        {
            /**
             * ```Map<String, Class<? extends IRouteGroup>>```  (groupName, Class<? extends IRouteGroup>)
             */
            ParameterizedTypeName inputMapTypeOfName = ParameterizedTypeName.get(
                    ClassName.get(Map.class),
                    ClassName.get(String.class),
                    ParameterizedTypeName.get(
                            ClassName.get(Class.class),
                            WildcardTypeName.subtypeOf(ClassName.get(type_IZRouterGroup)))
            );
            ParameterSpec parameterSpec_Routers = ParameterSpec.builder(inputMapTypeOfName, AUTO_CREATE_INJECT_ROUTER_NAME_PARAM).build();

            return MethodSpec.methodBuilder(AUTO_CREATE_INJECT_NAME_METHOD)
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                    .addParameter(parameterSpec_Routers);
        }
    }
}
