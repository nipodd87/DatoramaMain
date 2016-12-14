package com.ignitionone.datastorm.datorama.util;

import java.io.InputStream;
import java.util.Properties;

/**
 * TODO: Enter Javadoc
 */
public class PropertyLoader {

    //~ Instance fields ------------------------------------------------------------------------------------------------

    private Properties props;

    //~ Methods --------------------------------------------------------------------------------------------------------

    /**
     * Getter for property props
     *
     * @return out value
     */
    public Properties getProps() {
        return props;
    }

    /**
     * retrive properties from file name
     *
     * @param fileName in value
     */
    public void loadProperties(String fileName) {
        this.props = new Properties();

        try {
            ClassLoader loader = ClassLoader.getSystemClassLoader();
            InputStream in = loader.getResourceAsStream(fileName);
            this.props.load(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
