package com.daughter.qinghan.utils;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.lang.invoke.SerializedLambda;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * The type Bean utils.
 *
 * @author fangxi
 */
public class BeanUtils extends BeanUtil {

    /**
     * SerializedLambda 反序列化缓存
     */
    private static final Map<Class<?>, WeakReference<SerializedLambda>> FUNC_CACHE = new ConcurrentHashMap<>(1 << 8);


    /**
     * Copy properties t.
     *
     * @param <T>    the type parameter
     * @param source 需要被拷贝的对象
     * @param target 需要返回的对象类型
     * @return the t
     */
    public static <T> T copyProperties(Object source, Class<T> target) {
        try {
            Assert.notNull(source, "Source must not be null");
            Assert.notNull(target, "Target must not be null");
            final Constructor<T> constructor = target.getConstructor();
            final T targetInstance = constructor.newInstance();
            BeanCopier beanCopier = BeanCopier.create(source.getClass(), target, false);
            beanCopier.copy(source, targetInstance, null);
            return targetInstance;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("copy properties fail");
        }
    }

    /**
     * Copy list properties list.
     *
     * @param <T>    the type parameter
     * @param <E>    the type parameter
     * @param source the source
     * @param target the target
     * @return the list
     */
    public static <T, E> List<T> copyProperties(Collection<E> source, Class<T> target) {
        Assert.notNull(source, "Source must not be null");
        return source.stream().map(x -> copyProperties(x, target)).collect(Collectors.toList());
    }

    /**
     * Copy properties.
     *
     * @param source 需要被拷贝的对象
     * @param target 目标对象
     */
    public static void copyProperties(Object source, Object target) {
        BeanCopier beanCopier = BeanCopier.create(source.getClass(), target.getClass(), false);
        beanCopier.copy(source, target, null);
    }


    /**
     * 通过Lambda的get方法引用拿到私有属性名
     *
     * @param <T>      字段类型
     * @param function lambda表达式
     * @return 返回字段名 string
     */
    public static <T> String convertToFieldName(Function<T, ?> function) {
        Class<?> clazz = function.getClass();
        return Optional.ofNullable(FUNC_CACHE.get(clazz))
                .map(WeakReference::get)
                .map(SerializedLambda::getImplMethodName)
                .map(BeanUtils::removeFreFix)
                .orElseGet(() -> {
                    SerializedLambda lambda = resolve(function);
                    String methodName = lambda.getImplMethodName();
                    FUNC_CACHE.put(clazz, new WeakReference<>(lambda));
                    return removeFreFix(methodName);
                });

    }

    /**
     * 深拷贝
     *
     * @param <T>    the type parameter
     * @param object the object
     * @return t t
     */
    public static <T> T deepCopy(T object) {
        if (object == null) {
            return null;
        }
        String serialize = JacksonUtils.serialize(object);
        return StringUtils.isBlank(serialize) ? null : JacksonUtils.parse(serialize, new TypeReference<T>() {
        });
    }


    /**
     * Describe list.
     *
     * @param <T>   the type parameter
     * @param beans the beans
     * @return the list
     */
    public static <T> List<Map<String, Object>> describe(List<T> beans) {
        if (CollectionUtils.isEmpty(beans)) {
            return Collections.emptyList();
        }
        return beans.stream().map(BeanUtils::describe).collect(Collectors.toList());
    }


    /**
     * 将对象转成Map
     *
     * @param obj the obj
     * @return map map
     */
    public static Map<String, Object> describe(Object obj) {
        return BeanUtil.beanToMap(obj, false, true);
    }

    /**
     * 验证某个bean的参数
     *
     * @param <T>    the type parameter
     * @param object 被校验的参数
     * @throws ValidationException 如果参数校验不成功则抛出此异常
     */
    public static <T> void validate(T object) {
        //获得验证器
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        //执行验证
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(object);
        //如果有验证信息，则取出来包装成异常返回
        if (!CollectionUtils.isEmpty(constraintViolations)) {
            throw new ValidationException(convertErrorMsg(constraintViolations));
        }
    }


    /**
     * 转换异常信息
     *
     * @param <T> the type parameter
     * @param set the set
     * @return the string
     */
    private static <T> String convertErrorMsg(Set<ConstraintViolation<T>> set) {
        return set.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(StringPool.COMMA));
    }


    /**
     * Remove fre fix string.
     *
     * @param methodName the method name
     * @return the string
     */
    private static String removeFreFix(String methodName) {
        String prefix = null;
        if (methodName.startsWith("get")) {
            prefix = "get";
        } else if (methodName.startsWith("is")) {
            prefix = "is";
        }
        if (prefix == null) {
            throw new RuntimeException("无效的getter方法: " + methodName);
        }
        String replace = methodName.replace(prefix, StringPool.EMPTY);
        if (Character.isLowerCase(replace.charAt(0))) {
            return replace;
        } else {
            return Character.toLowerCase(replace.charAt(0)) + replace.substring(1);
        }
    }

    /**
     * Resolve serialized lambda.
     *
     * @param <T>    the type parameter
     * @param lambda the lambda
     * @return the serialized lambda
     */
    private static <T> SerializedLambda resolve(Function<T, ?> lambda) {
        try {
            Class<?> clazz = lambda.getClass();
            if (!clazz.isSynthetic()) {
                throw new RuntimeException("该方法仅能传入 lambda 表达式产生的合成类");
            }
            // 提取SerializedLambda并缓存
            Method method = lambda.getClass().getDeclaredMethod("writeReplace");
            method.setAccessible(Boolean.TRUE);
            return (SerializedLambda) method.invoke(lambda);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
