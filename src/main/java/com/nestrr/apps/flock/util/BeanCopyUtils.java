package com.nestrr.apps.flock.util;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * Credit: <a href="https://gist.github.com/alankarapte/902bfe20c3e8e6a1ad82d5e4d7cc164c">...</a>
 */
public class BeanCopyUtils {

  /**
   * Copies properties from one object to another
   *
   * @param source The source object.
   * @param destination The destination object.
   */
  public static void copyNonNullProperties(
      Object source, Object destination, String... ignoreProperties) {
    Set<String> ignorePropertiesSet = getNullPropertyNames(source);
    for (String propertyName : ignoreProperties) {
      ignorePropertiesSet.add(propertyName);
    }
    BeanUtils.copyProperties(source, destination, ignorePropertiesSet.toArray(String[]::new));
  }

  /**
   * Returns an array of null properties of an object
   *
   * @param source The source object.
   * @return A Set of the properties that are null.
   */
  public static Set<String> getNullPropertyNames(Object source) {
    final BeanWrapper src = new BeanWrapperImpl(source);
    java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
    Set<String> emptyNames = new HashSet<String>();
    for (java.beans.PropertyDescriptor pd : pds) {
      // check if value of this property is null then add it to the collection
      Object srcValue = src.getPropertyValue(pd.getName());
      if (srcValue == null) emptyNames.add(pd.getName());
    }
    return emptyNames;
  }

  /**
   * Returns an array of non-null properties of an object
   *
   * @param source The source object.
   * @return A Set of the properties that are null.
   */
  public static Set<String> getNonNullPropertyNames(Object source) {
    final BeanWrapper src = new BeanWrapperImpl(source);
    java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
    Set<String> nonNullNames = new HashSet<String>();
    for (java.beans.PropertyDescriptor pd : pds) {
      // check if value of this property is non-null, then add it to the collection
      Object srcValue = src.getPropertyValue(pd.getName());
      if (srcValue != null) nonNullNames.add(pd.getName());
    }
    return nonNullNames;
  }
}
