package slogo.visualization.observers;

/**
 * An observer interface that observes changes that occur due to loading from an XML file. In the
 * classes that implement this interface, they will update their states when certain actions or
 * events occur from loading in XML files for workspace preferences.
 *
 * @author Samy Boutouis
 */
public interface XMLObserver {

  /**
   * Updates the language when loading in an XML file.
   *
   * @param language String representing the language chosen.
   */
  void updateLanguage(String language);
}
