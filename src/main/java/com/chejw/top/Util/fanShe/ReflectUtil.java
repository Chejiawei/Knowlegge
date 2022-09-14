package com.chejw.top.Util.fanShe;

import com.chejw.top.Util.Exception.CHEJWRuntimeException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ReflectUtil {

    public static List<Object> getField(Object obj, Class objClass, Class cla) {
        List<Object> objectList = new ArrayList<>();
        Field[] fields = objClass.getDeclaredFields();
        for (Field field : fields) {
            try {
                if (field.getType().getCanonicalName().equalsIgnoreCase(cla.getCanonicalName())) {
                    field.setAccessible(true);
                    Object filedObj = field.get(objClass.cast(obj));
                    if (filedObj != null) {
                        objectList.add(filedObj);
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return objectList;
    }

    public static List<Object> getField(Object obj, Class cla) {
        List<Object> objectList = new ArrayList<>();
        for (Class<?> clazz = obj.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                try {
                    if (field.getType().getCanonicalName().equalsIgnoreCase(cla.getCanonicalName())) {
                        field.setAccessible(true);
                        Object filedObj = field.get(obj);
                        if (filedObj != null) {
                            objectList.add(filedObj);
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return objectList;
    }

    public static Object getField(Object obj, String fieldName) {
        for (Class<?> clazz = obj.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.getName().equals(fieldName)) {
                    field.setAccessible(true);
                    try {
                        return field.get(obj);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }


    public static Object invokeMethod(Object object, String methodName, Object[] args) {
        Object result;
        Method method = null;
        try {
            if (args != null) {
                Class<?>[] types = new Class[args.length];
                for (int i = 0; i < args.length; i++) {
                    types[i] = getBaseType(args[i]);
                }
                for (Class<?> clazz = object.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
                    try {
                        method = clazz.getDeclaredMethod(methodName, types);
                        method.setAccessible(true);
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                for (Class<?> clazz = object.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
                    try {
                        method = clazz.getDeclaredMethod(methodName);
                        method.setAccessible(true);
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            if (method != null) {
                result = method.invoke(object, args);
            } else {
                throw new NullPointerException();
            }
        } catch (Exception e) {
            throw new CHEJWRuntimeException("invoke Method Failed: " + methodName);
        }
        return result;
    }

    public static Object invokeMethod(Object object, String methodName) {
        return invokeMethod(object, methodName, null);
    }

    public static Object getParam(Object object, String name) {
        Object result = null;
        Field field = null;
        try {
            for (Class<?> clazz = object.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
                try {
                    field = clazz.getDeclaredField(name);
                    field.setAccessible(true);
                    break;
                } catch (NoSuchFieldException ignore) {
                }
            }
            if (field != null) {
                result = field.get(object);
            } else {
                throw new CHEJWRuntimeException("get Param Failed: " + name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static <T> T assertNotNull(T object) {
        if (object != null) {
            return object;
        } else {
            throw new NullPointerException();
        }
    }

    public static Class<?> getBaseType(Object object) {
        Class className = object.getClass();
        if (className.equals(Integer.class)) {
            return Integer.TYPE;
        } else if (className.equals(Byte.class)) {
            return Byte.TYPE;
        } else if (className.equals(Long.class)) {
            return Long.TYPE;
        } else if (className.equals(Double.class)) {
            return Double.TYPE;
        } else if (className.equals(Float.class)) {
            return Float.TYPE;
        } else if (className.equals(Character.class)) {
            return Character.TYPE;
        } else if (className.equals(Short.class)) {
            return Short.TYPE;
        } else if (className.equals(Boolean.class)) {
            return Boolean.TYPE;
        } else {
            return className;
        }
    }

}
