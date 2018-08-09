package au.com.normist.capital.repository.cap.dbutilsjpa;

/**
 * Determines whether the given entity is new or not.
 */
public interface NewEntityTester {

    boolean isNew(Object entity);

}
