package com.jr.websession;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.jr.DynamicInvocationHandler;
import com.jr.controller.HomeController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ConferenceWebSessionManager {

    private static final String CONF_WEB_SESSION_ENABLED_ANNOTATION_NAME
            = "com.jr.websession.ConferenceWebSessionEnabled";

    private static Log log = LogFactory.getLog(ConferenceWebSessionManager.class);

    public ConferenceWebSessionManager() {
        if(log.isDebugEnabled()) {
            log.debug("in ConferenceWebSessionManagerConstructor");
        }
        scanConfiguredPackagesForAnnotations();
    }

    public void scanConfiguredPackagesForAnnotations() {
        try {
            String packageName = readConfig();

            ClassLoader classLoader = getClass().getClassLoader();
            String packagePath  = packageName.replace('.', '/');
            URL urls = classLoader.getResource(packagePath);

            File folder = new File(urls.getPath());
            File[] classes = folder.listFiles();

            int numClassesInPackage = classes.length;

            List<Class<?>> classList =
                    processClassAnnotations(numClassesInPackage, classes, packageName, classLoader);

            if (log.isDebugEnabled()) {
                if (classList != null) {
                    log.debug("number of classes processed for conference web session annotation: "
                            + classList.size());
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private List<Class<?>> processClassAnnotations(int numClassesInPackage,
                                         File[] classes,
                                         String packageName,
                                         ClassLoader classLoader) throws ClassNotFoundException {

        List<Class<?>> classList = new ArrayList<Class<?>>();

        for(int i = 0; i < numClassesInPackage; i++){
            int index = classes[i].getName().indexOf(".");
            String className = classes[i].getName().substring(0, index);
            String classNamePath = packageName + "." + className;
            Class<?> repoClass;
            repoClass = Class.forName(classNamePath);
            Annotation[] annotations = repoClass.getAnnotations();
            for(int j = 0; j < annotations.length; j++){
                processAnnotation(annotations[j], repoClass, classLoader, classList);
            }

        }

        return classList;
    }

    private void processAnnotation(Annotation annotation, Class repoClass, ClassLoader classLoader,
                                   List<Class<?>> classList) {
        String clazzName = repoClass.getName();
        String annotationName = annotation.annotationType().getName();

        if (log.isDebugEnabled()) {
            log.debug("Annotation in class "
                    + clazzName
                    + " is "
                    + annotationName);
        }

        if (annotation.annotationType().getName()
                .equals(CONF_WEB_SESSION_ENABLED_ANNOTATION_NAME)) {
            classList.add(repoClass);
            registerProxyForAnnotatedType(classLoader);
        }
    }

    private void registerProxyForAnnotatedType(ClassLoader classLoader) {
        HomeController proxyInstance = (HomeController) Proxy.newProxyInstance(
                classLoader,
                new Class[] { HomeController.class },
                new DynamicInvocationHandler());

        proxyInstance.adminPage();
    }

    private String readConfig() {
        String result = "com.jr.controller";
        return result;
    }
}
