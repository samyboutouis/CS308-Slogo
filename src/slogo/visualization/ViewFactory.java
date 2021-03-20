package slogo.visualization;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class ViewFactory {
  private static final String PACKAGE = View.class.getPackageName();
  private static final String POSSIBLE_VIEWS = "resources/reflection/PossibleViews.properties";

  private List<String> possibleViewsList;

  public ViewFactory() {
    ResourceBundle reflectionResources = ResourceBundle.getBundle(POSSIBLE_VIEWS);
    for (String key : Collections.list(reflectionResources.getKeys())) {
      possibleViewsList.add(key);
    }
  }

  public View makeView(String className) throws IllegalStateException {
    try {
      Class<?> clazz = Class.forName(PACKAGE + "." + className);
      Constructor<?> ctor = clazz.getDeclaredConstructor();
      return (View) ctor.newInstance();
    } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException |
      InstantiationException | InvocationTargetException e) {
      throw new IllegalStateException("ERROR: unable to create proper view", e);
    }

  }
}
