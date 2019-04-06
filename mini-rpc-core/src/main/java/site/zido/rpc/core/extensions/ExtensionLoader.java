package site.zido.rpc.core.extensions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import site.zido.rpc.utils.ReflectionUtils;
import site.zido.rpc.utils.values.Holder;
import site.zido.rpc.utils.values.VolHolder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Pattern;

/**
 * load mini rpc extensions
 *
 * @author zido
 */
public class ExtensionLoader<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExtensionLoader.class);

    private static final String SERVICE_PATH = "META-INF/extensions";

    private static final String MINI_DIRECTORY = "META-INF/mini";

    private static final String MINI_INTERNAL_DIRECTORY = MINI_DIRECTORY + "internal/";

    private static final Pattern NAME_SEPARATOR = Pattern.compile("\\s*[,]+\\s*");

    private static final ConcurrentMap<Class<?>,ExtensionLoader<?>> EXTENSION_LOADERS = new ConcurrentHashMap<>();

    private static final ConcurrentMap<Class<?>,Object> EXTENSION_INSTANCES = new ConcurrentHashMap<>();

    private final Class<?> type;
    private final ExtensionFactory objectFactory;

    private final ConcurrentMap<Class<?>,String> cachedNames = new ConcurrentHashMap<>();
    private final VolHolder<Map<String,Class<?>>> cachedClasses = new VolHolder<>();
    private final Map<String,Object> cachedActivates = new ConcurrentHashMap<>();
    private final ConcurrentMap<String,VolHolder<Object>> cachedInstances = new ConcurrentHashMap<>();
    private final VolHolder<Object> cachedAdaptiveInstance = new VolHolder<>();
    private volatile Class<?> cachedAdaptiveClass = null;
    private String cachedDefaultName;
    private volatile Throwable createAdaptiveInstanceError;

    private Set<Class<?>> cachedWrapperClasses;

    private Map<String,IllegalStateException> exceptions = new ConcurrentHashMap<>();

    private ExtensionLoader(Class<?> type){
        this.type = type;
        objectFactory = (type == ExtensionFactory.class ? null : ExtensionLoader.getExtensionLoader(ExtensionFactory.class).getAdaptiveExtension());
    }

    @SuppressWarnings("unchecked")
    public static <T> ExtensionLoader<T> getExtensionLoader(Class<T> type){
        ExtensionLoader<T> loader = (ExtensionLoader<T>) EXTENSION_LOADERS.get(type);
        if(loader == null){
            EXTENSION_LOADERS.putIfAbsent(type, new ExtensionLoader<T>(type));
            loader = (ExtensionLoader<T>) EXTENSION_LOADERS.get(type);
        }
        return loader;
    }

    private static void checkInterfaceType(Class<?> type){
        if(type == null){
            throw new IllegalArgumentException("Extension type == null");
        }
        if(!type.isInterface()){
            throw new IllegalArgumentException("Extension type (" + type +") is not an interface!");
        }
        if(!withSPIAnnotation(type)){
            throw new IllegalArgumentException("Extension type (" + type +
                    ") is not an extension, because it is NOT annotated with @" + SPI.class.getSimpleName() + "!");
        }
    }

    private static <T> boolean withSPIAnnotation(Class<T> type) {
        return type.isAnnotationPresent(SPI.class);
    }

    @SuppressWarnings("unchecked")
    public T getAdaptiveExtension(){
        Object instance = cachedAdaptiveInstance.get();
        if(instance == null){
            if(createAdaptiveInstanceError == null){
                synchronized (cachedAdaptiveInstance){
                    instance = cachedAdaptiveInstance.get();
                    if(instance == null){
                        try {
                            instance = createAdaptiveExtension();
                            cachedAdaptiveInstance.set(instance);
                        } catch (Throwable t) {
                            createAdaptiveInstanceError = t;
                            throw new IllegalStateException("Failed to create adaptive instance: " + t.toString(), t);
                        }
                    }
                }
            }else {
                throw new IllegalStateException("Failed to create adaptive instance: " + createAdaptiveInstanceError.toString(), createAdaptiveInstanceError);
            }
        }
        return (T) instance;
    }

    private T createAdaptiveExtension(){
            return injectExtension(getAdaptiveExtension());
    }

    private Class<?> getAdaptiveExtensionClass(){
        getExtensionClasses();
        if(cachedAdaptiveClass != null){
            return cachedAdaptiveClass;
        }
        //TODO get adaptive extension class
        return null;
    }

    private Map<String,Class<?>> getExtensionClasses(){
        Map<String,Class<?>> classes = cachedClasses.get();
        if(classes == null){
            synchronized (cachedClasses){
                classes = cachedClasses.get();
                if(classes == null){
                    classes =loadExtensionClasses();
                }
            }
        }
        return null;
    }

    private Map<String,Class<?>> loadExtensionClasses(){
        cacheDefaultExtensionName();

        Map<String,Class<?>> extensionClasses = new HashMap<>();
        return null;
    }

    private void cacheDefaultExtensionName() {
        final SPI defaultAnnotation = type.getAnnotation(SPI.class);
        if(defaultAnnotation != null){
            String value = defaultAnnotation.value();
            if((value = value.trim()).length() > 0){
                String[] names = NAME_SEPARATOR.split(value);
                if(names.length > 1){
                    throw new IllegalStateException("More than 1 default extension name on extension " + type.getName()
                    +": " + Arrays.toString(names));
                }
                if(names.length == 1){
                    cachedDefaultName = names[0];
                }
            }
        }
    }

    private T injectExtension(T instance){
        try{
            if(objectFactory != null){
                for(Method method : instance.getClass().getMethods()){
                    if(isSetter(method)){
                        //check DisableInject annotation to see if we need auto injection for this property
                        if(method.getAnnotation(DisableInject.class) != null){
                            continue;
                        }
                        Class<?> pt = method.getParameterTypes()[0];
                        if(ReflectionUtils.isPrimitive(pt)){
                            continue;
                        }
                        try{
                            String property = getSetterProperty(method);
                            Object object = objectFactory.getExtension(pt,property);
                            if(object != null) {
                                method.invoke(instance,object);
                            }
                        } catch (Exception e) {
                            LOGGER.error("Failed to inject via method "+method.getName()
                            +" of interface " + type.getName() + ": " + e.getMessage(),e);
                        }
                    }
                }
            }
        }catch (Exception e){
            LOGGER.error(e.getMessage(),e);
        }
        return instance;
    }

    private String getSetterProperty(Method method){
        return method.getName().length() > 3
                ? method.getName().substring(3,4).toLowerCase()+method.getName().substring(4)
                :"";
    }

    /**
     * return true if and only if:
     * <p>
     * 1, public
     * <p>
     * 2, name starts with "set"
     * <p>
     * 3, only has one parameter
     */
    private boolean isSetter(Method method) {
        return method.getName().startsWith("set")
                && method.getParameterTypes().length == 1
                && Modifier.isPublic(method.getModifiers());
    }
}
