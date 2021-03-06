package au.com.normist.capital.repository.cap.sqlwriter.mapping;

import au.com.normist.capital.core.annotation.cap.PersistEntity;

import javax.persistence.*;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;

class Entities {

  public static AccessibleObject getIdAccessorOrNull(Class<?> type) {
    for (Method method : type.getMethods()) {
      if (isIdAccessor(method)) {
        return method;
      }
    }

    for (Field field : type.getDeclaredFields()) {
      if (isIdAccessor(field)) {
        return field;
      }
    }

    //throw new IllegalArgumentException(type.getName() + " does not have a field or property annotated with @Id");
    return null;
  }

  /**
   * @return Name of corresponding table. Uses Table annotation if present, defaults to class's simple name.
   */
  public static String getName(Class<?> entityClass) {
    if (entityClass.isAnnotationPresent(Table.class)) {
      String name = entityClass.getAnnotation(Table.class).name();
      if (!name.isEmpty()) {
        return name;
      }
    }

    return entityClass.getSimpleName();
  }

  /**
   * @return Name of corresponding field. Uses Column annotation if present, defaults to field if accessibleObject is a field or JavaBean-style property name if accessibleObject is a method.
   */
  public static String getName(AccessibleObject accessibleObject) {
    if (accessibleObject.isAnnotationPresent(Column.class)) {
      String name = accessibleObject.getAnnotation(Column.class).name();
      if (!name.isEmpty()) {
        return name;
      }
    }

    if (accessibleObject instanceof Field) {
      return ((Field) accessibleObject).getName();
    }

    Method method = (Method) accessibleObject;

    try {
      PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(method.getDeclaringClass()).getPropertyDescriptors();
      for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
        if (method.equals(propertyDescriptor.getReadMethod())) {
          return propertyDescriptor.getName();
        }
      }
    } catch (IntrospectionException e) {
      throw new RuntimeException(e);
    }

    throw new IllegalArgumentException("No column name can be derived from " + method + ". Make sure it is a getter.");

  }

  public static boolean isTransient(AccessibleObject accessibleObject) {
    return Modifier.isTransient(((Member) accessibleObject).getModifiers()) || accessibleObject.isAnnotationPresent(Transient.class);
  }

  public static boolean isRelation(AccessibleObject accessibleObject) {
    return accessibleObject.isAnnotationPresent(OneToMany.class) || isToOneRelation(accessibleObject);
  }

  public static boolean isToOneRelation(AccessibleObject accessibleObject) {
    return accessibleObject.isAnnotationPresent(ManyToOne.class) || accessibleObject.isAnnotationPresent(OneToOne.class);
  }

  public static String getAnnotatedColumnName(AnnotatedElement annotatedElement) {
    return annotatedElement.isAnnotationPresent(JoinColumn.class) ? annotatedElement.getAnnotation(JoinColumn.class).name() : "";
  }

  private static boolean isIdAccessor(AnnotatedElement annotatedElement) {
    return annotatedElement.isAnnotationPresent(Id.class);
  }

  public static boolean isMapped(Class<?> objectClass) {
    return objectClass.isAnnotationPresent(Entity.class) || objectClass.isAnnotationPresent(PersistEntity.class) || objectClass.isAnnotationPresent(MappedSuperclass.class);
  }

  public static boolean isStatic(Member member) {
    return Modifier.isStatic(member.getModifiers());
  }

  private Entities() {}
}
