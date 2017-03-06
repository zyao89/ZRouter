package com.zyao89.zrouter.compiler.processor;

import com.google.auto.service.AutoService;
import com.zyao89.zrouter.annotation.ZRouter;
import com.zyao89.zrouter.compiler.utils.ZUtils;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import static com.zyao89.zrouter.compiler.utils.Constant.ANNOTATION_TYPE_ZROUTER;
import static com.zyao89.zrouter.compiler.utils.Constant.GRADLE_KEY_MODULE_NAME;


/**
 * Created by zyao89 on 2017/3/3.
 * Contact me at 305161066@qq.com or zyao89@gmail.com
 * For more projects: https://github.com/zyao89
 */
@AutoService(Processor.class)
@SupportedOptions(GRADLE_KEY_MODULE_NAME)
@SupportedAnnotationTypes({ANNOTATION_TYPE_ZROUTER})
public class ZRouterProcessor extends AbstractProcessor
{
    private ProcessorHelper mProcessorHelper;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment)
    {
        super.init(processingEnvironment);
        mProcessorHelper = new ProcessorHelper(processingEnvironment);
        mProcessorHelper.initModuleName();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment)
    {
        if (ZUtils.isNotEmpty(set))
        {
            Set<? extends Element> routerElements = roundEnvironment.getElementsAnnotatedWith(ZRouter.class);
            mProcessorHelper.parseZRouters(routerElements);
            return true;
        }

        return false;
    }

    @Override
    public SourceVersion getSupportedSourceVersion()
    {
        return SourceVersion.latestSupported();
    }
}
